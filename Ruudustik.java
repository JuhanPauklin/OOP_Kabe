package com.example.oop_kabe;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Ruudustik {

    private VBox vBox; // sisaldab 8 hBox'i. Igas hBox'is on 8 Ruut'u
    private Ruut[][] maatriks; // kahemõõtmeline maatriks kus on kõik Ruut isendid

    public Ruudustik() { // konstruktor. Vajalik isendi loomiseks,
        // kuid kuna ruudustikku on vaid ühel viisil vaja luua, siis argumentidega pole mõtet isendit luua.
        looRuudustik(); // selle asemel väärtustatakse isendi vBox ja maatriks eraldi meetodiga
    }

    public Ruudustik(VBox vBox, Ruut[][] maatriks) { //loome teise kontruktori, mida saame kasutada klassi Valgeala jaoks
        this.vBox = vBox;
        this.maatriks = maatriks;
    }

    public void looRuudustik() {
        VBox vBox = new VBox(); // tühi vBox kuhu lisatakse
        Ruut[][] ruudustik = new Ruut[9][8]; // tühi maatriks mida täidetakse
        boolean ruuduVärv = false; // false tähendab hele. true tähendab tume

        for (int y = 0; y < 8; y++) {
            HBox hBox = new HBox(); // uus tühi hBox
            vBox.getChildren().add(hBox); // lisame tühja hBox'i juba vBox'i
            vBox.setVgrow(hBox, Priority.ALWAYS); // hBox reageerib akna suuruse muutmisele. Vertikaalne dünaamilisus

            for (int x = 0; x < 8; x++) {
                Ruut ruut = new Ruut(x,y); // uus Ruut.
                if (ruuduVärv){ // värvi valimine
                    ruut.setStyle("-fx-background-color: saddlebrown;");
                    ruuduVärv = false; // järgmine Ruut tuleb teist värvi
                } else {
                    ruut.setStyle("-fx-background-color: bisque;");
                    ruuduVärv = true; // järgmine Ruut tuleb teist värvi
                }

                Text koordinaadid = new Text(x + " " + y); // ajutine. Abistab arusaamisega.
                koordinaadid.setFill(Color.RED);
                ruut.getChildren().add(koordinaadid);

                ruut.setPrefHeight(64); // default suurus
                ruut.setPrefWidth(64);
                ruut.setBorder(new Border( new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT))); // border

                ruudustik[y][x] = ruut; // maatriksisse ruudu lisamine
                hBox.getChildren().add(ruut);
                hBox.setHgrow(ruut, Priority.ALWAYS); // ruut reageerib akna suuruse muutmisele. Horisontaalne dünaamilisus
            }

            ruuduVärv = !ruuduVärv; // uuel real peab esimene ruut olema sama värvi, mis eelmise rea viimane
            // nii et muudame uuesti värvi (põhimõtteliselt tühistame "järgmine Ruut tuleb teist värvi" osa)
        }

        this.vBox = vBox; // väärtustame isendiväjad
        this.maatriks = ruudustik;
    }


    public VBox getvBox() {
        return vBox;
    }

    public void setvBox(VBox vBox) {
        this.vBox = vBox;
    }

    public Ruut[][] getMaatriks() {
        return maatriks;
    }

    public void setMaatriks(Ruut[][] maatriks) {
        this.maatriks = maatriks;
    }

    public void asetaNupud() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {

            }
        }
    }
}
