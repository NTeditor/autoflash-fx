package com.github.nteditor.flash;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.github.nteditor.Shell;

import javafx.application.Platform;
import javafx.scene.control.Label;


public class FlashGSI {

    private final int MIN_FILE_SIZE = 500; // MB
    private final int MAX_FILE_SIZE = 7168; // MB

    private SelectFile selectFile;
    private File file;
    private Label outputLabel;
    private List<Shell> processList = new ArrayList<>();
    private volatile boolean isCancelled = false;

    public FlashGSI(Label outputLabel) {
        this.outputLabel = outputLabel;
        this.selectFile = new SelectFile();
        this.file = selectFile.getFile();
    }

    private void startFlash() {
        Platform.runLater(() -> outputLabel.setText("Прошивка GSI\n" +
            "Выбран файл: " + file.getAbsolutePath()));
        new Thread(() -> {
            if (isCancelled) return;
            Platform.runLater(() -> outputLabel.setText("Перезагрузка.."));
            var proc1 = new Shell(List.of("fastboot", "reboot", "fastboot"), outputLabel);
            processList.add(proc1);
            proc1.start();

            if (isCancelled) return;
            Platform.runLater(() -> outputLabel.setText("Очистка system.."));
            var proc2 = new Shell(List.of("fastboot", "erase", "system"), outputLabel);
            processList.add(proc2);
            proc2.start();

            if (isCancelled) return;
            Platform.runLater(() -> outputLabel.setText("Удаление product_a.."));
            var proc3 = new Shell(List.of("fastboot", "delete-logical-partition", "product_a"), outputLabel);
            processList.add(proc3);
            proc3.start();

            if (isCancelled) return;
            Platform.runLater(() -> outputLabel.setText("Удаление product_b.."));
            var proc4 = new Shell(List.of("fastboot", "delete-logical-partition", "product_b"), outputLabel);
            processList.add(proc4);
            proc4.start();

            if (isCancelled) return;
            Platform.runLater(() -> outputLabel.setText("Прошивка system.."));
            var proc5 = new Shell(List.of("fastboot", "flash", "system", file.getAbsolutePath()), outputLabel);
            processList.add(proc5);
            proc5.start();

            if (isCancelled) return;
            System.out.println("Прошивка завершена, сбросьте настройки через recovery и перезагрузитесь в систему.");
        }).start();
    }

    public void flash() {
        if (selectFile.isCanceled(file)) {
            Platform.runLater(() -> outputLabel.setText(outputLabel.getText() + "\n" + "Выбор файла отменен!"));
            return;
        } else if (selectFile.getSize(file) > MAX_FILE_SIZE) {
            Platform.runLater(() -> outputLabel.setText(outputLabel.getText() + "\n" + "Файл слишком большой!"));
            return;
        } else if (selectFile.getSize(file) < MIN_FILE_SIZE) {
            Platform.runLater(() -> outputLabel.setText(outputLabel.getText() + "\n" + "Файл слишком маленький!"));
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
