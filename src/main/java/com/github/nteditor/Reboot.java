package com.github.nteditor;

import java.util.List;

public class Reboot {
    private String to;

    public Reboot(String to) {
        if (to == "system" || to == "System") {
            this.to = "";
        } else {
            this.to = to;
        }
    }

    public void rebootS2() {
        new Thread(() -> {
            new Shell(List.of("adb", "reboot", this.to)).start();
        });
    }

    public void rebootF2() {
        new Thread(() -> {
            new Shell(List.of("fastboot", "reboot", this.to)).start();
        });
    }
}
