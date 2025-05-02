package com.github.nteditor;


import java.net.URL;
import java.util.ResourceBundle;

import com.github.nteditor.flash.FlashBoot;
import com.github.nteditor.flash.FlashGSI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

public class Home {
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
    void cancel() {
        Shell.stop();
        FlashGSI.setCancelled(true);
        FlashBoot.setCancelled(true);
        outputLabel.setText("Все дочернии процессы остановлены.");
    }

    @FXML
    void flashBoot() {
        new FlashBoot(outputLabel).flash();
    }

    @FXML
    void flashGSI() {
        new FlashGSI(outputLabel).flash();
    }

    @FXML
    void rebootF2R() {
        new Reboot("recovery", outputLabel).rebootF2();
    }
    
    @FXML
    void rebootF2S() {
        new Reboot("system", outputLabel).rebootF2();
    }

    @FXML
    void rebootS2F() {
        new Reboot("fastboot", outputLabel).rebootS2();
    }

    @FXML
    void rebootS2R() {
        new Reboot("recovery", outputLabel).rebootS2();
    }

    @FXML
    void getADBDevices() {
        new Devices(outputLabel).getADBDevices();
    }
    
    @FXML
    void getFastbootDevices() {
        new Devices(outputLabel).getFastbootDevices();
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