package com.github.nteditor;

public class Reboot {
    private String[] to = new String[3];

    public Reboot(String to) {
        this.to[2] = to;
        this.to[1] = "reboot";
    }

    public void rebootS2() {
        this.to[0] = "adb";
        new Shell(this.to).start();
    }

    public void rebootF2() {
        this.to[0] = "fastboot";
        new Shell(this.to).start();
    }
}
