package ru.pavlovap.sshexample;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;

public class Ssh {

    String host = "";
    String user = "";
    String password = "";

    private static final Integer port = 22;

    protected void sendCommand(String command) throws Exception {
        Session session = null;
        ChannelExec channelExec = null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);

            session.setConfig(
                    "PreferredAuthentications",
                    "publickey,keyboard-interactive,password"
            );

            UserInfo ui = new SUserInfo(password, null);
            session.setUserInfo(ui);
            session.setPassword(password);
            session.connect();

            channelExec = (ChannelExec) session.openChannel("exec");
            InputStream in = channelExec.getInputStream();
            channelExec.setCommand(command);
            channelExec.connect();

            AtomicInteger atomicInteger = new AtomicInteger();

            new BufferedReader(new InputStreamReader(in))
                    .lines()
                    .forEach(
                            linea -> System.out.println(atomicInteger.getAndIncrement() + " : " + linea)
                    );

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channelExec != null) {
                channelExec.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }

            channelExec = null;
            session = null;
        }

    }

    public class SUserInfo implements UserInfo {

        private String password;
        private String passPhrase;

        public SUserInfo(String password, String passPhrase) {
            this.password = password;
            this.passPhrase = passPhrase;
        }

        public String getPassphrase() {
            return passPhrase;
        }

        public String getPassword() {
            return password;
        }

        public boolean promptPassphrase(String arg0) {
            return true;
        }

        public boolean promptPassword(String arg0) {
            return false;
        }

        public boolean promptYesNo(String arg0) {
            return true;
        }

        public void showMessage(String arg0) {
            System.out.println("SUserInfo.showMessage()");
        }
    }
}