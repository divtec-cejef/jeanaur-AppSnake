package com.example.appsnake;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appsnake.models.SnakeOpenHelper;

/**
 * Classe qui s'occupera de nous afficher nos joueurs du classement
 * <p>
 * Malheureusement je n'ai pas réussi à finir à temps = n'est pas fonctionnelle.
 */
public class ClassementUsers extends AppCompatActivity {

    private EditText txt_pseudo;
    private TextView txt_score;
    private Button bt_sauvegarder;

    SnakeOpenHelper snakeOpenHelper = new SnakeOpenHelper(ClassementUsers.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_sauvegarder = findViewById(R.id.bt_save);
        txt_pseudo = findViewById(R.id.txt_pseudo);
        txt_score = findViewById(R.id.txt_score);

        bt_sauvegarder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Classement classement = new Classement(null, txt_pseudo.getText().toString(), txt_score.setText(txt_score.getText().toString()));

            }
        });

    }
}
