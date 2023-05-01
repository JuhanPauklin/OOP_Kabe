package com.example.oop_kabe;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Audio {
    private File fail; //faili nimi
    private Clip clip; //klipp
    private long klipipositsioon; //seda on vaja muusikaklipi peatamiseks ja jätkamiseks. Sellega teama, kust jätkata

    public Audio(File fail) throws Exception {
        this.fail = fail;
    }

    public File getFail() {
        return fail;
    }

    public void mängiMuusikat()  throws Exception{ //see meetod alustab muusika mängimist algusest
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(fail);
            clip = AudioSystem.getClip(); //teeme klipi
            clip.open(ais); //teeme klipi lahti
            clip.start(); //alustame klipi mängimist
        } // try lause
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void paus() {
        klipipositsioon = clip.getMicrosecondPosition(); //leiame klipi positsiooni
        clip.stop();
    }

    public void jätka(){ //see meetod jätkab muusika mängimist
        clip.setMicrosecondPosition(klipipositsioon); //leiame selle koha, kust me pooleli jäime
        try{
            clip.start(); //ning jätkame sellest
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void lõpetaMuusika(){ //see meetod lõpetab muusika mängimise
        clip.close();
    }

    public long getKlipipositsioon() {
        return klipipositsioon;
    }
}
