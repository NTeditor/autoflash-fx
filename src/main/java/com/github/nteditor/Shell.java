package com.github.nteditor;

import java.io.IOException;
import java.lang.ProcessBuilder;
import java.util.List;

public class Shell {
    private List<String> command;
    public Shell(List<String> command) {
        this.command = command;
    }

    public Shell() {
        this.command = null;
    }

    private void runCommand(List<String> command) {
        try {
            new ProcessBuilder(command)
                .inheritIO()
                .start()
                .waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public void setCommand(List<String> command) {
        this.command = command;
    }

    public void start() {
        if (command == null) {
            System.out.println("Command is not set.");
            throw new IllegalStateException("Command is not set.");
        }
        runCommand(command);
    }

}
