package com.github.nteditor;

import java.util.List;

import javafx.scene.control.Label;

public class Reboot {
    private String to;
    private Label outputLabel;

    public Reboot(String to, Label outputLabel) {
        this.outputLabel = outputLabel;
        if (to == "system" || to == "System") {
            this.to = "";
        } else {
            this.to = to;
        }
    }

    public void rebootS2() {
        new Thread(() -> {
            new Shell(List.of("adb", "reboot", this.to), outputLabel).start();
        }).start();
    }

    public void rebootF2() {
        new Thread(() -> {
            new Shell(List.of("fastboot", "reboot", this.to), outputLabel).start();
        }).start();
    }
}
