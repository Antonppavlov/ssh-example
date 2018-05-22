package ru.pavlovap.sshexample;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Ssh {

    private final static String HOST = "";
    private final static String USER = "";
    private final static String PASSWORD = "";
    private static final int PORT = 22;

    protected void sendCommand(String command) {
        Session session = null;
        ChannelExec channelExec = null;
        try {
            session = new JSch().getSession(USER, HOST, PORT);

            session.setConfig(
                    "PreferredAuthentications",
                    "publickey,keyboard-interactive,PASSWORD"
            );

            session.setUserInfo(
                    new SUserInfo(PASSWORD, null)
            );
            session.setPassword(PASSWORD);
            session.connect();

            channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setCommand(command);
            channelExec.connect();

            AtomicInteger atomicInteger = new AtomicInteger(1);

            new BufferedReader(new InputStreamReader(channelExec.getInputStream()))
                    .lines()
                    .forEach(
                            linea -> log.info(atomicInteger.getAndIncrement() + " : " + linea)
                    );

        } catch (Exception e) {
            log.error(e.getMessage());
            Arrays.stream(e.getStackTrace()).forEach(
                    stackTraceElement ->
                            log.error(stackTraceElement.toString())
            );
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

    @Getter
    @AllArgsConstructor
    public class SUserInfo implements UserInfo {

        private String password;
        private String passphrase;

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