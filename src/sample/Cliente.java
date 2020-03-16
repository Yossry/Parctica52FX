package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args) {
        final String from = "server:";
        final String HOST = "127.0.0.1";
        final int PORT = 5000;
        DataInputStream in;
        DataOutputStream out;



        try {



            Socket sc = new Socket(HOST, PORT);

            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());

            out.writeUTF("Hola desde el cliente"); // mando mensaje al servidor

            String mensaje = in.readUTF(); // esperando mensaje del servidor

            System.out.println(from + mensaje);

            sc.close(); // cierra el socket del servidor


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarMissatge(){

    }
}
