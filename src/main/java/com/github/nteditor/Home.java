package com.github.nteditor;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.github.nteditor.flash.FlashBoot;
import com.github.nteditor.flash.FlashGSI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

public class Home {

    private List<Shell> runningProcesses = new ArrayList<>();
    private List<Reboot> runningProcessesReboot = new ArrayList<>();
    private List<FlashGSI> runningProcessesGSI = new ArrayList<>();
    private List<FlashBoot> runningProcessesBoot = new ArrayList<>();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label outputLabel;

    @FXML
    private MenuItem flashBoot;

    @FXML
    private MenuItem flashGSI;

    @FXML
    private MenuItem rebootF2R;

    @FXML
    private MenuItem rebootF2S;

    @FXML
    private MenuItem rebootS2F;

    @FXML
    private MenuItem rebootS2R;

    @FXML
    private MenuItem fastbootDevices;

    @FXML
    private MenuItem adbDevices;

    @FXML
    private Button cancel;

    @FXML
    void cancel(ActionEvent event) {
        for (FlashGSI shell : runningProcessesGSI) {
            shell.stop();
        }
        
        for (FlashBoot shell : runningProcessesBoot) {
            shell.stop();
        }
 
        for (Reboot shell : runningProcessesReboot) {
            shell.stop();
        }
        
        for (Shell shell : runningProcesses) {
            shell.stop();
        }

        runningProcesses.clear();
        runningProcessesReboot.clear();
        runningProcessesGSI.clear();
        runningProcessesBoot.clear();
        outputLabel.setText("Все дочернии процессы остановлены.");
    }

    @FXML
    void flashBoot(ActionEvent event) {
        
        var process = new FlashBoot(outputLabel);
        runningProcessesBoot.add(process);
        process.flash();
    }

    @FXML
    void flashGSI(ActionEvent event) {
        var process = new FlashGSI(outputLabel);
        runningProcessesGSI.add(process);
        process.flash();
    }

    @FXML
    void rebootF2R(ActionEvent event) {
        var process = new Reboot("recovery", outputLabel);
        runningProcessesReboot.add(process);
        process.rebootF2();
    }
    
    @FXML
    void rebootF2S(ActionEvent event) {
        var process = new Reboot("system", outputLabel);
        runningProcessesReboot.add(process);
        process.rebootF2();
    }

    @FXML
    void rebootS2F(ActionEvent event) {
        var process = new Reboot("fastboot", outputLabel);
        runningProcessesReboot.add(process);
        process.rebootS2();
    }

    @FXML
    void rebootS2R(ActionEvent event) {
        var process = new Reboot("recovery", outputLabel);
        runningProcessesReboot.add(process);
        process.rebootS2();
    }

    @FXML
    void getADBDevices(ActionEvent event) {
        Platform.runLater(() -> outputLabel.setText("Устройства в режиме adb:"));
        new Thread(() -> {
            var process = new Shell(List.of("adb", "devices"), outputLabel);
            runningProcesses.add(process);
            process.start();
        }).start();  
    }
    
    @FXML
    void getFastbootDevices(ActionEvent event) {
        Platform.runLater(() -> outputLabel.setText("Устройства в режиме fastboot:"));
        new Thread(() -> {
            var process = new Shell(List.of("fastboot", "devices"), outputLabel);
            runningProcesses.add(process);
            process.start();
        }).start();
    }

    @FXML
    void initialize() {
        assert flashBoot != null : "fx:id=\"flashBoot\" was not injected: check your FXML file 'home.fxml'.";
        assert flashGSI != null : "fx:id=\"flashGSI\" was not injected: check your FXML file 'home.fxml'.";
        assert rebootF2R != null : "fx:id=\"rebootF2R\" was not injected: check your FXML file 'Home.fxml'.";
        assert rebootF2S != null : "fx:id=\"rebootF2S\" was not injected: check your FXML file 'Home.fxml'.";
        assert rebootS2F != null : "fx:id=\"rebootS2F\" was not injected: check your FXML file 'Home.fxml'.";
        assert rebootS2R != null : "fx:id=\"rebootS2R\" was not injected: check your FXML file 'Home.fxml'.";
        assert adbDevices != null : "fx:id=\"adbDevices\" was not injected: check your FXML file 'Home.fxml'.";
        assert fastbootDevices != null : "fx:id=\"fastbootDevices\" was not injected: check your FXML file 'Home.fxml'.";
        assert outputLabel != null : "fx:id=\"outputLabel\" was not injected: check your FXML file 'Home.fxml'.";

    }

}