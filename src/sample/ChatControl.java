package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ChatControl implements Initializable {
    final String from = "server:";
    final String HOST = "127.0.0.1";
    final int PORT = 5000;
    DataInputStream in;
    DataOutputStream out;

    final Observable option = FXCollections.observableArrayList();
    String userRecived;
    @FXML
    TextField txt_textToSend;
    @FXML
    Button enviar;
    @FXML
    TextArea txtAreaChat;
    @FXML
    ListView llistaNicks = new ListView();

    Connection connection = Controller.databaseConneciton("adminadmin", "adminadmin");

    public void buscaUsersConectats() {
        ResultSet resultSet = null;
        Statement sentencia = null;
        Connection conexion = null;

        try {
            sentencia = connection.createStatement();
            //busca users conectados en la bd postgres
            resultSet = sentencia.executeQuery("select nick from users where connected='true'; ");

//            while (resultSet.next()) {
//                System.out.println(llistaNicks.getItems().add(resultSet.getString(1)));
//            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void enviarMissatge() {
        Socket sc = null;
        try {
            sc = new Socket(HOST, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String scString= String.valueOf(sc.getRemoteSocketAddress());
            System.out.println("scString = " + scString);
            scString= String.valueOf(sc.getRemoteSocketAddress());
            System.out.println("scString = " + scString);
            scString = String.valueOf(sc.getChannel());
            System.out.println("scString = " + scString);
            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());
            //aqui enviamos el msj lo recibimos y lo subimos
            Thread.currentThread().setName(userRecived);
            String missatge = Thread.currentThread().getName() + ": " + txt_textToSend.getText() + "\n";
            if (!missatge.isEmpty()) {
                out.writeUTF(missatge); // mando mensaje al servidor
                txt_textToSend.clear();
                missatge = in.readUTF(); // esperando mensaje del servidor

                txtAreaChat.appendText(missatge + "\n");
//                System.out.println(from + mensaje);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                sc.close(); // cierra el socket del servidor
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //lo primero que hace es buscar usuarios conectados
        //todo obserbable

        buscaUsersConectats();


    }

    public void enviaDada(String text) {
        userRecived = text;
    }
}
