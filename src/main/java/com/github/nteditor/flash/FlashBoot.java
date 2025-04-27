package com.github.nteditor.flash;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.github.nteditor.Shell;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class FlashBoot {

    private final int MAX_FILE_SIZE = 100; // MB

    private SelectFile selectFile;
    private File file;
    private Label outputLabel;
    private List<Shell> processList = new ArrayList<>();
    private volatile boolean isCancelled = false;


    public FlashBoot(Label outputLabel) {
        this.outputLabel = outputLabel;
        this.selectFile = new SelectFile();
        this.file = selectFile.getFile();
    }

    private void startFlash() {
        Platform.runLater(() -> outputLabel.setText("Выбран файл: " + file.getAbsolutePath()));
        new Thread(() -> {
            if (isCancelled) return;
            Platform.runLater(() -> outputLabel.setText("Перезагрузка.."));
            var proc1 = new Shell(List.of("fastboot", "reboot", "fastboot"), outputLabel);  
            processList.add(proc1);
            proc1.start();
            
            if (isCancelled) return;
            Platform.runLater(() -> outputLabel.setText("Прошивка boot.."));
            var proc2 = new Shell(List.of("fastboot", "flash", "boot", file.getAbsolutePath()), outputLabel);
            processList.add(proc2);
            proc2.start();

            if (isCancelled) return;
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
        } else {
            if (isCancelled) return;
            startFlash();
        }
    }

    public void stop() {
        isCancelled = true;
        for (Shell shell : processList) {
            shell.stop();
        }
        processList.clear();
    }
}
