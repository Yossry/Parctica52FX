package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    public Scene loginScene, mainWindowsScene;
    public Stage primaryStage = null;

//Crecio de la taula
//    CREATE TABLE users (
//            id SERIAL,first varchar,last varchar ,email varchar,
//            username varchar,
//            password varchar);
//
    public static Connection databaseConneciton(String user, String password) {
        Connection conexion = null;
        try {
            //Cargar el driver
            Class.forName("org.postgresql.Driver");
            // Establecemos la conexion con la BD
            conexion = DriverManager.getConnection
               //     ("jdbc:postgresql://localhost/dbtest", "adminadmin", "adminadmin");
                    ("jdbc:postgresql://localhost/dbtest", "admin", "admin"); // AQUEST ES PER LA ESCOLA
        } catch (ClassNotFoundException e) {
            e.getMessage();
        } catch (SQLException e) {
            e.getMessage();
        }
        return conexion;
    }

    public Boolean logIn() {
        ResultSet resultSet = null;
        Statement sentencia = null;
        Connection conexion = null;
        String user = txtUser.getText().toString();
        String password = txtPassword.getText().toString();
        conexion = databaseConneciton(user, password);
        try {
            // Preparamos la consulta
            sentencia = conexion.createStatement();
            resultSet = sentencia.executeQuery("SELECT * FROM users where username = '" + user + "' and password = '" + password + "';");
            if ((txtUser.getText().isEmpty() || txtPassword.getText().isEmpty())) {
                //mostraDialog(Alert.AlertType.WARNING, "falten camps per omplir", null, "TILUL");
                lbl_error.setText("tens que omplir els camps");
                System.err.println("tens que omplir els camps");
                return false;

            } else {
                if (!resultSet.next()) {
                    lbl_error.setText("usuari o password mal introduit");
                    System.err.println("Password mal introduit");
                    return false;
                } else {
                    lbl_error.setText("");
                    System.out.println("Login correcte");
                    mostraDialog(Alert.AlertType.CONFIRMATION, "Login Correcte, benvingut " + user, null, null);

                    FXMLLoader loader = new FXMLLoader( (getClass().getResource("appInside.fxml")));
                    Scene scene;

                    scene=new Scene(loader.load(),800,600);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                }
            }

        } catch (
                SQLException e) {
            e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();// Cerrar ResultSet
                sentencia.close();// Cerrar Statement
                conexion.close();//Cerrar conexion
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return null;
    }




    public void mostraDialog(Alert.AlertType alertType, String info, String capcelera, String titul) {
        Alert alert = new Alert((alertType));
        alert.setContentText(info);
        alert.setHeaderText(capcelera);
        alert.showAndWait();
    }


}


