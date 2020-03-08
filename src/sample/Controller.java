package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    Button btnSignin;
    @FXML
    Button btnSignup;

    @FXML
    TextField txtUser;
    @FXML
    PasswordField txtPassword;
    @FXML
    Label lbl_error;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void logIn() {
        if ((txtUser.getText().isEmpty() || txtPassword.getText().isEmpty())) {
            mostraDialog(Alert.AlertType.WARNING, "falten camps per omplir", null, "TILUL");
            lbl_error.setText("tens que omplir els camps");
        }


        String user = txtUser.getText().toString();
        String password = txtPassword.getText().toString();


        try {
            //Cargar el driver
            Class.forName("org.postgresql.Driver");
            // Establecemos la conexion con la BD
            Connection conexion = DriverManager.getConnection
                    ("jdbc:postgresql://localhost/dbtest", "adminadmin", "adminadmin");

            // Preparamos la consulta
            Statement sentencia = conexion.createStatement();
            ResultSet resultSet = sentencia.executeQuery("SELECT * FROM users where username = '" + user + "' and password = '" + password + "';");


            if (!resultSet.next()) {
                lbl_error.setText("usuari o password mal introduit");
                System.err.println("Password mal introduit");

            } else {
                System.out.println("Login correcte");
                mostraDialog(Alert.AlertType.CONFIRMATION, "Login Correcte, benvingut "+ user, null, "Correcte");




            }


            resultSet.close();// Cerrar ResultSet
            sentencia.close();// Cerrar Statement
            conexion.close();//Cerrar conexion

        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void mostraDialog(Alert.AlertType alertType, String info, String capcelera, String titul) {
        Alert alert = new Alert((alertType));
        alert.setContentText(info);
        alert.setHeaderText(capcelera);
        alert.showAndWait();
    }
    private  void goApp (){
        try {
            Parent appinside = FXMLLoader.load(getClass().getResource("sample/appInside.fxml"));
            Scene scene = new Scene(appinside);
            Stage window = new Stage();
            window.setScene(scene);
            window.show();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


