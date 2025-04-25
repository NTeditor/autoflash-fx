package com.github.nteditor.flash;

import java.io.File;
import java.util.List;

import com.github.nteditor.Shell;

import javafx.stage.FileChooser;

public class FlashGSI {
    private File fileChooser() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("IMG Образ", "*.img"),
            new FileChooser.ExtensionFilter("BIN Образ", "*.bin"));

        return fileChooser.showOpenDialog(null);
    }

    private boolean isCanceled(File imgPath) {
        if (imgPath == null) return true;
        return false;
    }

    public void flash() {
        var file = fileChooser();
        if (isCanceled(file)) {
            System.out.println("1");
            System.out.println(file);
            System.out.println(file.getAbsolutePath());
            return;
        }
        new Thread(() -> {
            Shell shell = new Shell();
            shell.setCommand(List.of("fastboot", "reboot", "fastboot"));
            shell.start();
            shell.setCommand(List.of("fastboot", "flash", "boot", file.getAbsolutePath()));
            shell.start();

            System.out.println("0");
            System.out.println(file);
            System.out.println(file.getAbsolutePath());
        }).start();
    }
}
