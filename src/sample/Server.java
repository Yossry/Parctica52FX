package sample;

import javafx.fxml.Initializable;

import javax.xml.ws.spi.http.HttpExchange;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Server {


    public static void main(String[] args) {


        final int PUERTO = 5000;
        final String from = "cliente: ";
        ServerSocket servidor = null;
        Socket sc = null;
        ArrayList<String> listClientsip = new ArrayList();
        ArrayList<String> listClientshost = new ArrayList();

        try {

            servidor = new ServerSocket(PUERTO);
            System.out.println("servider iniciado");
            DataInputStream in;
            DataOutputStream out;


            while (true) {
HttpExchange exchange = null;
                sc = servidor.accept(); // metodo que se queda esperando a que alguien se conecte, pusieramos codigo despues no se ejecuta hasta que no se conecta alguien
                          //si pasa del metodo accept() quiere decir que el cliente se a conectado
                System.out.println("cliente conectado: ");



                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());

                String mensaje = in.readUTF();// esta esperando a recibir un mensaje

                //System.out.println(from + mensaje);

                out.writeUTF(mensaje); //envia un mensaje al cliente

                System.out.println("cliente desconectado");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            try {
                sc.close();//cierra la conexion.... IMPORTANTE CREO QUE CIERRA LA DEL CLIENTE
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
