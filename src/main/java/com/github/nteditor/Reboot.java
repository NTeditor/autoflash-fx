package com.github.nteditor;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Reboot {
    private String to;
    private Label outputLabel;
    private ArrayList<Shell> processList = new ArrayList<>();

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
            var proc = new Shell(List.of("adb", "reboot", this.to), outputLabel);
            processList.add(proc);
            proc.start();
        }).start();   
    }
    
    public void rebootF2() {
        Platform.runLater(() -> outputLabel.setText("Перезагрузка в " + this.to + "..."));
        new Thread(() -> {
            var proc = new Shell(List.of("fastboot", "reboot", this.to), outputLabel);
            processList.add(proc);
            proc.start();
        }).start();
    }

    public void stop() {
        for (Shell shell : processList) {
            shell.stop();
        }
        processList.clear();
    }
}
