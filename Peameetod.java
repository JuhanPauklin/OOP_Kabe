package com.example.oop_kabe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.File;

import java.util.concurrent.atomic.AtomicBoolean;

public class Peameetod extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Kabe");

        Ruudustik ruudustik = new Ruudustik(); // loob uue ruudustiku isendi, milles on olemas vBox

        VBox vBox = ruudustik.getvBox(); // võtab ruudustiku isendist vBox'i

        Scene scene = new Scene(vBox, 512, 512);

        Audio player = new Audio(new File("Death or Sovngarde.wav"));
        AtomicBoolean muusikaMängib = new AtomicBoolean(false); //kuna me töötame threadidega ja meil on käimas kaks threadi (audio ja stage), siis peame kasutama atomatic booleani

        //KÕIK KLAHVIVAJUTUSED
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && muusikaMängib.get() == false) { //kui klaviatuuri vajutus oli "Enter" klahv ja muusikat ei mängita praegu
                muusikaMängib.set(true); //muudame meie boolean väärtust "true"-ks. Seda on vaja selleks, et me ei saaks mitu korda samat laulu mängida
                try { //proovime muusikat mängida
                    player.mängiMuusikat();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } //if lause

            else if (event.getCode() == KeyCode.ENTER && muusikaMängib.get() == true){ //kui muusika mängib, siis lõpetame
                muusikaMängib.set(false); //siis muudame booleani väärtust "false"-iks, et oleks võimalik uuesti muusikat mängida
                player.lõpetaMuusika(); //lõpetab muusika mängimise
            }  //else if
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.P && muusikaMängib.get() == true) { //kui klaviatuuri vajutus oli "P" klahv ja muusika mängib
                muusikaMängib.set(false); //siis muudame booleani väärtust "false"-iks, et oleks võimalik uuesti muusikat mängida
                player.paus(); //paneme muusika pausile
            }//if lause

            else if (event.getCode() == KeyCode.P && muusikaMängib.get() == false) {
                muusikaMängib.set(true); //muudame boolean väärtust jälle
                player.jätka(); //jätkame
            }
        });



        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) throws Exception {
        launch();
    }
}