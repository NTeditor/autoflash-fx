package com.github.nteditor.flash;

import java.io.File;

import javafx.stage.FileChooser;

public class FlashBoot {
    private File fileChooser() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("IMG Образ", "*.img"),
            new FileChooser.ExtensionFilter("BIN Образ", "*.bin"),
            new FileChooser.ExtensionFilter("Все Файлы", "*.*"));

        return fileChooser.showOpenDialog(null);
    }

    private boolean isCanceled(File imgPath) {
        if (imgPath == null) return true;
        return false;
    }

    public boolean flash() {
        return isCanceled(fileChooser());
    }
}
