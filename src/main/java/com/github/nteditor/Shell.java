package com.github.nteditor;

import java.io.IOException;
import java.lang.ProcessBuilder;

public class Shell {
    private String[] command;
    public Shell(String[] command) {
        this.command = command;
    }

    private void runCommand(String[] command) {
        try {
            new ProcessBuilder(command)
            .inheritIO()
            .start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        runCommand(command);
    }

}
