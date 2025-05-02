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

    private void setText(String text, boolean clean) {
        if (clean) {
            Platform.runLater(() -> outputLabel.setText(text));
        } else {
            Platform.runLater(() -> outputLabel.setText(outputLabel.getText() + "\n" + text));
        }
    }

    private void rebootF2F() {
        setText("Перезагрузка..", true);
        new Shell(List.of("fastboot", "reboot", "fastboot"), outputLabel)
                .start();
    }

    private void eraseSystem() {
        setText("Очистка system..", false);
        new Shell(List.of("fastboot", "erase", "system"), outputLabel)
                .start();
    }

    private void deletePartition(String part) {
        setText("Удаление product_a..", false);
        new Shell(List.of("fastboot", "delete-logical-partition", part), outputLabel)
                .start();
    }


    private void flashSystem() {
        setText("Прошивка system..", false);
        new Shell(List.of("fastboot", "flash", "system", file.getAbsolutePath()), outputLabel)
                .start();
    }

    private void startFlash() {
        setText("Прошивка GSI\n" +
            "Выбран файл: " + file.getAbsolutePath(), false);
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
            setText("Прошивка завершена, сбросьте настройки через recovery и перезагрузитесь в систему.", false);
        }).start();
    }

    public void flash() {
        final int MIN_FILE_SIZE = 500; // MB
        final int MAX_FILE_SIZE = 7168; // MB

        if (selectFile.isCanceled(file)) {
           setText("Выбор файла отменен!", false);
            return;
        } else if (selectFile.getSize(file) > MAX_FILE_SIZE) {
            setText("Файл слишком большой!", false);
            return;
        } else if (selectFile.getSize(file) < MIN_FILE_SIZE) {
            setText("Файл слишком маленький!", false);
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
