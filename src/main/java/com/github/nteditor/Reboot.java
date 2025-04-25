package com.github.nteditor;

import java.util.List;

public class Reboot {
    private String to;

    public Reboot(String to) {
        this.to = to;
    }

    public void rebootS2() {
        new Shell(List.of("adb", "reboot", this.to)).start();
    }

    public void rebootF2() {
        new Shell(List.of("fastboot", "reboot", this.to)).start();
    }
}
