package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    Button btn_close;
    @FXML
    TextField txt_textToSend;
    @FXML
    Button enviar;
    @FXML
    TextArea txtAreaChat;

    public String getNickDelUsuariDelChat() {
        return nickDelUsuariDelChat;
    }

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
    String nickDelUsuariDelChat;

    public void buscaUsersConectats() {


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

    public String cercaNickDelUsuari(String nom) {


        try {
            Statement sentencia = connection.createStatement();
            //busca el nick del usuari logejat conectados en la bd postgres
            String user = getNamUserRebutDeController();
            System.err.println(user);
            ResultSet resultSet = sentencia.executeQuery("select nick from users where username='" + getNamUserRebutDeController() + "'; ");

            while (resultSet.next()) {
                nickDelUsuariDelChat = resultSet.getString("nick");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nickDelUsuariDelChat;
    }


    public void enviarText() {

        localDateTime = LocalDateTime.now();

        String dataFormateada = localDateTime.format(FORMATTER);


        String user = cercaNickDelUsuari(getNamUserRebutDeController());
        txtAreaChat.appendText(dataFormateada + " " + user + ": " + txt_textToSend.getText() + "\n");

        // teexa el lavel text per enviar text

        txt_textToSend.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //lo primero que hace es buscar usuarios conectados
        //todo obserbable i  poner en tru el usuario para que salga como conectado



        userUpdateStateConection(true);

    }

    public void userUpdateStateConection(boolean status) {
//todo
        //        try {
//            String SQLupdate = "update users set connected = '" + status + "' where username='" + getNamUserRebutDeController() + "';";
//            PreparedStatement insert1 = connection.prepareStatement(SQLupdate);
//            int update = insert1.executeUpdate();
//            connection.commit();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    public void closeButtonAction() {
        userUpdateStateConection(false);
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();
    }
}
