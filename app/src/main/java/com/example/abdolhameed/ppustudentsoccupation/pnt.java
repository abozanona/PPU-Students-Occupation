package com.example.abdolhameed.ppustudentsoccupation;

/**
 * Created by abdolhameed on 21/03/16.
 */
public class pnt {
    private float xs,ys,xn,yn;

    public float getXs() {
        return xs;
    }

    public void setXs(float xs) {
        this.xs = xs;
    }

    public float getYs() {
        return ys;
    }

    public void setYs(float ys) {
        this.ys = ys;
    }

    public float getXn() {
        return xn;
    }

    public void setXn(float xn) {
        this.xn = xn;
    }

    public float getYn() {
        return yn;
    }

    public void setYn(float yn) {
        this.yn = yn;
    }

    public pnt(float xs, float ys, float xn, float yn) {

        this.xs = xs;
        this.ys = ys;
        this.xn = xn;
        this.yn = yn;
    }
}
