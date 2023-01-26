package com.example.appsnake;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Snake {

    // Variable de vérification si le snake bouge à gauche, droite, haut ou bas
    private boolean move_left, move_right, move_up, move_down;

    // Les différentes partie du snake
    private Bitmap bm, bm_head_up, bm_head_down, bm_head_left, bm_head_right, bm_body_vertical, bm_body_horizontal,
            bm_body_top_right, bm_body_top_left, bm_body_bottom_right, bm_body_bottom_left, bm_tail_right, bm_tail_left,
            bm_tail_up, bm_tail_down;

    // La position x et y du snake + sa longueur
    private int x, y, length;

    // Liste des parties du snake
    private ArrayList<PartSnake> arrPartSnake = new ArrayList<>();

    /**
     * Constructeur de la classe "Snake"
     * @param bm bitmap
     * @param x la position x
     * @param y la position y
     * @param length la longueur du snake
     */
    public Snake(Bitmap bm, int x, int y, int length) {
        this.bm = bm;
        this.x = x;
        this.y = y;
        this.length = length;

        //Instancie les valeurs des différentes parties du corps du snake
        bm_body_bottom_left = Bitmap.createBitmap(bm, 0, 0, GameView.sizeOfMap, GameView.sizeOfMap);
        bm_body_bottom_right = Bitmap.createBitmap(bm, GameView.sizeOfMap, 0, GameView.sizeOfMap, GameView.sizeOfMap);
        bm_body_horizontal = Bitmap.createBitmap(bm, 2 * GameView.sizeOfMap, 0, GameView.sizeOfMap, GameView.sizeOfMap);
        bm_body_top_left = Bitmap.createBitmap(bm, 3 * GameView.sizeOfMap, 0, GameView.sizeOfMap, GameView.sizeOfMap);
        bm_body_top_right = Bitmap.createBitmap(bm, 4 * GameView.sizeOfMap, 0, GameView.sizeOfMap, GameView.sizeOfMap);
        bm_body_vertical = Bitmap.createBitmap(bm, 5 * GameView.sizeOfMap, 0, GameView.sizeOfMap, GameView.sizeOfMap);
        bm_head_down = Bitmap.createBitmap(bm, 6 * GameView.sizeOfMap, 0, GameView.sizeOfMap, GameView.sizeOfMap);
        bm_head_left = Bitmap.createBitmap(bm, 7 * GameView.sizeOfMap, 0, GameView.sizeOfMap, GameView.sizeOfMap);
        bm_head_right = Bitmap.createBitmap(bm, 8 * GameView.sizeOfMap, 0, GameView.sizeOfMap, GameView.sizeOfMap);
        bm_head_up = Bitmap.createBitmap(bm, 9 * GameView.sizeOfMap, 0, GameView.sizeOfMap, GameView.sizeOfMap);
        bm_tail_up = Bitmap.createBitmap(bm, 10 * GameView.sizeOfMap, 0, GameView.sizeOfMap, GameView.sizeOfMap);
        bm_tail_right = Bitmap.createBitmap(bm, 11 * GameView.sizeOfMap, 0, GameView.sizeOfMap, GameView.sizeOfMap);
        bm_tail_left = Bitmap.createBitmap(bm, 12 * GameView.sizeOfMap, 0, GameView.sizeOfMap, GameView.sizeOfMap);
        bm_tail_down = Bitmap.createBitmap(bm, 13 * GameView.sizeOfMap, 0, GameView.sizeOfMap, GameView.sizeOfMap);
        setMove_right(true);
        arrPartSnake.add(new PartSnake(bm_head_right, x, y));
        for (int i = 1; i < length -1 ; i++) {
            arrPartSnake.add(new PartSnake(bm_body_horizontal, arrPartSnake.get(i-1).getX()
                    - GameView.sizeOfMap, y));
        }
        arrPartSnake.add(new PartSnake(bm_tail_right, arrPartSnake.get(length-2).getX()
                - GameView.sizeOfMap, y));
    }

    /**
     * Méthode appelé lorsque l'on fait une mise à jour du jeux
     */
    public void update() {
        for (int i = length -1; i > 0; i--) {
            arrPartSnake.get(i).setX(arrPartSnake.get(i-1).getX());
            arrPartSnake.get(i).setY(arrPartSnake.get(i-1).getY());
        }
        if (move_right){
            arrPartSnake.get(0).setX(arrPartSnake.get(0).getX() + GameView.sizeOfMap);
            arrPartSnake.get(0).setBitmap(bm_head_right);

        } else if (move_left) {
            arrPartSnake.get(0).setX(arrPartSnake.get(0).getX() - GameView.sizeOfMap);
            arrPartSnake.get(0).setBitmap(bm_head_left);

        } else if (move_up) {
            arrPartSnake.get(0).setY(arrPartSnake.get(0).getY() - GameView.sizeOfMap);
            arrPartSnake.get(0).setBitmap(bm_head_up);

        } else if (move_down) {
            arrPartSnake.get(0).setY(arrPartSnake.get(0).getY() + GameView.sizeOfMap);
            arrPartSnake.get(0).setBitmap(bm_head_down);
        }

        for (int i = 1; i < length - 1; i++) {
            if (arrPartSnake.get(i).getrLeft().intersect(arrPartSnake.get(i + 1).getrBody())
                    && arrPartSnake.get(i).getrBottom().intersect(arrPartSnake.get(i - 1).getrBody())
                    || arrPartSnake.get(i).getrLeft().intersect(arrPartSnake.get(i - 1).getrBody())
                    && arrPartSnake.get(i).getrBottom().intersect(arrPartSnake.get(i + 1).getrBody())) {

                arrPartSnake.get(i).setBitmap(bm_body_bottom_left);

            } else if (arrPartSnake.get(i).getrRight().intersect(arrPartSnake.get(i + 1).getrBody())
                    && arrPartSnake.get(i).getrBottom().intersect(arrPartSnake.get(i - 1).getrBody())
                    || arrPartSnake.get(i).getrRight().intersect(arrPartSnake.get(i - 1).getrBody())
                    && arrPartSnake.get(i).getrBottom().intersect(arrPartSnake.get(i + 1).getrBody())) {

                arrPartSnake.get(i).setBitmap(bm_body_bottom_right);

            } else if (arrPartSnake.get(i).getrLeft().intersect(arrPartSnake.get(i + 1).getrBody())
                    && arrPartSnake.get(i).getrTop().intersect(arrPartSnake.get(i - 1).getrBody())
                    || arrPartSnake.get(i).getrLeft().intersect(arrPartSnake.get(i - 1).getrBody())
                    && arrPartSnake.get(i).getrTop().intersect(arrPartSnake.get(i + 1).getrBody())) {

                arrPartSnake.get(i).setBitmap(bm_body_top_left);

            } else if (arrPartSnake.get(i).getrRight().intersect(arrPartSnake.get(i + 1).getrBody())
                    && arrPartSnake.get(i).getrTop().intersect(arrPartSnake.get(i - 1).getrBody())
                    || arrPartSnake.get(i).getrRight().intersect(arrPartSnake.get(i - 1).getrBody())
                    && arrPartSnake.get(i).getrTop().intersect(arrPartSnake.get(i + 1).getrBody())) {

                arrPartSnake.get(i).setBitmap(bm_body_top_right);

            } else if (arrPartSnake.get(i).getrTop().intersect(arrPartSnake.get(i + 1).getrBody())
                    && arrPartSnake.get(i).getrBottom().intersect(arrPartSnake.get(i - 1).getrBody())
                    || arrPartSnake.get(i).getrTop().intersect(arrPartSnake.get(i - 1).getrBody())
                    && arrPartSnake.get(i).getrBottom().intersect(arrPartSnake.get(i + 1).getrBody())) {

                arrPartSnake.get(i).setBitmap(bm_body_vertical);

            } else if (arrPartSnake.get(i).getrLeft().intersect(arrPartSnake.get(i + 1).getrBody())
                    && arrPartSnake.get(i).getrRight().intersect(arrPartSnake.get(i - 1).getrBody())
                    || arrPartSnake.get(i).getrLeft().intersect(arrPartSnake.get(i - 1).getrBody())
                    && arrPartSnake.get(i).getrRight().intersect(arrPartSnake.get(i + 1).getrBody())) {

                arrPartSnake.get(i).setBitmap(bm_body_horizontal);
            } else {
                if(move_right){
                    arrPartSnake.get(i).setBitmap(bm_body_horizontal);
                }else if(move_down){
                    arrPartSnake.get(i).setBitmap(bm_body_vertical);
                }else if(move_up){
                    arrPartSnake.get(i).setBitmap(bm_body_vertical);
                }else{
                    arrPartSnake.get(i).setBitmap(bm_body_horizontal);
                }
            }
        }

        if (arrPartSnake.get(length -1).getrRight().intersect(arrPartSnake.get(length -2).getrBody())) {
            arrPartSnake.get(length -1).setBitmap(bm_tail_right);

        } else if (arrPartSnake.get(length -1).getrLeft().intersect(arrPartSnake.get(length -2).getrBody())) {
            arrPartSnake.get(length - 1).setBitmap(bm_tail_left);

        } else if (arrPartSnake.get(length -1).getrTop().intersect(arrPartSnake.get(length -2).getrBody())) {
            arrPartSnake.get(length - 1).setBitmap(bm_tail_up);

        } else if (arrPartSnake.get(length -1).getrBottom().intersect(arrPartSnake.get(length -2).getrBody())) {
            arrPartSnake.get(length - 1).setBitmap(bm_tail_down);
        }

    }

    /**
     * Méthode appelé lorsque l'on souhaite dessiner le snake
     * @param canvas ce que l'on a besoin pour dessiner
     */
    public void drawSnake(Canvas canvas){
        for(int i = length-1; i >= 0; i--){
            canvas.drawBitmap(arrPartSnake.get(i).getBitmap(), arrPartSnake.get(i).getX(), arrPartSnake.get(i).getY(), null);
        }
    }


    // Instanciation des getters et setters

    public Bitmap getBm() {
        return bm;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public Bitmap getBm_head_up() {
        return bm_head_up;
    }

    public void setBm_head_up(Bitmap bm_head_up) {
        this.bm_head_up = bm_head_up;
    }

    public Bitmap getBm_head_down() {
        return bm_head_down;
    }

    public void setBm_head_down(Bitmap bm_head_down) {
        this.bm_head_down = bm_head_down;
    }

    public Bitmap getBm_head_left() {
        return bm_head_left;
    }

    public void setBm_head_left(Bitmap bm_head_left) {
        this.bm_head_left = bm_head_left;
    }

    public Bitmap getBm_head_right() {
        return bm_head_right;
    }

    public void setBm_head_right(Bitmap bm_head_right) {
        this.bm_head_right = bm_head_right;
    }

    public Bitmap getBm_body_vertical() {
        return bm_body_vertical;
    }

    public void setBm_body_vertical(Bitmap bm_body_vertical) {
        this.bm_body_vertical = bm_body_vertical;
    }

    public Bitmap getBm_body_horizontal() {
        return bm_body_horizontal;
    }

    public void setBm_body_horizontal(Bitmap bm_body_horizontal) {
        this.bm_body_horizontal = bm_body_horizontal;
    }

    public Bitmap getBm_body_top_right() {
        return bm_body_top_right;
    }

    public void setBm_body_top_right(Bitmap bm_body_top_right) {
        this.bm_body_top_right = bm_body_top_right;
    }

    public Bitmap getBm_body_top_left() {
        return bm_body_top_left;
    }

    public void setBm_body_top_left(Bitmap bm_body_top_left) {
        this.bm_body_top_left = bm_body_top_left;
    }

    public Bitmap getBm_body_bottom_right() {
        return bm_body_bottom_right;
    }

    public void setBm_body_bottom_right(Bitmap bm_body_bottom_right) {
        this.bm_body_bottom_right = bm_body_bottom_right;
    }

    public Bitmap getBm_body_bottom_left() {
        return bm_body_bottom_left;
    }

    public void setBm_body_bottom_left(Bitmap bm_body_bottom_left) {
        this.bm_body_bottom_left = bm_body_bottom_left;
    }

    public Bitmap getBm_tail_right() {
        return bm_tail_right;
    }

    public void setBm_tail_right(Bitmap bm_tail_right) {
        this.bm_tail_right = bm_tail_right;
    }

    public Bitmap getBm_tail_left() {
        return bm_tail_left;
    }

    public void setBm_tail_left(Bitmap bm_tail_left) {
        this.bm_tail_left = bm_tail_left;
    }

    public Bitmap getBm_tail_up() {
        return bm_tail_up;
    }

    public void setBm_tail_up(Bitmap bm_tail_up) {
        this.bm_tail_up = bm_tail_up;
    }

    public Bitmap getBm_tail_down() {
        return bm_tail_down;
    }

    public void setBm_tail_down(Bitmap bm_tail_down) {
        this.bm_tail_down = bm_tail_down;
    }

    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getlength() {
        return length;
    }

    public void setlength(int length) {
        this.length = length;
    }

    public ArrayList<PartSnake> getArrPartSnake() {
        return arrPartSnake;
    }

    public void setArrPartSnake(ArrayList<PartSnake> arrPartSnake) {
        this.arrPartSnake = arrPartSnake;
    }

    public boolean isMove_left() {
        return move_left;
    }

    public void setMove_left(boolean move_left) {
        setup();
        this.move_left = move_left;
    }

    public boolean isMove_right() {
        return move_right;
    }

    public void setMove_right(boolean move_right) {
        setup();
        this.move_right = move_right;
    }

    public boolean ismove_up() {
        return move_up;
    }

    public void setmove_up(boolean move_up) {
        setup();
        this.move_up = move_up;
    }

    public boolean ismove_down() {
        return move_down;
    }

    public void setmove_down(boolean move_down) {
        setup();
        this.move_down = move_down;
    }

    public void setup() {
        this.move_left = false;
        this.move_right = false;
        this.move_up = false;
        this.move_down = false;
    }

    /**
     * Méthode appelé lorsque l'on souhaite ajouter une partie au snake
     */
    public void addPart() {
        PartSnake partSnake = this.arrPartSnake.get(length -1);
        this.length += 1;
        if (partSnake.getBitmap() == bm_tail_right){
            this.arrPartSnake.add(new PartSnake(bm_tail_right, partSnake.getX() - GameView.sizeOfMap, partSnake.getY()));

        } else if (partSnake.getBitmap() == bm_tail_left){
            this.arrPartSnake.add(new PartSnake(bm_tail_left, partSnake.getX() + GameView.sizeOfMap, partSnake.getY()));

        } else if (partSnake.getBitmap() == bm_tail_up){
            this.arrPartSnake.add(new PartSnake(bm_tail_up, partSnake.getX(), partSnake.getY() + GameView.sizeOfMap));

        } else if (partSnake.getBitmap() == bm_tail_down){
            this.arrPartSnake.add(new PartSnake(bm_tail_right, partSnake.getX(), partSnake.getY() - GameView.sizeOfMap));
        }
    }
}
