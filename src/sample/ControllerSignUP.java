package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
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
    Button btnSignup;
    @FXML
    Button btnCancel;
    @FXML
    GridPane grid;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

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


        }


    }
}
