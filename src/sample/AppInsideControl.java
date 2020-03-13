package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AppInsideControl implements Initializable {
    @FXML Label lbl_userName;
    @FXML TextField txt_userCerca;
    @FXML Button btn_CercaUser;
    @FXML Button btn_addUser;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void enviaDada(String user) {

        lbl_userName.setText(user);

    }
public void cercaUser(){

}

}
