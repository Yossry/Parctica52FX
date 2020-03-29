package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class ControllerSignUP implements Initializable {

    @FXML
    TextField txtFirstName;
    @FXML
    TextField txtLastName;
    @FXML
    TextField txtUserName;
    @FXML
    PasswordField txtPassword;
    @FXML
    PasswordField txtRetype;
    @FXML
    TextField txtEmail;
    @FXML
    Label lbl_error;
   @FXML
    Button btn_close;
    @FXML
    GridPane grid;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    //todo metodo para dar de alta un usuario
    public void signUP() {
        for (Node node : this.grid.getChildren()) {
            if (node instanceof TextField || node instanceof PasswordField) {
                if (node == null) {
                    lbl_error.setText("Falta omplir algun dels camps");
                }
            }
            if (txtPassword.getText() != txtRetype.getText()) {
                lbl_error.setText("Els passwords no coincideixen");
            }
            insertaUser();
        }


    }

    /**
     * Lo utilizamos para conectarse a la base de datos de postgres
     * @param user nom del usuari
     * @param password password del usuari
     * @return null;
     */
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

    @FXML
    /**
     * S'utiliza per tancar la finestre
     */
    private void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) btn_close.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    /**
     * s'utiliza per insertar un usuari la base de dades
     */
    private void insertaUser() {
        String first = txtFirstName.getText();
        String last = txtLastName.getText();
        String email = txtEmail.getText();
        String user = txtUserName.getText();
        String password = txtPassword.getText();
        ResultSet resultSet = null;
        Statement sentencia = null;
        Connection conexion = null;
        conexion = databaseConneciton(user, password);
        try {
            sentencia = conexion.createStatement();

            resultSet = sentencia.executeQuery("SELECT * FROM users where username = '" + user + "';");
            if (!resultSet.next()) {
                resultSet = sentencia.executeQuery("insert into users (first,last,email,username,password) values ('" + first + "','" + last + "','" + email + "','" + user + "','" + password + "');");


                FXMLLoader loader = new FXMLLoader( (getClass().getResource("appInside.fxml")));
                Scene scene;

                scene=new Scene(loader.load(),600,500);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

            }else {
                System.err.println("el usuaria ja existeix");
                lbl_error.setText("el usuaria ja existeix");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    }

}
