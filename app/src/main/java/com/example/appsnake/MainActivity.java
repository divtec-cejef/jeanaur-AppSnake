package com.example.appsnake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static TextView txt_score, txt_pseudo;
    public static RelativeLayout rl_game_over;
    public static Button btn_start, bt_restart, bt_quitter, bt_sauvegarder;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Constants.SCREEN_WIDTH = displayMetrics.widthPixels;
        Constants.SCREEN_HEIGHT = displayMetrics.heightPixels;
        setContentView(R.layout.activity_main);
        txt_score = findViewById(R.id.txt_score);
        rl_game_over = findViewById(R.id.rl_game_over);
        gameView = findViewById(R.id.ghost_view);
        btn_start = findViewById(R.id.btn_start_game);
        bt_restart = findViewById(R.id.bt_restart);
        bt_quitter = findViewById(R.id.bt_quitter);

        /**
         * Méthode appelé lorsque l'on clique sur le bouton start, cela démarre le jeux
         */
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.setStart(true);
                txt_score.setVisibility(View.VISIBLE);
                btn_start.setVisibility(View.INVISIBLE);
                gameView.draw(new Canvas());
            }
        });

        /**
         * Méthode appelé lorsque le bouton restart est cliqué
         * Cela supprime l'ancienne partie et crée une nouvelle
         */
        bt_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.setStart(false);
                gameView.reset();
                btn_start.setVisibility(View.VISIBLE);
                rl_game_over.setVisibility(View.INVISIBLE);
            }
        });

        /**
         * Méthode appelé lorsque le bouton quitter est cliqué
         * Cela quitte le jeux et fait un reset de celui-ci.
         */
        bt_quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.reset();
                finish();
            }
        });
    }
}