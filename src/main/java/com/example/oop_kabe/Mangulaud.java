package com.example.oop_kabe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Mangulaud extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Kabe");

        Ruudustik ruudustik = new Ruudustik(); // loob uue ruudustiku isendi, milles on olemas vBox

        VBox vBox = ruudustik.getvBox(); // võtab ruudustiku isendist vBox'i

        Scene scene = new Scene(vBox, 512, 512);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}