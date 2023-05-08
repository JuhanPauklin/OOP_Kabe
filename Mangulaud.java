package com.example.oop_kabe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Mangulaud extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        startEkraan(stage);

        stage.setTitle("Kabe");

        Meta meta = new Meta(true);
        Ruudustik ruudustik = new Ruudustik(meta); // loob uue ruudustiku isendi, milles on olemas vBox
        Ruudustik.asetaNupud(ruudustik.getMaatriks(), meta);

        Valgeala valgeala = new Valgeala(ruudustik.getvBox(), ruudustik.getMaatriks());

        VBox vBox = ruudustik.getvBox(); // võtab ruudustiku isendist vBox'i

        Scene scene = new Scene(vBox, 512, 512);

        //KÕIK MUUSIKAGA SEOTUD VAJALIK
        AtomicBoolean muusikaMängib = new AtomicBoolean(false); //kuna me töötame threadidega ja meil on käimas kaks threadi (audio ja stage), siis peame kasutama atomatic booleani
        AtomicBoolean muusikaPausipeal = new AtomicBoolean(false);
        AtomicBoolean õigestiSisestatudLaul = new AtomicBoolean(false);
        List<Audio> laulud = looMuusikaList();
        List<Audio> hetkelMängiv = new ArrayList<>(); //siia paneme selle laulu, mis on hetkel mängimas
        List<String> kõikmängitudlaulud = new ArrayList<>(); //siia paneme kõik laulude nimetused, mida oleme mänginud

        //KÕIK NUPUVAJUTUSED-----------------------------------------------------------------------------------------------
        valgeala.getPlayNupp().setOnMouseClicked(event -> { //kui ruudustikus valge ala peal oleva mängi-nupu peale vajutati
            File antudfail = new File (valgeala.getTekstitekst()+".wav"); //leiame tekstivälja väärtuse ja teeme temast faili
            //nüüd vaatame, kas meie audio failis on olemas selline laul
            for (int i = 0; i < laulud.size(); i++) {
                if (laulud.get(i).getFail().equals(antudfail)) { //kui me leidsime sellise, siis
                    õigestiSisestatudLaul.set(true); //siis kõigepealt muudame väärtust true-iks, et oleme leidnud sellise laulu

                    kõikmängitudlaulud.add(valgeala.getTekstitekst()); //kirjutame laulu nimetuse laulude logide listi

                    if (muusikaMängib.get() == false) { //kui muusika pole mängimas, paneme käima
                        muusikaMängib.set(true);

                        //lisaks kontrollime, kas muusika oli enne pausi peal. Kui oli, siis ta enam pole pausi peal
                        if (muusikaPausipeal.get() == true) {
                            muusikaPausipeal.set(false);
                            valgeala.setPausNupp("Mute");
                            hetkelMängiv.remove(0); //ja eemaldame selle laulu listist
                        }
                        hetkelMängiv.add(laulud.get(i)); //jätame selle faili nimetuse meelde
                        try { //proovime muusikat mängida
                            laulud.get(i).mängiMuusikat();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }//muusikamängib if lause
                    else if (muusikaMängib.get() == true) { //kui aga muusika on mängimas, peame praeguse laulu ennem kinni panema
                        hetkelMängiv.get(0).lõpetaMuusika();
                        hetkelMängiv.remove(0); //ja eemaldame ta listist

                        hetkelMängiv.add(laulud.get(i)); //jätame selle faili nimetuse meelde
                        try { //proovime muusikat mängida
                            laulud.get(i).mängiMuusikat();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }//else if
                }  //if lause
            } //for-tsükkel

            //nüüd me kontrollime, kas me leidsime sellise laulu. Kui ei, siis peame väärtuse false-iks muutma, et järgmise lauluga kontroll töötaks
            if (õigestiSisestatudLaul.get() == false)
                throw new ValeMuusikaErind("Valesti sisestatud laul", valgeala.getTekst());
            õigestiSisestatudLaul.set(false);
        });

        valgeala.getPausNupp().setOnMouseClicked(event -> { //kui vajutati pausi nupu peale
            if (muusikaMängib.get() == true && muusikaPausipeal.get() == false) { //siis kõigepealt üldse vaatame, kas muusika mängib. kui jah, siis
                muusikaMängib.set(false);
                muusikaPausipeal.set(true);

                hetkelMängiv.get(0).paus();
                valgeala.setPausNupp("Edasi");

            } else if (muusikaMängib.get() == false && hetkelMängiv.size() != 0 && muusikaPausipeal.get() == true) { //kui muusika ei mängi ja laulu enne mängiti (ehk pandi laul pausi peale)
                muusikaMängib.set(true);
                muusikaPausipeal.set(false);
                hetkelMängiv.get(0).jätka();
                valgeala.setPausNupp("Paus");
            }
        });

        valgeala.getAnnaAllaNupp().setOnMouseClicked(event -> { //kui ruudustikus valge ala peal oleva anna alla nupu peale vajutati
            if (muusikaMängib.get() == true) //kui muusika mängib
                hetkelMängiv.get(0).lõpetaMuusika(); //siis lõpetame selle
            meta.võiduEkraan(stage, !meta.isMustaKord()); //kui musta kord on, siis on vaja, et ta annaks meile ette "VALGE VÕITIS", selleks muudame väärtuse vastupidiseks

        });
        //KÕIK KLAHVIVAJUTUSED---------------------------------------------------------------------------------------------

        scene.setOnKeyReleased(event -> { //kui klaviatuuri vajutus oli ctrl
            if (event.getCode() == KeyCode.CONTROL && muusikaMängib.get() == true && muusikaPausipeal.get() == false) { //kui klaviatuuri vajutus oli "P" klahv ja muusika mängib
                muusikaMängib.set(false);
                muusikaPausipeal.set(true);

                hetkelMängiv.get(0).paus();
                valgeala.setPausNupp("Edasi");
                ; //paneme muusika pausile
            }//if lause

            else if (event.getCode() == KeyCode.CONTROL && hetkelMängiv.size() != 0 && muusikaPausipeal.get() == true) {
                muusikaMängib.set(true);
                muusikaPausipeal.set(false);
                hetkelMängiv.get(0).jätka();
                valgeala.setPausNupp("Paus");
            }
        });


        //STSEENI KINNI PANEK
        stage.setOnCloseRequest((e) -> { //kui me panime stseeni kinni
            try {
                kirjutaLaulFaili(kõikmängitudlaulud); //kirjutame kõik laulud faili
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        stage.setOnHidden((e) -> { //kui võidu ekraan tuleb lahti, siis läheb see stseen peitu. Loome siis logifaili juhul, kui tegemist pole start-ekraaniga
            try {
                kirjutaLaulFaili(kõikmängitudlaulud); //kirjutame kõik laulud faili
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

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

    public static void kirjutaLaulFaili(List<String> laulud) throws IOException{
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("lauludelogi.txt"), "UTF-8"))) {
            for (int i = 0; i < laulud.size(); i++) {
                bw.write(laulud.get(i) + "\n");//kirjutame iga rea listi
            }
        } //try
    }

    public static void startEkraan(Stage stage) {
        BorderPane küljendus = new BorderPane();
        Stage teine = new Stage();
        Text stardiTekst = new Text();

        stardiTekst.setFont(Font.font("Impact", FontWeight.BOLD, 70));
        stardiTekst.setText("KABE");

        Button stardiNupp = new Button("Start");

        küljendus.setCenter(stardiTekst);
        küljendus.setBottom(stardiNupp);


        Scene stseen2 = new Scene(küljendus, 500, 150, Color.SNOW);
        teine.setTitle("StartEkraan");
        teine.setResizable(false);
        teine.setScene(stseen2);
        teine.show();

        stardiNupp.setOnMouseClicked((event) -> { //kui vajutatakse stardinupu peale
            teine.close();
            stage.show();
        });
    }
}