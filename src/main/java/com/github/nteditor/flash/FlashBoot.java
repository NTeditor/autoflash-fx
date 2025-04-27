package com.github.nteditor.flash;


import java.io.File;
import java.util.List;
import com.github.nteditor.Shell;

import javafx.scene.control.Label;

public class FlashBoot {

    private final int MAX_FILE_SIZE = 100; // MB

    private SelectFile selectFile;
    private File file;
    private Label outputLabel;

    public FlashBoot(Label outputLabel) {
        this.outputLabel = outputLabel;
        this.selectFile = new SelectFile();
        this.file = selectFile.getFile();
    }

    private void startFlash() {
        System.out.println("Выбран файл: " + file.getAbsolutePath());

        new Thread(() -> {
            Shell shell = new Shell(outputLabel);
            shell.setCommand(List.of("fastboot", "reboot", "fastboot"));
            shell.start();
            shell.setCommand(List.of("fastboot", "flash", "boot", file.getAbsolutePath()));
            shell.start();
            System.out.println("Готово!");
        }).start();
    }

    public void flash() {
        if (selectFile.isCanceled(file)) {
            System.err.println("Выбор файла отменен!");
            return;
        }
        if  (selectFile.getSize(file) > MAX_FILE_SIZE) {
            System.err.println("Файл слишком большой!");
            return;
        }
        startFlash();
    }
}
