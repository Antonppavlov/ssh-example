package ru.pavlovap.sshexample.utils;

import com.jcraft.jsch.ChannelExec;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Command {

    public void send(String command) throws IOException {
        ChannelExec channelExec = ConnectSSH.openChannelExec();

        channelExec.setCommand(command);

        AtomicInteger atomicInteger = new AtomicInteger(1);

        new BufferedReader(new InputStreamReader(channelExec.getInputStream()))
                .lines().forEach(
                linea -> log.info(atomicInteger.getAndIncrement() + " : " + linea)
        );

    }
}
