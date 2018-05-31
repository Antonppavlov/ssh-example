package ru.pavlovap.sshexample.utils;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import ru.pavlovap.sshexample.utils.user.SUserInfo;

import java.util.Arrays;

@Slf4j
public class ConnectSSH {

    private final static String HOST = "";
    private final static String USER = "";
    private final static String PASSWORD = "";
    private static final int PORT = 22;


    public static ChannelExec openChannelExec() {
        return (ChannelExec) openChannel("exec");
    }

    public static ChannelSftp openChannelSftp() {
        return (ChannelSftp) openChannel("sftp");
    }


    private static Channel openChannel(String typeChannel) {
        Session session = null;
        Channel channel = null;

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

            channel = session.openChannel(typeChannel);

        } catch (Exception e) {
            log.error(e.getMessage());
            Arrays.stream(e.getStackTrace())
                    .map(StackTraceElement::toString).forEach(log::error);
        }

        return channel;

    }

}
