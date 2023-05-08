package com.example.oop_kabe;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Ruudustik {
    private VBox vBox; // sisaldab 8 hBox'i. Igas hBox'is on 8 Ruut'u
    private Ruut[][] maatriks; // kahemõõtmeline maatriks kus on kõik Ruut isendid

    public Ruudustik(Meta meta) { // konstruktor. Vajalik isendi loomiseks,
        // kuid kuna ruudustikku on vaid ühel viisil vaja luua, siis argumentidega pole mõtet isendit luua.
        looRuudustik(meta); // selle asemel väärtustatakse isendi vBox ja maatriks eraldi meetodiga
    }

    public Ruudustik(VBox vBox, Ruut[][] maatriks) { //loome teise kontruktori, mida saame kasutada klassi Valgeala jaoks
        this.vBox = vBox;
        this.maatriks = maatriks;
    }

    public VBox getvBox() {
        return vBox;
    }

    public Ruut[][] getMaatriks() {
        return maatriks;
    }

    public void looRuudustik(Meta meta) {
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
                    ruut.setMust(true);
                    ruuduVärv = false; // järgmine Ruut tuleb teist värvi
                } else {
                    ruut.setStyle("-fx-background-color: bisque;");
                    ruuduVärv = true; // järgmine Ruut tuleb teist värvi
                }
                if (ruut.isMust()) {
                    ruut.setOnMouseEntered(event -> {
                        ruut.setStyle("-fx-background-color: PERU;");

                    });
                    ruut.setOnMouseExited(event -> ruut.setStyle("-fx-background-color: saddlebrown;"));

                    ruut.setOnMouseClicked(event -> {
                        if (meta.getValitudNupp() != null && !ruut.isTäis() // Kui leidub valitud nupp ja ruut millele vajutad on tühi, siis
                                && ( on1VõrraDiagonaalis(meta, ruut) || onÜleVastaseNupu(meta, ruut, maatriks))
                        ) {

                            meta.getRuutValitudNupuga().getChildren().remove(meta.getValitudNupp()); // eemaldame eelnevalt ruudult nupu
                            meta.getRuutValitudNupuga().setTäis(false); // määrame et eelnev ruut on tühi

                            ruut.getChildren().add(meta.getValitudNupp()); // lisame vajutatud ruudule nupu
                            ruut.setTäis(true); // määrame et vajutatud ruut on täis

                            tapa(meta, ruut, maatriks);

                            meta.setRuutValitudNupuga(ruut); // määrame, et ruut millele vajutasime on ruut millel on valitud nupp

                            meta.setMustaKord(!meta.isMustaKord()); // muudame mängijate korda

                            if (meta.getValitudNupp().isMust()) { // muudame valitud nupu värvi tagasi algseks (värvi muudeti sellele vajutades)
                                meta.getValitudNupp().setFill(Color.BLACK);
                            } else meta.getValitudNupp().setFill(Color.SNOW);

                            meta.setValitudNupp(null); // eemaldame praeguse valitud nupu, selleks, et järgmine mängija saaks valida oma nupu
                        }
                    }); // Mängija käik
                }

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

    // nuppude objektide loomine ja nende asetamine ruudustikku.
    // nuppude event nendele vajutamisel.
    public static void asetaNupud(Ruut[][] maatriks, Meta meta) {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Ruut ruut = maatriks[y][x];
                if (y < 3 && ruut.isMust()) {
                    Nupp nupp = new Nupp(32,32,16, Color.BLACK, true, x, y);
                    ruut.getChildren().add(nupp);
                    ruut.setTäis(true);
                    nupp.setOnMouseClicked(event -> { // nupule vajutamine
                        if (meta.isMustaKord()) { // mustasid nuppe saab valida vaid musta mängija korra ajal

                            // kui oma korra ajal juba valisime nupu, siis uut nuppu valides muudame eelmise valitud nupu tagasi samat värvi
                            if (meta.getValitudNupp() != null) {
                                    meta.getValitudNupp().setFill(Color.BLACK);
                            }

                            meta.setValitudNupp(nupp);
                            meta.setRuutValitudNupuga((Ruut) nupp.getParent());
                            nupp.setFill(Color.web("rgb(50, 0, 0)"));
                        }
                    });

                }
                if (y > 4 && ruut.isMust()) {
                    Nupp nupp = new Nupp(32,32,16, Color.SNOW, false, x, y);
                    ruut.getChildren().add(nupp);
                    ruut.setTäis(true);
                    nupp.setOnMouseClicked(event -> { // nupule vajutamine
                        if (!meta.isMustaKord()) { // valgeid nuppe saab valida vaid valge mängija korra ajal

                            // kui oma korra ajal juba valisime nupu, siis uut nuppu valides muudame eelmise valitud nupu tagasi samat värvi
                            if (meta.getValitudNupp() != null) {
                                meta.getValitudNupp().setFill(Color.SNOW);
                            }

                            meta.setValitudNupp(nupp);
                            meta.setRuutValitudNupuga((Ruut) nupp.getParent());
                            nupp.setFill(Color.web("rgb(204, 204, 255)"));
                        }
                    });


                }
            }
        }
    }

    // Nupu liigutamise jaoks. Kontrollime, kas ruut millele vajutasime on ühe võrra diagonaalis ruudust, kus on valitud nupp.
    public static boolean on1VõrraDiagonaalis(Meta meta, Ruut vajutatudRuut) {
        Ruut ruutValituga = meta.getRuutValitudNupuga();
        if (meta.isMustaKord()) {
            return (  (vajutatudRuut.getX() == ruutValituga.getX() - 1 || vajutatudRuut.getX() == ruutValituga.getX() + 1)
                    &&
                      vajutatudRuut.getY() == ruutValituga.getY() + 1);
        } else {
            return (  (vajutatudRuut.getX() == ruutValituga.getX() - 1 || vajutatudRuut.getX() == ruutValituga.getX() + 1)
                    &&
                      vajutatudRuut.getY() == ruutValituga.getY() - 1);
        }
    }

    // Nupu liigutamise jaoks. Kontrollime, kas ruut millele vajutasime on kahe võrra diagonaalis ruudust, kus on valitud nupp
    // ning, et ruudul mis on vajutatud ruudu ja nupuga ruudu vahel on vastane.
    public static boolean onÜleVastaseNupu(Meta meta, Ruut vajutatudRuut, Ruut[][] maatriks) {
        Ruut ruutValituga = meta.getRuutValitudNupuga();
        if (meta.isMustaKord()) {
            return ( ( vajutatudRuut.getX() == ruutValituga.getX() - 2 && ruudulOlevNuppOnMust(maatriks[ruutValituga.getY()+1][ruutValituga.getX()-1]) == -1
                    ||
                       vajutatudRuut.getX() == ruutValituga.getX() + 2 && ruudulOlevNuppOnMust(maatriks[ruutValituga.getY()+1][ruutValituga.getX()+1]) == -1 )
                    &&
                       vajutatudRuut.getY() == ruutValituga.getY() + 2
                    );
        } else {
            return ( ( vajutatudRuut.getX() == ruutValituga.getX() - 2 && ruudulOlevNuppOnMust(maatriks[ruutValituga.getY()-1][ruutValituga.getX()-1]) == 1
                    ||
                       vajutatudRuut.getX() == ruutValituga.getX() + 2 && ruudulOlevNuppOnMust(maatriks[ruutValituga.getY()-1][ruutValituga.getX()+1]) == 1)
                    &&
                     vajutatudRuut.getY() == ruutValituga.getY() - 2
                    );
        }
    }

    // kontrollib, kas etteantud ruudu peal on nupp ja kas see on must.
    public static int ruudulOlevNuppOnMust (Ruut ruut) {
        if (ruut.getChildren().size() == 1) { // kas ruudul on olemas nupp
            Nupp nupp = (Nupp) ruut.getChildren().get(0);
            if (nupp.isMust()) return 1;
            else if (!nupp.isMust()) return -1;
        }
        return 0; // kui ruut on tühi
    }

    // Vastases nupust ülehüppamisel eemaldab vastase nupu
    public static void tapa (Meta meta, Ruut vajutatudRuut, Ruut[][] maatriks) {
        Ruut ruutValituga = meta.getRuutValitudNupuga();

        int vastaseX = ruutValituga.getX() + (vajutatudRuut.getX()-ruutValituga.getX())/2; // arvutab, mis suunas liiguti, mööda x-koordinaati...
        int vastaseY = ruutValituga.getY() + (vajutatudRuut.getY()-ruutValituga.getY())/2; // ja y-koordinaati
        Ruut ruutVastaseNupuga = maatriks[vastaseY][vastaseX];

        // Alguses kontrollime, kas keskmisel ruudul on ikka nupp olemas
        // kuna tegelikult kutsume meetodit esile ka siis kui ei hüppa üle vastase ehk ainult ühe ruudu võrra liikumisel,
        // siis peame vaatama, et sellel ruudul pole meie enda valitud nuppu. Sphagetti code :]
        if (ruutVastaseNupuga.isTäis() && meta.getValitudNupp() != ruutVastaseNupuga.getChildren().get(0)) {
            Nupp vastaseNupp = (Nupp) ruutVastaseNupuga.getChildren().get(0);

            if (vastaseNupp.isMust()) meta.setMustiNuppe(meta.getMustiNuppe()-1);
            else meta.setValgeidNuppe(meta.getValgeidNuppe()-1);

            ruutVastaseNupuga.getChildren().remove(0);
            ruutVastaseNupuga.setTäis(false);
        }
    }
}
