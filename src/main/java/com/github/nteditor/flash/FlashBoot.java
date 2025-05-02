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

    private void setText(String text, boolean clean) {
        if (clean) {
            Platform.runLater(() -> outputLabel.setText(text));
        } else {
            Platform.runLater(() -> outputLabel.setText(outputLabel.getText() + "\n" + text));
        }
    }

    private void startFlash() {
        setText("Прошивка Boot\n" +
            "Выбран файл: " + file.getAbsolutePath(), true);

        new Thread(() -> {
            if (isCancelled) return;
            setText("Перезагрузка..", false);
            new Shell(List.of("fastboot", "reboot", "fastboot"), outputLabel)
                    .start();

            if (isCancelled) return;
            setText("Прошивка boot..", false);
            new Shell(List.of("fastboot", "flash", "boot", file.getAbsolutePath()), outputLabel)
                .start();

            if (isCancelled) return;
            setText("Готово!", false);
        }).start();
    }

    public void flash() {
        int MAX_FILE_SIZE = 100; // MB

        if (selectFile.isCanceled(file)) {
            setText("Выбор файла отменен!", true);
            return;
        } else if (selectFile.getSize(file) > MAX_FILE_SIZE) {
            setText("Файл слишком большой!", true);
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
