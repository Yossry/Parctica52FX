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

public class AppInsideControl  {
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




    /**
     * s'utilizaria per buscar un usuari
     */
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

    /**
     * Dispara finestres de alerta
     * @param alertType tipus de missatge
     * @param info informacio del missatge
     * @param capcelera la capcelera del missatge
     * @param titul el titul de la finestre
     */

    public void mostraDialog(Alert.AlertType alertType, String info, String capcelera, String titul) {
        Alert alert = new Alert((alertType));
        alert.setContentText(info);
        alert.setHeaderText(capcelera);
        alert.showAndWait();
    }

    /**
     * s'utiliza per obrir la finestra que ens porta al formolari per donar d'alta un usuari
     */
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


    /**
     * s'utiliza per quan apretes al boto de tancar "x" , tanca el stage
     */
    @FXML
    public void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) btn_close.getScene().getWindow();
        // do what you have to do
        stage.close();
    }



}
