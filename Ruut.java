package com.example.oop_kabe;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


public class Ruut extends StackPane {
    // ruudu koordinaadid
    private int x;
    private int y;
    private boolean must;
    //private Nupp pealOlevNupp;
    private boolean täis;

    public Ruut(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isMust() {
        return must;
    }

    public void setMust(boolean must) {
        this.must = must;
    }

    public boolean isTäis() {
        return täis;
    }

    public void setTäis(boolean täis) {
        this.täis = täis;
    }

    @Override
    public String toString() {
        return "Ruut{" +
                "x=" + x +
                ", y=" + y +
                ", must=" + must +
                ", täis=" + täis +
                '}';
    }
}
