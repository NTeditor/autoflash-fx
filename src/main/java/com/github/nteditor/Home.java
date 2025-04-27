package com.github.nteditor;


import java.net.URL;
import java.util.ResourceBundle;

import com.github.nteditor.flash.FlashBoot;
import com.github.nteditor.flash.FlashGSI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class Home {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    void flashBoot(ActionEvent event) {
        new FlashBoot().flash();
    }

    @FXML
    void flashGSI(ActionEvent event) {
        new FlashGSI().flash();
    }

    @FXML
    void rebootF2R(ActionEvent event) {
        new Reboot("recovery").rebootF2();
    }
    
    @FXML
    void rebootF2S(ActionEvent event) {
        new Reboot("system").rebootF2();
    }

    @FXML
    void rebootS2F(ActionEvent event) {
        new Reboot("fastboot").rebootS2();
    }

    @FXML
    void rebootS2R(ActionEvent event) {
        new Reboot("recovery").rebootS2();
    }

    @FXML
    void initialize() {
        assert flashBoot != null : "fx:id=\"flashBoot\" was not injected: check your FXML file 'Untitled'.";
        assert flashGSI != null : "fx:id=\"flashGSI\" was not injected: check your FXML file 'Untitled'.";
        assert rebootF2R != null : "fx:id=\"rebootF2R\" was not injected: check your FXML file 'Untitled'.";
        assert rebootF2S != null : "fx:id=\"rebootF2S\" was not injected: check your FXML file 'Untitled'.";
        assert rebootS2F != null : "fx:id=\"rebootS2F\" was not injected: check your FXML file 'Untitled'.";
        assert rebootS2R != null : "fx:id=\"rebootS2R\" was not injected: check your FXML file 'Untitled'.";

    }

}