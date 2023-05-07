package com.example.oop_kabe;

import javafx.scene.control.TextField;

public class ValeMuusikaErind extends RuntimeException{

    public ValeMuusikaErind(String message, TextField tekst) {
        super(message);
        tekst.setText(message); //muudame tekstikastis teksti
    }
}
