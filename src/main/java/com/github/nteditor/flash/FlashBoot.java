package com.github.nteditor.flash;


import java.io.File;
import java.util.List;
import com.github.nteditor.Shell;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class FlashBoot {

    private final SelectFile selectFile;
    private final File file;
    private final Label outputLabel;
    private static volatile boolean isCancelled = false;


    public FlashBoot(Label outputLabel) {
        this.outputLabel = outputLabel;
        this.selectFile = new SelectFile();
        this.file = selectFile.getFile();
    }

    private void startFlash() {
        Platform.runLater(() -> outputLabel.setText("Прошивка Boot\n" +
            "Выбран файл: " + file.getAbsolutePath()));
        new Thread(() -> {
            if (isCancelled) return;
            Platform.runLater(() -> outputLabel.setText(outputLabel.getText() + "\n" + "Перезагрузка.."));
            new Shell(List.of("fastboot", "reboot", "fastboot"), outputLabel)
                    .start();

            if (isCancelled) return;
            Platform.runLater(() -> outputLabel.setText(outputLabel.getText() + "\n" + "Прошивка boot.."));
            new Shell(List.of("fastboot", "flash", "boot", file.getAbsolutePath()), outputLabel)
                .start();

            if (isCancelled) return;
            Platform.runLater(() -> outputLabel.setText(outputLabel.getText() + "\n" + "Готово!"));
        }).start();
    }

    public void flash() {
        int MAX_FILE_SIZE = 100; // MB

        if (selectFile.isCanceled(file)) {
            Platform.runLater(() -> outputLabel.setText(outputLabel.getText() + "\n" + "Выбор файла отменен!"));
            return;
        } else if (selectFile.getSize(file) > MAX_FILE_SIZE) {
            Platform.runLater(() -> outputLabel.setText(outputLabel.getText() + "\n" + "Файл слишком большой!"));
            return;
        } else {
            if (isCancelled) setCancelled(false);
            startFlash();
        }
    }

    public static void setCancelled(boolean isCancelled) {
        FlashBoot.isCancelled = isCancelled;

    }
}
