package com.github.nteditor.flash;

import java.io.File;

import javafx.stage.FileChooser;

public class FlashBoot {
    public void fileChooser() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("IMG Образ", "*.img"),
            new FileChooser.ExtensionFilter("BIN Образ", "*.bin"),
            new FileChooser.ExtensionFilter("Все Файлы", "*.*"));

        File selectedFile = fileChooser.showOpenDialog(null);
        System.out.println(selectedFile);
    }

    

    public void flash() {
        
    }
}
