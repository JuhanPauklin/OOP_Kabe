package com.example.oop_kabe;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Nupp extends Circle{
    private boolean must;
    private boolean kuningas;

    private int x;
    private int y;

    public Nupp(double centerX, double centerY, double radius, Paint fill, boolean must, int x, int y) {
        super(centerX, centerY, radius, fill);
        this.must = must;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Nupp{" +
                "must=" + must +
                ", kuningas=" + kuningas +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public boolean isMust() {
        return must;
    }
}
