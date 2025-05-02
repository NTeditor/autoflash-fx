package com.github.nteditor.flash;


import java.io.File;
import java.util.List;

import com.github.nteditor.Shell;

import javafx.application.Platform;
import javafx.scene.control.Label;


public class FlashGSI {
    private final SelectFile selectFile;
    private final File file;
    private final Label outputLabel;
    private static volatile boolean isCancelled = false;

    public FlashGSI(Label outputLabel) {
        this.outputLabel = outputLabel;
        this.selectFile = new SelectFile();
        this.file = selectFile.getFile();
    }

    private void rebootF2F() {
        Platform.runLater(() -> outputLabel.setText(outputLabel.getText() + "\n" + "Перезагрузка.."));
        new Shell(List.of("fastboot", "reboot", "fastboot"), outputLabel)
                .start();
    }

    private void eraseSystem() {
        Platform.runLater(() -> outputLabel.setText(outputLabel.getText() + "\n" + "Очистка system.."));
        new Shell(List.of("fastboot", "erase", "system"), outputLabel)
                .start();
    }

    private void deletePartition(String part) {
        Platform.runLater(() -> outputLabel.setText(outputLabel.getText() + "\n" + "Удаление product_a.."));
        new Shell(List.of("fastboot", "delete-logical-partition", part), outputLabel)
                .start();
    }


    private void flashSystem() {
        Platform.runLater(() -> outputLabel.setText(outputLabel.getText() + "\n" + "Прошивка system.."));
        new Shell(List.of("fastboot", "flash", "system", file.getAbsolutePath()), outputLabel)
                .start();
    }

    private void startFlash() {
        Platform.runLater(() -> outputLabel.setText("Прошивка GSI\n" +
            "Выбран файл: " + file.getAbsolutePath()));
        new Thread(() -> {
            if (isCancelled) return;
            rebootF2F();

            if (isCancelled) return;
            eraseSystem();
            
            if (isCancelled) return;
            deletePartition("product_a");

            if (isCancelled) return;
            deletePartition("product_b");

            if (isCancelled) return;
            flashSystem();
            
            if (isCancelled) return;
            Platform.runLater(() -> outputLabel.setText(outputLabel.getText() + "\n"
                    + "Прошивка завершена, сбросьте настройки через recovery и перезагрузитесь в систему."));
        }).start();
    }

    public void flash() {
        final int MIN_FILE_SIZE = 500; // MB
        final int MAX_FILE_SIZE = 7168; // MB

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
            if (isCancelled) setCancelled(false);
            startFlash();
        }
    }

    public static void setCancelled(boolean isCancelled) {
        FlashGSI.isCancelled = isCancelled;

    }
}
