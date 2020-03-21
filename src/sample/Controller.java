package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    final static String userSQL = "adminadmin";
    final static String passSQL = "adminadmin";
    @FXML
    Button btnSignin;
    @FXML
    TextField txtUser;
    @FXML
    PasswordField txtPassword;
    @FXML
    Label lbl_error;
    @FXML
    Button btn_close;

    ChatControl chatControl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public static Connection databaseConneciton(String user, String password) {
        Connection conexion = null;
        try {
            Class.forName("org.postgresql.Driver");            //Cargar el driver
            conexion = DriverManager.getConnection            // Establecemos la conexion con la BD
                    ("jdbc:postgresql://localhost/dbtest", userSQL, passSQL);//     ("jdbc:postgresql://localhost/dbtest", "admin", "admin"); // AQUEST ES PER LA ESCOLA
        } catch (ClassNotFoundException e) {e.getMessage();} catch (SQLException e) {e.getMessage();}
        return conexion;
    }
    public Boolean logIn() {
        String[] errorsString = new String[]{"Tens que omplir tots els camps","User o password mal introduit"}; //llistat de errors
        ResultSet resultSet = null;
        Statement sentencia = null;
        Connection conexion;
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
                    // showNewScreen("appInside.fxml");
                    showNewScreen("chat.fxml");

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

    public void showNewScreen(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = (Parent) loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();

        chatControl = loader.getController();
        chatControl.setNamUserRebutDeController(txtUser.getText());
    }


    @FXML
    private void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) btn_close.getScene().getWindow();
        // do what you have to do
        stage.close();
    }


}


