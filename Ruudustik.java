package com.example.oop_kabe;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Ruudustik {

    private VBox vBox; // sisaldab 8 hBox'i. Igas hBox'is on 8 Ruut'u
    private Ruut[][] maatriks; // kahemõõtmeline maatriks kus on kõik Ruut isendid

    private TextField tekst; //loome kaks isendivälja valge ala jaoks, kus on tekstiväli ja nupp
    private Button playNupp;

    public Ruudustik() { // konstruktor. Vajalik isendi loomiseks,
        // kuid kuna ruudustikku on vaid ühel viisil vaja luua, siis argumentidega pole mõtet isendit luua.
        looRuudustik(); // selle asemel väärtustatakse isendi vBox ja maatriks eraldi meetodiga
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

        HBox hBox = new HBox(); // valge ala tekitamine mängulaua alla
        Ruut ruut = new Ruut(0,8);
        ruudustik[8][0] = ruut;

        //TEKSTI JA NUPU TEKITAMINE VALGE ALALE

        VBox vboxnupp = new VBox(); //teeme mõlemale eraldi v-boxid ja lisame nad sinna
        VBox vboxtekstiväli = new VBox();
        tekst = new TextField("mingi tekst"); //anname isendiväljadele väärtused
        playNupp = new Button("Mängi");

        vboxtekstiväli.getChildren().add(tekst);
        vboxnupp.getChildren().add(playNupp);

        vboxnupp.setAlignment(Pos.CENTER); //viime nüüd mõlemad vboxid keskele
        vboxtekstiväli.setAlignment(Pos.CENTER);

        //siis teeme ühise hbox välja, kuhu paneme mõlemad v-boxid sisse
        HBox hboxkõikVäljad = new HBox();
        hboxkõikVäljad.setSpacing(2); //muudame nende vahel olevat vahe suurust suuremaks
        hboxkõikVäljad.setPadding(new Insets(0,5,0,0)); //ja muudame paddingut paremalt poolt ka kõrgemaks selleks, et tekstiväli ei puutuks ekraani äärt
        hboxkõikVäljad.getChildren().addAll(vboxnupp, vboxtekstiväli);

        hboxkõikVäljad.setAlignment(Pos.CENTER_RIGHT); //viime ta paremale ja keskele
        ruut.getChildren().add(hboxkõikVäljad);




        ruut.setStyle("-fx-background-color: white;");
        ruut.setPrefHeight(64);
        ruut.setPrefWidth(512);
        hBox.getChildren().add(ruut);
        vBox.getChildren().add(hBox);

        this.vBox = vBox; // väärtustame isendiväjad
        this.maatriks = ruudustik;
    }
    //teeme nupu ja teksti jaoks get meetodid. Teksti returnib ta stringina
    public String getTekst() {return tekst.getText();}
    public Button getPlayNupp() {return playNupp;}


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
