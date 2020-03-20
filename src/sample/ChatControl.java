package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ChatControl implements Initializable {
    final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");


    @FXML
    TextField txt_textToSend;
    @FXML
    Button enviar;
    @FXML
    TextArea txtAreaChat;

    public String getNamUserRebutDeController() {
        return namUserRebutDeController;
    }

    public void setNamUserRebutDeController(String namUserRebutDeController) {
        this.namUserRebutDeController = namUserRebutDeController;
    }

    @FXML
    ListView<String> llistaNicks = new ListView<>();

    Connection connection = Controller.databaseConneciton("adminadmin", "adminadmin");

    LocalDateTime localDateTime; // la fecjha para el chat
    String namUserRebutDeController; // esta es nick del usuario que se a conectado al chat importante:; es el nick no el nombre de usuario

    public void buscaUsersConectats() {

        Statement sentencia = null;


        try {
            sentencia = connection.createStatement();
            //busca users conectados en la bd postgres
            ResultSet resultSet = sentencia.executeQuery("select nick from users where connected='true'; ");

            while (resultSet.next()) {
                System.out.println(llistaNicks.getItems().add(resultSet.getString(1)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void cercaNickDelUsuari(String nom) {


        try {
            Statement sentencia = connection.createStatement();
            //busca users conectados en la bd postgres
            ResultSet resultSet = sentencia.executeQuery("select nick from users where connected='true'; ");

            while (resultSet.next()) {
                System.out.println(llistaNicks.getItems().add(resultSet.getString(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void enviarText() {

        localDateTime = LocalDateTime.now();

        String dataFormateada = localDateTime.format(FORMATTER);

        txtAreaChat.appendText(getNamUserRebutDeController() + dataFormateada + " " + ": " + txt_textToSend.getText() + "\n");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //lo primero que hace es buscar usuarios conectados
        //todo obserbable

        buscaUsersConectats();


    }


}
