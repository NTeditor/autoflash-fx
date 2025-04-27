package com.github.nteditor;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.github.nteditor.flash.FlashBoot;
import com.github.nteditor.flash.FlashGSI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;

public class Home {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ProgressBar loading;

    @FXML
    private Label output;

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
    private MenuItem isFastbootConnect;

    @FXML
    private MenuItem isADBConnect;

    @FXML
    void flashBoot(ActionEvent event) {
        new FlashBoot(output).flash();
    }

    @FXML
    void flashGSI(ActionEvent event) {
        new FlashGSI(output).flash();
    }

    @FXML
    void rebootF2R(ActionEvent event) {
        new Reboot("recovery", output).rebootF2();
    }
    
    @FXML
    void rebootF2S(ActionEvent event) {
        new Reboot("system", output).rebootF2();
    }

    @FXML
    void rebootS2F(ActionEvent event) {
        new Reboot("fastboot", output).rebootS2();
    }

    @FXML
    void rebootS2R(ActionEvent event) {
        new Reboot("recovery", output).rebootS2();
    }

    @FXML
    void isADBConnect(ActionEvent event) {
        new Thread(() -> {
            new Shell(List.of("adb", "devices"), output).start();
        }).start();
    }
    
    @FXML
    void isFastbootConnect(ActionEvent event) {
        new Thread(() -> {
            System.out.println("Если вы не получили список устройств, то устройство не обнаружено.");
            new Shell(List.of("fastboot", "devices"), output).start();
        }).start();
    }

    @FXML
    void initialize() {
        assert flashBoot != null : "fx:id=\"flashBoot\" was not injected: check your FXML file 'Untitled'.";
        assert flashGSI != null : "fx:id=\"flashGSI\" was not injected: check your FXML file 'Untitled'.";
        assert rebootF2R != null : "fx:id=\"rebootF2R\" was not injected: check your FXML file 'Untitled'.";
        assert rebootF2S != null : "fx:id=\"rebootF2S\" was not injected: check your FXML file 'Untitled'.";
        assert rebootS2F != null : "fx:id=\"rebootS2F\" was not injected: check your FXML file 'Untitled'.";
        assert rebootS2R != null : "fx:id=\"rebootS2R\" was not injected: check your FXML file 'Untitled'.";
        assert isADBConnect != null : "fx:id=\"adbDevice\" was not injected: check your FXML file 'Untitled'.";
        assert isFastbootConnect != null : "fx:id=\"adbDevice\" was not injected: check your FXML file 'Untitled'.";
        assert loading != null : "fx:id=\"loading\" was not injected: check your FXML file 'Untitled'.";
        assert output != null : "fx:id=\"output\" was not injected: check your FXML file 'Untitled'.";

    }

}