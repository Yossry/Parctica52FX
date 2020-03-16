package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AppInsideControl implements Initializable {
    String user = null;
    String pass = null;
    @FXML
    Label lbl_userName;
    @FXML
    TextField txt_userCerca;
    @FXML
    Button btn_CercaUser;
    @FXML
    Button btn_addUser;
    @FXML
    Button btn_close;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void enviaDada(String user, String pass) {

        lbl_userName.setText(user);
        this.user = user;
        this.pass = pass;
    }

    public void cercaUser() {
        ResultSet resultSet = null;
        Statement sentencia = null;
        Connection connection = Controller.databaseConneciton(user, pass);
        try {
            sentencia = connection.createStatement();
            resultSet = sentencia.executeQuery("select nom from users where nom='" + txt_userCerca.getText().trim() + "' and connected=true;");
            if (!resultSet.next()) {
                mostraDialog(Alert.AlertType.WARNING, "Usuari No Conectat", null, null);
            } else {
                mostraDialog(Alert.AlertType.CONFIRMATION, "Usuari Conectat", null, null);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void mostraDialog(Alert.AlertType alertType, String info, String capcelera, String titul) {
        Alert alert = new Alert((alertType));
        alert.setContentText(info);
        alert.setHeaderText(capcelera);
        alert.showAndWait();
    }


    public void addUserScreen() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signup.fxml"));
        Parent root = null;
        try {
            root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) btn_close.getScene().getWindow();
        // do what you have to do
        stage.close();
    }



}
