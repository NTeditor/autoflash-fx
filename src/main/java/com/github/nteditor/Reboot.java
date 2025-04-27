package com.github.nteditor;

import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Reboot {
    private String to;
    private Label outputLabel;
    private Shell process;

    public Reboot(String to, Label outputLabel) {
        this.outputLabel = outputLabel;
        if (to == "system" || to == "System") {
            this.to = "";
        } else {
            this.to = to;
        }
    }

    public void rebootS2() {
        Platform.runLater(() -> outputLabel.setText("Перезагрузка в " + this.to + "..."));
        new Thread(() -> {
            process = new Shell(List.of("adb", "reboot", this.to), outputLabel);
            process.start();
        }).start();   
    }
    
    public void rebootF2() {
        Platform.runLater(() -> outputLabel.setText("Перезагрузка в " + this.to + "..."));
        new Thread(() -> {
            process = new Shell(List.of("fastboot", "reboot", this.to), outputLabel);
            process.start();
        }).start();
    }

    public void stop() {
        if (process != null) {
            process.stop();
        }
    }
}
