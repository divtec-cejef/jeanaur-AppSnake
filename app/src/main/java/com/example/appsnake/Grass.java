package com.example.appsnake;

import android.graphics.Bitmap;


public class Grass {
    // Variable bitmap
    private Bitmap bitmap;
    // La position x et y des images
    // Et la hauteur plus sa largeur
    private int x, y, width, height;

    /**
     * Constructeur de la classe "Grass"
     * @param bitmap zone de dessin
     * @param x Position x de l'image
     * @param y Position y de l'image
     * @param width La largeur de l'image
     * @param height La hauteur de l'image
     */
    public Grass(Bitmap bitmap, int x, int y, int width, int height) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Instanciation des getters et setters

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
