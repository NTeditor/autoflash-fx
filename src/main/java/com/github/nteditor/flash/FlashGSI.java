package com.github.nteditor.flash;


import java.util.List;

import com.github.nteditor.Shell;


public class FlashGSI {

    public void flash() {
        var selectFile = new SelectFile();
        var file = selectFile.getFile();
        if (selectFile.isCanceled(file)) {
            System.out.println("1");
            System.out.println(file);
            return;
        }
        if (selectFile.getSize(file) < 100) {
            System.out.println("Файл слишком маленький!");
            return;
        }
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

            System.out.println("0");
            System.out.println(file);
            System.out.println(file.getAbsolutePath());
        }).start();
    }
}
