package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML Button btnSignin;
    @FXML TextField txtUser;
    @FXML PasswordField txtPassword;
    @FXML Label lbl_error;
    @FXML Button btn_close;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    //Crecio de la taula
//    CREATE TABLE users (id SERIAL,first varchar,last varchar ,email varchar,username varchar,password varchar,level int,connected boolean);
//    insert into users(first,last,email,username,password,level,connected) values ('Josep M','Olmos','olmosmoog@gmail.com','olmos','olmos',10,true);
//    insert into users(first,last,email,username,password,level,connected) values ('William','Areas','will@gmail.com','william','bbmas',9,false);
//    insert into users(first,last,email,username,password,level,connected) values ('Roser ','Olmos','roser@gmail.com','roser','olmos',5,true);


    public static Connection databaseConneciton(String user, String password) {
        Connection conexion = null;
        try {
            //Cargar el driver
            Class.forName("org.postgresql.Driver");
            // Establecemos la conexion con la BD
            conexion = DriverManager.getConnection
                    ("jdbc:postgresql://localhost/dbtest", "adminadmin", "adminadmin");
            //     ("jdbc:postgresql://localhost/dbtest", "admin", "admin"); // AQUEST ES PER LA ESCOLA
        } catch (ClassNotFoundException e) {
            e.getMessage();
        } catch (SQLException e) {
            e.getMessage();
        }
        return conexion;
    }

    public Boolean logIn() {
        String[] errorsString = new String[2];
        errorsString[0] = "Tens que omplir tots els camps";
        errorsString[1] = "User o password mal introduit";
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
                lbl_error.setText(errorsString[0]);
                System.err.println(errorsString[0]);
                return false;

            } else {
                if (!resultSet.next()) {
                    lbl_error.setText(errorsString[1]);
                    System.err.println(errorsString[1]);
                    return false;
                } else {
                    lbl_error.setText("");
                    System.out.println("Login correcte");
                    //mostraDialog(Alert.AlertType.CONFIRMATION, "Login Correcte, benvingut " + user, "Login Correcte", null);
                    lbl_error.setText("USUARI / LOGIN CORRECTE");
                    llençaPantalla("appInside.fxml");
                }
            }

        } catch (
                SQLException e) {
            e.getMessage();
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

    public void llençaPantalla(String nomPantalla) {

        FXMLLoader loader = new FXMLLoader((getClass().getResource(nomPantalla)));
        Scene scene;

        try {
            scene = new Scene(loader.load(), 300, 380);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void signUp() {
        llençaPantalla("signup.fxml");

    }

    @FXML
    private void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) btn_close.getScene().getWindow();
        // do what you have to do
        stage.close();
    }


    public void mostraDialog(Alert.AlertType alertType, String info, String capcelera, String titul) {
        Alert alert = new Alert((alertType));
        alert.setContentText(info);
        alert.setHeaderText(capcelera);
        alert.showAndWait();
    }


}


