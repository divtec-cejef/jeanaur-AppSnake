package com.example.testsnake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.LogRecord;

public class GameView extends View {

    private Bitmap bmGrass1, bmGrass2, bmSnake, bmApple;
    public static int sizeOfMap = 70 * Constants.SCREEN_WIDTH / 1080;
    private int h = 7, w = 16;
    private ArrayList<Grass> arrGrass = new ArrayList<>();
    private Snake snake;
    private boolean move = false;
    private float mx, my;
    private android.os.Handler handler;
    private Runnable runnable;

    private Apple apple;
    public static boolean isPlaying = false;
    private Context context;

    public GameView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;

        bmGrass1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.img_grass);
        bmGrass1 = Bitmap.createScaledBitmap(bmGrass1, sizeOfMap, sizeOfMap, true);
        bmGrass2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.img_grass03);
        bmGrass2 = Bitmap.createScaledBitmap(bmGrass2, sizeOfMap, sizeOfMap, true);
        bmSnake = BitmapFactory.decodeResource(this.getResources(), R.drawable.snake1);
        bmSnake = Bitmap.createScaledBitmap(bmSnake, 14 * sizeOfMap, sizeOfMap, true);
        bmApple = BitmapFactory.decodeResource(this.getResources(), R.drawable.apple);
        bmApple = Bitmap.createScaledBitmap(bmApple, sizeOfMap, sizeOfMap, true);

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (( i + j ) % 2 == 0) {
                    arrGrass.add(new Grass(bmGrass1, j * sizeOfMap + Constants.SCREEN_WIDTH / 2 - ( w / 2 ) * sizeOfMap,
                            i * sizeOfMap + Constants.SCREEN_HEIGHT / 1920, sizeOfMap, sizeOfMap));

                } else {
                    arrGrass.add(new Grass(bmGrass2, j * sizeOfMap + Constants.SCREEN_WIDTH / 2 - ( w / 2 ) * sizeOfMap,
                            i * sizeOfMap + Constants.SCREEN_HEIGHT / 1920, sizeOfMap, sizeOfMap));
                }
            }
        }
        snake = new Snake(bmSnake, arrGrass.get(25).getX(), arrGrass.get(60).getY(), 2);
        apple = new Apple(bmApple, arrGrass.get(randomApple()[0]).getX(), arrGrass.get(randomApple()[1]).getY());
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }

    public int[] randomApple() {
        int[] xy = new int[2];
        Random random = new Random();
        xy[0] = random.nextInt(arrGrass.size() - 1);
        xy[1] = random.nextInt(arrGrass.size() - 1);
        Rect rect = new Rect(arrGrass.get(xy[0]).getX(), arrGrass.get(xy[1]).getY(), arrGrass.get(xy[0]).getX() + sizeOfMap, arrGrass.get(xy[1]).getY() + sizeOfMap);
        boolean check = true;
        while (check) {
            check = false;
            for (int i = 0; i < snake.getArrPartSnake().size(); i++) {
                if (rect.intersect(snake.getArrPartSnake().get(i).getrBody())) {
                    check = true;
                    xy[0] = random.nextInt(arrGrass.size() - 1);
                    xy[1] = random.nextInt(arrGrass.size() - 1);
                    rect = new Rect(arrGrass.get(xy[0]).getX(), arrGrass.get(xy[1]).getY(), arrGrass.get(xy[0]).getX() + sizeOfMap, arrGrass.get(xy[1]).getY() + sizeOfMap);

                }
            }
        }
        return xy;
    }

    /**
     * Méthode qui fait bouger le snake
     *
     * @param x valeur de l'accélération selon l'axe X
     * @param y valeur de l'accélération selon l'axe Y
     */
   /* public static void moveBlock(float x, float y) {
        // Récupération des dimensions du block
        snake_height = snake. ( );
        snake_width = snake.getWidth();

        System.out.println("Snake height : " + snake_height);
        System.out.println("Snake Width : " + snake_width);

        // Calculer la force
        float force = (float) Math.sqrt(( x * x ) + ( y * y ));

        // Modifier la puissance de la force
        force = force / 10;

        // Calculer les nouvelles coordonnées de l'image
        snakePosX = (int) ( snakePosX - x * force );
        snakePosY = (int) ( snakePosY + y * force );

        // Vérification que l'image ne sort pas de l'écran
        // Si elle sort, on la positionne sur l'écran
        if (snakePosX < 0) {
            snakePosX = 0;
        } else if (snakePosX > display_width - snake_width) {
            snakePosX = display_width - snake_width;
        }
        if (snakePosY < 0) {
            snakePosY = 0;
        } else if (snakePosY > display_height - snake_height) {
            snakePosY = display_height - snake_height;
        }
        System.out.println("Snake X : " + snakePosX);
        System.out.println("Snake Y : " + snakePosY);

        // Déplacer l'image
        snake.setX((float) snakePosX);
        snake.setY((float) snakePosY);
    }

    /**
     * Méthode qui vérifie s'il y a une collision
     *
     * @return 0 s'il n'y en a pas, 1 2 3 ou 4 en fonction du côté du mur qui a été touché
     */
    /*public static int checkIfCollision() {

        // Récupérer les coordonnées du mur
        double murX = (int) mur.getX();
        double murY = (int) mur.getY();

        // Récupérer les dimensions du mur
        double mur_height = mur.getHeight();
        double mur_width = mur.getWidth();

        System.out.println("Verif de la col");
        System.out.println("Mur X : " + murX);
        System.out.println("Mur Y : " + murY);
        System.out.println("Mur Height : " + mur_height);
        System.out.println("Mur Width : " + mur_width);

        // Si l'image touche le mur, la positionner en fonction du côté
        if (( snakePosX + snake_width >= murX ) && ( snakePosX <= murX + mur_width )) {
            if (( snakePosY + snake_height >= murY ) && ( snakePosY <= murY + mur_height )) {
                // Gauche
                if (snakePosX + snake_width >= murX && snakePosX <= murX) {
                    System.out.println("Gauche");
                    return 1;
                }
                // Droite
                if (snakePosX <= murX + mur_width && snakePosX + snake_width >= murX + mur_width) {
                    System.out.println("Droite");
                    return 2;
                }
                // Haut
                if (snakePosY + snake_height >= murY && snakePosY <= murY) {
                    System.out.println("Haut");
                    return 3;
                }
                // Bas
                if (snakePosY <= murY + mur_height && snakePosY + snake_height >= murY + mur_height) {
                    System.out.println("Bas");
                    return 4;
                }
            }
        }*/

    /**
     * Méthode qui empêche le snake de traverser le mur
     *
     * @param //checkResultColision est le côté touché
     */
        /*public static void restraintBlock (int checkCollisionResult){
            switch (checkResultColision) {
                case 1:
                    // Gauche
                    snakePosX = (int) mur.getX() - snake_width - 0.1;
                    break;

                case 2:
                    // Droite
                    snakePosX = (int) mur.getX() + mur.getWidth() + 0.1;
                    break;

                case 3:
                    // Haut
                    snakePosY = (int) mur.getY() - snake_height - 0.1;
                    break;

                case 4:
                    // Bas
                    snakePosY = (int) mur.getY() + mur.getHeight() + 0.1;
                    break;
            }

            // Déplacer le snake
            snake.setX((float) snakePosX);
            snake.setY((float) snakePosY);
        }
        // Aucune collision
        System.out.println("Pas col");
        return 0;
    }*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int a = event.getActionMasked();

        switch (a) {
            case MotionEvent.ACTION_MOVE: {
                if (move == false) {
                    mx = event.getX();
                    my = event.getY();
                    move = true;
                } else {
                    if (mx - event.getX() > 100 * Constants.SCREEN_WIDTH / 1080 && !snake.isMove_right()) {
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_left(true);
                        isPlaying = true;
                    } else if (event.getX() - mx > 100 * Constants.SCREEN_WIDTH / 1080 && !snake.isMove_left()) {
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_right(true);
                        isPlaying = true;
                    } else if (event.getY() - my > 100 * Constants.SCREEN_WIDTH / 1080 && !snake.ismove_up()) {
                        mx = event.getX();
                        my = event.getY();
                        snake.setmove_down(true);
                        isPlaying = true;

                    } else if (my - event.getY() > 100 * Constants.SCREEN_WIDTH / 1080 && !snake.ismove_down()) {
                        mx = event.getX();
                        my = event.getY();
                        snake.setmove_up(true);
                        isPlaying = true;
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                mx = 0;
                my = 0;
                move = false;
                break;
            }

        }
        return true;
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        for (int i = 0; i < arrGrass.size(); i++) {
            canvas.drawBitmap(arrGrass.get(i).getBitmap(), arrGrass.get(i).getX(),
                    arrGrass.get(i).getY(), null);
        }
        if (isPlaying) {
            snake.update();
            if (snake.getArrPartSnake().get(0).getX() < this.arrGrass.get(0).getX()
                    || snake.getArrPartSnake().get(0).getY() < this.arrGrass.get(0).getY()
                    || snake.getArrPartSnake().get(0).getY() + sizeOfMap > this.arrGrass.get(this.arrGrass.size() - 1).getY() + sizeOfMap
                    || snake.getArrPartSnake().get(0).getX() + sizeOfMap > this.arrGrass.get(this.arrGrass.size() - 1).getX() + sizeOfMap) {
                isPlaying = false;
                this.reset();
            }
            for (int i = 1; i < snake.getArrPartSnake().size(); i++) {
                if (snake.getArrPartSnake().get(0).getrBody().intersect(snake.getArrPartSnake().get(i).getrBody())) {
                    isPlaying = false;
                    this.reset();
                }
            }
        }
        snake.drawSnake(canvas);
        apple.draw(canvas);
        if (snake.getArrPartSnake().get(0).getrBody().intersect(apple.getRect())) {
            apple.reset(arrGrass.get(randomApple()[0]).getX(), arrGrass.get(randomApple()[1]).getY());
            snake.addPart();
        }
        handler.postDelayed(runnable, 350);
    }

    public void reset() {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (( j + i ) % 2 == 0) {
                    arrGrass.add(new Grass(bmGrass1, j * sizeOfMap + Constants.SCREEN_WIDTH / 2 - ( w / 2 ) * sizeOfMap,
                            i * sizeOfMap + Constants.SCREEN_HEIGHT / 1920, sizeOfMap, sizeOfMap));

                } else {
                    arrGrass.add(new Grass(bmGrass2, j * sizeOfMap + Constants.SCREEN_WIDTH / 2 - ( w / 2 ) * sizeOfMap,
                            i * sizeOfMap + Constants.SCREEN_HEIGHT / 1920, sizeOfMap, sizeOfMap));
                }
            }
        }
        snake = new Snake(bmSnake, arrGrass.get(25).getX(), arrGrass.get(60).getY(), 2);
        apple = new Apple(bmApple, arrGrass.get(randomApple()[0]).getX(), arrGrass.get(randomApple()[1]).getY());
    }
}
