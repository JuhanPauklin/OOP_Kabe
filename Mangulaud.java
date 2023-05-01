package com.example.oop_kabe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Mangulaud extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Kabe");

        Ruudustik ruudustik = new Ruudustik(); // loob uue ruudustiku isendi, milles on olemas vBox

        VBox vBox = ruudustik.getvBox(); // võtab ruudustiku isendist vBox'i

        Scene scene = new Scene(vBox, 512, 512);

        AtomicBoolean muusikaMängib = new AtomicBoolean(false); //kuna me töötame threadidega ja meil on käimas kaks threadi (audio ja stage), siis peame kasutama atomatic booleani
        //AtomicReference<Audio> player = new AtomicReference<>(new Audio(new File("Death or Sovngarde.wav")));

        List<Audio> laulud = looMuusikaList();
        List<Audio> hetkelMängiv = new ArrayList<>(); //siia paneme selle laulu, mis on hetkel mängimas
        //KÕIK NUPUVAJUTUSED-----------------------------------------------------------------------------------------------
        ruudustik.getPlayNupp().setOnMouseClicked(event -> { //kui ruudustikus valge ala peal oleva nupu peale vajutati
            File antudfail = new File (ruudustik.getTekst()+".wav"); //leiame tekstivälja väärtuse ja teeme temast faili
            //nüüd vaatame, kas meie audio failis on olemas selline laul
            for (int i = 0; i < laulud.size(); i++) {
                if (laulud.get(i).getFail().equals(antudfail)) { //kui me leidsime sellise, siis
                    if (muusikaMängib.get() == false) { //kui muusika pole mängimas, paneme käima
                        muusikaMängib.set(true);
                        hetkelMängiv.add(laulud.get(i)); //jätame selle faili nimetuse meelde
                        try { //proovime muusikat mängida
                            laulud.get(i).mängiMuusikat();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }//muusikamängib if lause
                    else if (muusikaMängib.get() == true) { //kui aga muusika on mängimas, peame praeguse laulu ennem kinni panema
                        for (int j = 0; j < hetkelMängiv.size(); j++) {
                            hetkelMängiv.get(i).lõpetaMuusika();
                        }
                        hetkelMängiv.add(laulud.get(i)); //jätame selle faili nimetuse meelde
                        try { //proovime muusikat mängida
                            laulud.get(i).mängiMuusikat();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }//else if
                }  //if lause
            } //for-tsükkel
        });
        /**ruudustik.getPlayNupp().setOnMouseClicked(event -> { //kui ruudustikus valge ala peal oleva nupu peale vajutati
            if(muusikaMängib.get() == true) { //kõigepealt kontrollime, kas meil muusika mängib. Kui mängib, paneme kinni
                player.get().lõpetaMuusika();
                muusikaMängib.set(false);
            }
            String failinimi = ruudustik.getTekst(); //siis võtame tekstiväljast tema väärtuse
            File antudfail = new File (failinimi+".wav"); //teeme temast faili

            //nüüd vaatame, kas meie audio failis on olemas selline laul
            for (int i = 0; i < laulud.size(); i++) {
                player.set(laulud.get(i));
                if (player.get().getFail().equals(antudfail)) { //kui me leidsime sellise, siis

                    if (muusikaMängib.get() == false) { //kui muusika pole mängimas, paneme käima
                        muusikaMängib.set(true); //muudame booleani väärtust
                        try { //proovime muusikat mängida
                            player.get().mängiMuusikat();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }//teine if
                } //if lause
            }
        }); **/

        //KÕIK KLAHVIVAJUTUSED---------------------------------------------------------------------------------------------
        /**scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && muusikaMängib.get() == false) { //kui klaviatuuri vajutus oli "Enter" klahv ja muusikat ei mängita praegu
                muusikaMängib.set(true); //muudame meie boolean väärtust "true"-ks. Seda on vaja selleks, et me ei saaks mitu korda samat laulu mängida
                try { //proovime muusikat mängida
                    player.get().mängiMuusikat();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } //if lause

            else if (event.getCode() == KeyCode.ENTER && muusikaMängib.get() == true){ //kui muusika mängib, siis lõpetame
                muusikaMängib.set(false); //siis muudame booleani väärtust "false"-iks, et oleks võimalik uuesti muusikat mängida
                player.get().lõpetaMuusika(); //lõpetab muusika mängimise
            }  //else if
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.P && muusikaMängib.get() == true) { //kui klaviatuuri vajutus oli "P" klahv ja muusika mängib
                muusikaMängib.set(false); //siis muudame booleani väärtust "false"-iks, et oleks võimalik uuesti muusikat mängida
                player.get().paus(); //paneme muusika pausile
            }//if lause

            else if (event.getCode() == KeyCode.P && muusikaMängib.get() == false) {
                muusikaMängib.set(true); //muudame boolean väärtust jälle
                player.get().jätka(); //jätkame
            }
        }); **/

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) throws Exception {
        launch();
    }

    public static List<Audio> looMuusikaList() throws Exception {
        List<Audio> laulud = new ArrayList<>();

        //loeme nüüd kõik laulu nimetused failist listi
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("music.txt"), "UTF-8"))) {
            while (true){
                String rida = br.readLine();
                if (rida == null)
                    break;

                laulud.add(new Audio(new File(rida+ ".wav")));  //listi paneme Audio klassi objektid. Peame ka lisame nimedele .wav lõppu
            } //while tsükkel
        } //try
        return laulud;
    }
}