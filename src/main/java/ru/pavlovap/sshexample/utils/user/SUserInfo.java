package ru.pavlovap.sshexample.utils.user;

import com.jcraft.jsch.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

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