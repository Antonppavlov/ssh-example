package ru.pavlovap.sshexample.utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

public class Downloader {
    public void download(String sourceFile, String localFile) throws SftpException {
        ChannelSftp channelSftp = ConnectSSH.openChannelSftp();
        channelSftp.get(sourceFile, localFile);
    }
}
