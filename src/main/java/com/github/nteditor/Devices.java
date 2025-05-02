package com.github.nteditor;

import javafx.application.Platform;
import javafx.scene.control.Label;
import java.util.List;

public class Devices {
    private final Label outputLabel;

    public Devices(Label outputLabel) {
        this.outputLabel = outputLabel;
    }

    private void setText(String text) {
        Platform.runLater(() -> outputLabel.setText(outputLabel.getText() + "\n" + text));
    }

    public void getADBDevices() {
        setText("Устройства в режиме adb:");
        new Thread(() -> new Shell(List.of("adb", "devices"), outputLabel).start())
                .start();
    }

    public void getFastbootDevices() {
        setText("Устройства в режиме fastboot:");
        new Thread(() -> new Shell(List.of("fastboot", "device"), outputLabel).start())
                .start();
    }

}
