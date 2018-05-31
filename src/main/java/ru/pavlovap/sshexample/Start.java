package ru.pavlovap.sshexample;

import ru.pavlovap.sshexample.utils.Command;

public class Start {
    public static void main(String[] args) throws Exception {
       new Command().send("ls -la");
    }
}
