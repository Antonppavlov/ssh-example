package ru.pavlovap.sshexample;

public class Start {
    public static void main(String[] args) throws Exception {
        Ssh ssh = new Ssh();
        ssh.sendCommand("ls -la");
    }
}
