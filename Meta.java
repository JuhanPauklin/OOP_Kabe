package com.example.oop_kabe;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Meta {
    private Nupp valitudNupp;
    private Ruut ruutValitudNupuga;
    private boolean mustaKord;
    private IntegerProperty mustiNuppe;
    private IntegerProperty valgeidNuppe;

    public Meta(boolean mustaKord) {
        this.mustaKord = mustaKord;
        this.mustiNuppe = new SimpleIntegerProperty(12); // Alustades on mõlemal mängijal 12 nuppu
        this.valgeidNuppe = new SimpleIntegerProperty(12);
    }

    public void nuppudeLugemine (Stage stage) {
        mustiNuppe.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() <= 0) { // kui musti nuppe enam laual pole siis on valge võitnud
                    // muuda null suuremaks arvuks (nt 11) kui tahad testida võitmist ja ei taha lõpuni mängida.
                    võiduEkraan(stage, false);
                }
            }
        });

        valgeidNuppe.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() <= 0) { // kui valgeid nuppe enam laual pole siis on must võitnud
                    võiduEkraan(stage, true);
                }
            }
        });
    }

    // uus ekraan, mis ilmub võidu korral
    public void võiduEkraan(Stage stage, boolean mustaVõit) {
        stage.hide();
        BorderPane kyljendus = new BorderPane();
        Stage teine = new Stage();
        Text võiduTekst = new Text();
        võiduTekst.setFont(Font.font("Impact", FontWeight.BOLD, 70));
        kyljendus.setCenter(võiduTekst);

        if (mustaVõit) võiduTekst.setText("MUSTA VÕIT!");
        else võiduTekst.setText("VALGE VÕIT!");

        Scene stseen2 = new Scene(kyljendus, 500, 150, Color.SNOW);
        teine.setTitle("Võiduekraan");
        teine.setResizable(false);
        teine.setScene(stseen2);
        teine.show();
    }

    public Nupp getValitudNupp() {
        return valitudNupp;
    }

    public void setValitudNupp(Nupp valitudNupp) {
        this.valitudNupp = valitudNupp;
    }

    public Ruut getRuutValitudNupuga() {
        return ruutValitudNupuga;
    }

    public void setRuutValitudNupuga(Ruut ruutValitudNupuga) {
        this.ruutValitudNupuga = ruutValitudNupuga;
    }

    public boolean isMustaKord() {
        return mustaKord;
    }

    public void setMustaKord(boolean mustaKord) {
        this.mustaKord = mustaKord;
    }

    public int getMustiNuppe() {
        return mustiNuppe.get();
    }

    public void setMustiNuppe(int mustiNuppe) {
        this.mustiNuppe.set(mustiNuppe);
    }

    public int getValgeidNuppe() {
        return valgeidNuppe.get();
    }

    public void setValgeidNuppe(int valgeidNuppe) {
        this.valgeidNuppe.set(valgeidNuppe);
    }
}
