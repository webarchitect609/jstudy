package ru.webarch.jstudy.sandbox;

public class Equation {

    private double a;
    private double b;
    private double c;

    private int rootCount;

    public Equation(double a, double b, double c) {

        this.a = a;
        this.b = b;
        this.c = c;


        double d =  Math.pow(b, 2) - 4 * a * c;

        if (a != 0) {
            if ( d > 0) {
                rootCount = 2;
            } else if ( d == 0 ) {
                rootCount = 1;
            } else {
                rootCount = 0;
            }
        } else if (b != 0) {
            rootCount = 1;
        } else if (c != 0) {
            rootCount = 0;
        } else {
            rootCount = -1;
        }


    }

    public int getRootCount() {
        return rootCount;
    }
}
