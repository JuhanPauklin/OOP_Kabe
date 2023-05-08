package com.example.oop_kabe;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Valgeala extends Ruudustik{
    private TextField tekst; //loome kaks isendivälja valge ala jaoks, kus on tekstiväli ja nupp
    private Button playNupp;
    private Button pausNupp;
    private Button annaAllaNupp;

    public Valgeala(VBox vBox, Ruut[][] ruudustik) {
        super(vBox, ruudustik);

        // valge ala tekitamine mängulaua alla

        HBox hBox = new HBox();
        Ruut ruut = new Ruut(0,8);
        ruudustik[8][0] = ruut;

        //TEKSTI JA NUPPUDE TEKITAMINE VALGE ALALE

        tekst = new TextField("mingi tekst"); //anname isendiväljadele väärtused
        playNupp = new Button("Mängi");
        pausNupp = new Button("Paus");
        annaAllaNupp = new Button("Anna alla");

        //siis teeme ühise hbox välja, kuhu paneme mõlemad v-boxid sisse
        HBox hboxkõikVäljad = new HBox();
        hboxkõikVäljad.setSpacing(2); //muudame nende vahel olevat vahe suurust suuremaks
        hboxkõikVäljad.setPadding(new Insets(0,5,0,0)); //ja muudame paddingut paremalt poolt ka kõrgemaks selleks, et tekstiväli ei puutuks ekraani äärt
        hboxkõikVäljad.getChildren().addAll(annaAllaNupp, playNupp, tekst, pausNupp);

        hboxkõikVäljad.setAlignment(Pos.CENTER_RIGHT); //viime ta paremale ja keskele

        ruut.getChildren().add(hboxkõikVäljad);

        ruut.setStyle("-fx-background-color: white;");
        ruut.setPrefHeight(64);
        ruut.setPrefWidth(512);
        hBox.getChildren().add(ruut);
        vBox.getChildren().add(hBox);
    }

    //teeme nuppude ja teksti jaoks get meetodid. Teksti returnib ta stringina
    public String getTekstitekst() {return tekst.getText();}
    public Button getPlayNupp() {return playNupp;}
    public Button getPausNupp() {return pausNupp;}
    public Button getAnnaAllaNupp() {return annaAllaNupp;}

    //returnnib textfieldi ise
    public TextField getTekst() {
        return tekst;
    }

    public void setPausNupp(String antudTekst) {
        pausNupp.setText(antudTekst); //muudab teksti ära
    }
}
