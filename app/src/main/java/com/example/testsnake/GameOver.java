package com.example.testsnake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testsnake.GameView;
import com.example.testsnake.MainActivity;
import com.example.testsnake.R;

public class GameOver extends AppCompatActivity {

    // Instantier les bouttons
    Button bt_quitter;
    Button bt_restart;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        bt_quitter = findViewById(R.id.bt_quitter);
        bt_restart = findViewById(R.id.bt_restart);

        System.out.println("Lancement de game over");
        bt_quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quitterSnake = new Intent(GameOver.this, MainActivity.class);
                startActivity(quitterSnake);
            }
        });

        bt_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent restartGame = new Intent(GameOver.this, GameView.class);
                startActivity(restartGame);
            }
        });

    }
}
