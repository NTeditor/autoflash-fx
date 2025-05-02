package com.github.nteditor;

import javafx.application.Platform;
import javafx.scene.control.Label;
import java.util.List;

public class Devices {
    private final Label outputLabel;

    public Devices(Label outputLabel) {
        this.outputLabel = outputLabel;
    }

    public void getADBDevices() {
        Platform.runLater(() -> outputLabel.setText("Устройства в режиме adb:"));
        new Thread(() -> new Shell(List.of("adb", "devices"), outputLabel).start())
                .start();
    }

    public void getFastbootDevices() {
        Platform.runLater(() -> outputLabel.setText("Устройства в режиме fastboot:"));
        new Thread(() -> new Shell(List.of("fastboot", "device"), outputLabel).start())
                .start();
    }

}
