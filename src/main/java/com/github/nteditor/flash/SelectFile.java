package com.github.nteditor.flash;

import java.io.File;

import javafx.stage.FileChooser;

public class SelectFile {
    private File fileChooser() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("IMG Образ", "*.img"),
            new FileChooser.ExtensionFilter("BIN Образ", "*.bin"));

        return fileChooser.showOpenDialog(null);
    }

    private boolean isCancel(File imgPath) {
        if (imgPath == null) return true;
        return false;
    }

    public boolean isCanceled(File imgPath) {
        return isCancel(imgPath);
    }

    public File getFile() {
        return fileChooser();
    }
}
