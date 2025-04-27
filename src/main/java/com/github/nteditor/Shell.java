package com.github.nteditor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Shell {
    private List<String> command;
    private Label outputLabel;
    private Process process;

    public Shell(List<String> command, Label outputLabel) {
        this.command = command;
        this.outputLabel = outputLabel;
    }

    private void runCommand(List<String> command) {
        try {
            process = new ProcessBuilder(command)
                .redirectErrorStream(true)
                .start();


            try (var reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                Platform.runLater(() -> outputLabel.setText(""));
                while ((line = reader.readLine()) != null) {
                    String finalLine = line;
                    Platform.runLater(() ->
                        outputLabel.setText(outputLabel.getText() + "\n" + finalLine));
                }
            }

            int exitCode = process.waitFor();
            Platform.runLater(() ->
                outputLabel.setText(outputLabel.getText() + "\nProcess exited with code: " + exitCode));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            Platform.runLater(() -> outputLabel.setText(outputLabel.getText() + "\nError: " + e.getMessage()));
        }
    }

    public void setCommand(List<String> command) {
        this.command = command;
    }

    public void start() {
        if (command == null) {
            System.out.println("Command is not set.");
            throw new NullPointerException("Command is not set.");
        }
        runCommand(command);
    }

    public void stop() {
        if (process != null && process.isAlive()) {
            process.destroy();
        }
    }

}
