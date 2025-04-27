package com.github.nteditor.flash;


import java.io.File;
import java.util.List;

import com.github.nteditor.Shell;


public class FlashGSI {

    private final int MIN_FILE_SIZE = 500; // MB
    private final int MAX_FILE_SIZE = 7168; // MB

    private SelectFile selectFile;
    private File file;

    public FlashGSI() {
        this.selectFile = new SelectFile();
        this.file = selectFile.getFile();
    }

    private void startFlash() {
        System.out.println("Выбран файл: " + file.getAbsolutePath());

        new Thread(() -> {
            Shell shell = new Shell();
            shell.setCommand(List.of("fastboot", "reboot", "fastboot"));
            shell.start();
            System.out.println("Очистка system..");
            shell.setCommand(List.of("fastboot", "erase", "system"));
            shell.start();
            System.out.println("Удаление product_a..");
            shell.setCommand(List.of("fastboot", "delete-logical-partition", "product_a"));
            shell.start();
            System.out.println("Удаление product_b..");
            shell.setCommand(List.of("fastboot", "delete-logical-partition", "product_b"));
            shell.start();
            System.out.println("Прошивка system..");
            shell.setCommand(List.of("fastboot", "flash", "system", file.getAbsolutePath()));
            shell.start();
            System.out.println("Прошивка завершина, сбросте настройки через recovery и перезагрузитесь в систему.");
        }).start();
    }

    public void flash() {
        if (selectFile.isCanceled(file)) {
            System.err.println("Выбор файла отменен!");
            return;
        } else if (selectFile.getSize(file) > MAX_FILE_SIZE) {
            System.err.println("Файл слишком большой!");
            return;
        } else if (selectFile.getSize(file) < MIN_FILE_SIZE) {
            System.err.println("Файл слишком маленький!");
            return;
        } else {
            startFlash();
        }
    }
}
