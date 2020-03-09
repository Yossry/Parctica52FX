package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Connection conn;
    public PreparedStatement pst = null;
    public ResultSet resultSet = null;
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

    public static Connection databaseConneciton() {
        Connection conexion = null;
        try {
            //Cargar el driver
            Class.forName("org.postgresql.Driver");
            // Establecemos la conexion con la BD
            conexion = DriverManager.getConnection
                    ("jdbc:postgresql://localhost/dbtest", "adminadmin", "adminadmin");
            return conexion;
        } catch (ClassNotFoundException e) {
            e.getMessage();
        } catch (SQLException e) {
            e.getMessage();
        }
        return conexion;
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
                mostraDialog(Alert.AlertType.CONFIRMATION, "Login Correcte, benvingut " + user, null, null);

            }


            resultSet.close();// Cerrar ResultSet
            sentencia.close();// Cerrar Statement
            conexion.close();//Cerrar conexion

        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        try {
//            Parent appinside = FXMLLoader.load(getClass().getResource("appInside.fxml"));
//            Scene scene = new Scene(appinside);
//
//            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//
//            window.setScene(scene);
//
//            window.show();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void mostraDialog(Alert.AlertType alertType, String info, String capcelera, String titul) {
        Alert alert = new Alert((alertType));
        alert.setContentText(info);
        alert.setHeaderText(capcelera);
        alert.showAndWait();
    }


}


