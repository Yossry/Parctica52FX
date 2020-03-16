package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 300, 380));
        primaryStage.setResizable(false);
        //esto hace que boton de minimizar i cerrar no salgan
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();

    }


    public static void main(String[] args) {

        launch(args);




    }


}

