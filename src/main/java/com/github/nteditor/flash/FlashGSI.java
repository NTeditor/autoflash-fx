package com.github.nteditor.flash;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.github.nteditor.Shell;

import javafx.scene.control.Label;


public class FlashGSI {

    private final int MIN_FILE_SIZE = 500; // MB
    private final int MAX_FILE_SIZE = 7168; // MB

    private SelectFile selectFile;
    private File file;
    private Label outputLabel;
    private List<Shell> processes = new ArrayList<>();
    private volatile boolean isCancelled = false;

    public FlashGSI(Label outputLabel) {
        this.outputLabel = outputLabel;
        this.selectFile = new SelectFile();
        this.file = selectFile.getFile();
    }

    private void startFlash() {
        System.out.println("Выбран файл: " + file.getAbsolutePath());
        new Thread(() -> {
            var proc1 = new Shell(List.of("fastboot", "reboot", "fastboot"), outputLabel);
            processes.add(proc1);
            proc1.start();

            if (isCancelled) return;
            System.out.println("Очистка system..");
            var proc2 = new Shell(List.of("fastboot", "erase", "system"), outputLabel);
            processes.add(proc2);
            proc2.start();

            if (isCancelled) return;
            System.out.println("Удаление product_a..");
            var proc3 = new Shell(List.of("fastboot", "delete-logical-partition", "product_a"), outputLabel);
            processes.add(proc3);
            proc3.start();

            if (isCancelled) return;
            System.out.println("Удаление product_b..");
            var proc4 = new Shell(List.of("fastboot", "delete-logical-partition", "product_b"), outputLabel);
            processes.add(proc4);
            proc4.start();

            if (isCancelled) return;
            System.out.println("Прошивка system..");
            var proc5 = new Shell(List.of("fastboot", "flash", "system", file.getAbsolutePath()), outputLabel);
            processes.add(proc5);
            proc5.start();

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
            if (isCancelled) return;
            startFlash();
        }
    }

    public void stop() {
        isCancelled = true;
        for (Shell shell : processes) {
            shell.stop();
        }
        processes.clear();
    }
}
