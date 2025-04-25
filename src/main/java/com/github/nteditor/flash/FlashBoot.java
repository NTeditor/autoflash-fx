package com.github.nteditor.flash;


import java.util.List;
import com.github.nteditor.Shell;

public class FlashBoot {

    public void flash() {
        var selectFile = new SelectFile();
        var file = selectFile.getFile();
        if (selectFile.isCanceled(file)) {
            System.out.println("1");
            System.out.println(file);
            return;
        }
        new Thread(() -> {
            Shell shell = new Shell();
            shell.setCommand(List.of("fastboot", "reboot", "fastboot"));
            shell.start();
            shell.setCommand(List.of("fastboot", "flash", "boot", file.getAbsolutePath()));
            shell.start();

            System.out.println("0");
            System.out.println(file);
            System.out.println(file.getAbsolutePath());
        }).start();
    }
}
