package com.example.appsnake;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {
    // Création variable
    // Création des Bitmap pour l'herbe, le snake et la pomme
    private Bitmap bmGrass1, bmGrass2, bmSnake, bmApple;
    // Taille de la map sur le téléphone
    public static int sizeOfMap = 70 * Constants.SCREEN_WIDTH / 1080;
    // Définir le nombre d'images que l'on souhaite en hauteur et en largeur
    private int h = 7, w = 16;
    // Liste qui s'occupe de stocker nos images pour créer la map
    private ArrayList<Grass> arrGrass = new ArrayList<>();
    // Variable snake
    private Snake snake;
    // Variable de vérification si le snake bouge.
    private boolean move = false;
    // Variable mx = que le serpent bouge sur l'axe x
    // Variable my = que le serpent bouge sur l'axe y
    private float mx, my;
    // Création handler
    private android.os.Handler handler;
    // Création runnable
    private Runnable runnable;
    // Variable pomme
    private Apple apple;
    // Variable de vérification si le jeux est en jeux.
    public static boolean isPlaying = false;
    private Context context;
    // Variable qui s'occupe du score
    private int score;
    // Variable de vérification si le bouton "Start" est clické
    private boolean start = false;

    /**
     * Constructeur
     *
     * @param context      Context qui est utilisé
     * @param attributeSet Les attributs
     */
    public GameView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        // Enregistrement du score pour pouvoir le réutiliser plus tard
        SharedPreferences sharedPreferences = context.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            score = sharedPreferences.getInt("score", 0);
        }
        start = false;
        // S'occupe de créer la map
        // Va chercher les images souhaité pour la création de la map
        bmGrass1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.img_grass);
        bmGrass1 = Bitmap.createScaledBitmap(bmGrass1, sizeOfMap, sizeOfMap, true);
        bmGrass2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.img_grass03);
        bmGrass2 = Bitmap.createScaledBitmap(bmGrass2, sizeOfMap, sizeOfMap, true);
        bmSnake = BitmapFactory.decodeResource(this.getResources(), R.drawable.snake1);
        bmSnake = Bitmap.createScaledBitmap(bmSnake, 14 * sizeOfMap, sizeOfMap, true);
        bmApple = BitmapFactory.decodeResource(this.getResources(), R.drawable.apple);
        bmApple = Bitmap.createScaledBitmap(bmApple, sizeOfMap, sizeOfMap, true);

        // Création de la map selon la taille de notre écran
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
        // Création du snake, de la pomme, du handler et du runnable
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

    /**
     * Méthode appelé pour placer aléatoirement la pomme dans la map
     *
     * @return La position x et y de la pomme
     */
    public int[] randomApple() {
        int[] xy = new int[2];
        Random random = new Random();
        // Mettre une limite pour pas que la pomme se génère en dehors de l'écran
        xy[0] = random.nextInt(arrGrass.size() - 1);
        xy[1] = random.nextInt(arrGrass.size() - 1);
        // Création de la position de la pomme
        Rect rect = new Rect(arrGrass.get(xy[0]).getX(), arrGrass.get(xy[1]).getY(), arrGrass.get(xy[0]).getX() + sizeOfMap, arrGrass.get(xy[1]).getY() + sizeOfMap);
        // Variable de vérification si la pomme à bien àtà générée
        boolean check = true;
        // Tant que la variable check est à true, on génère la pomme
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
     * Méthode appelé quand on touche l'écran pour faire bouger le snake
     *
     * @param event The motion event.
     * @return true si l'écran est touché, sinon cela retourne false.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int toucheEcran = event.getActionMasked();

        switch (toucheEcran) {
            // Case si on touche l'écran
            case MotionEvent.ACTION_MOVE: {

                // Vérification si la variable est à faux
                if (move == false) {
                    // Alors le mouvement sur l'axe x revient à la position x
                    mx = event.getX();
                    // Alors le mouvement sur l'axe y revient à la position y
                    my = event.getY();
                    // Variable de mouvement = true
                    move = true;
                } else {

                    // Test si le mouvement est à gauche
                    if (mx - event.getX() > 100 * Constants.SCREEN_WIDTH / 1080 && !snake.isMove_right()) {
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_left(true);
                        isPlaying = true;

                        // Test si le mouvement est à droite
                    } else if (event.getX() - mx > 100 * Constants.SCREEN_WIDTH / 1080 && !snake.isMove_left()) {
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_right(true);
                        isPlaying = true;

                        // Test si le mouvement est en bas
                    } else if (event.getY() - my > 100 * Constants.SCREEN_WIDTH / 1080 && !snake.ismove_up()) {
                        mx = event.getX();
                        my = event.getY();
                        snake.setmove_down(true);
                        isPlaying = true;

                        // Test si le mouvement est en haut
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


    /**
     * Méthode appelé pour dessiner les parties du snake, du snake et de la pomme
     *
     * @param canvas The Canvas to which the View is rendered.
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        // Vérifie si le bouton start est pressé alors on fait ce bout de code
        if (start) {
            for (int i = 0; i < arrGrass.size(); i++) {
                // Dessin de la map
                canvas.drawBitmap(arrGrass.get(i).getBitmap(), arrGrass.get(i).getX(),
                        arrGrass.get(i).getY(), null);
            }
            // Si le joueur joue
            if (isPlaying) {
                // Appel de la méthode update
                snake.update();
                // Vérification si le snake à une collision avec les bords de l'éran
                // Egal game over
                if (snake.getArrPartSnake().get(0).getX() < this.arrGrass.get(0).getX()
                        || snake.getArrPartSnake().get(0).getY() < this.arrGrass.get(0).getY()
                        || snake.getArrPartSnake().get(0).getY() + sizeOfMap > this.arrGrass.get(this.arrGrass.size() - 1).getY() + sizeOfMap
                        || snake.getArrPartSnake().get(0).getX() + sizeOfMap > this.arrGrass.get(this.arrGrass.size() - 1).getX() + sizeOfMap) {
                    // Mettre que le jeux est terminé
                    isPlaying = false;
                    // Rendre l'activité game over à visible
                    MainActivity.rl_game_over.setVisibility(VISIBLE);
                }
                // Regarde si le snake se mort lui même
                // Egal game over
                for (int i = 1; i < snake.getArrPartSnake().size(); i++) {
                    if (snake.getArrPartSnake().get(0).getrBody().intersect(snake.getArrPartSnake().get(i).getrBody())) {
                        // Mettre que le jeux est terminé
                        isPlaying = false;
                        // Rendre l'activité game over à visible
                        MainActivity.rl_game_over.setVisibility(VISIBLE);
                    }
                }
            }
            // Dessiner le snake
            snake.drawSnake(canvas);
            // Dessiner la pomme
            apple.draw(canvas);
            // Vérification si le snake mange la pomme
            // Alors on incrémente le score
            // On génère une nouvelle pomme dans la map
            // Et on ajoute une partie du corps au snake = il s'allonge quand il mange une pomme
            if (snake.getArrPartSnake().get(0).getrBody().intersect(apple.getRect())) {
                apple.reset(arrGrass.get(randomApple()[0]).getX(), arrGrass.get(randomApple()[1]).getY());
                snake.addPart();
                score++;
                MainActivity.txt_score.setText(score + "");
            }
            handler.postDelayed(runnable, 350);
        }
    }

    /**
     * Méthode appelé pour remettre le jeux de base à zéro
     * Si l'on souhaite rejouer
     */
    public void reset() {
        // Redessine la map du jeux
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
        // Redessine le snake
        snake = new Snake(bmSnake, arrGrass.get(25).getX(), arrGrass.get(60).getY(), 2);
        // Redessine la pomme
        apple = new Apple(bmApple, arrGrass.get(randomApple()[0]).getX(), arrGrass.get(randomApple()[1]).getY());
        // Mettre le score à 0
        score = 0;
        // Afficher le score à 0
        MainActivity.txt_score.setText(score + "");
    }

    /**
     * Méthode appelé quand le jeux commence
     *
     * @return l'état du jeux
     */
    public boolean isStart() {
        return start;
    }

    /**
     * Setter du start
     *
     * @param start l'état du jeux
     */
    public void setStart(boolean start) {
        this.start = start;
    }
}
