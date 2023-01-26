package com.example.appsnake.models;

import android.database.Cursor;
import android.media.session.PlaybackState;

import androidx.annotation.NonNull;

public class Classement {
    // Création variable
    private String player;
    private int scorePlayer;


    /**
     * Constructeur de la classe "Classement"
     * @param cursor Cursor qui contient les données du classement
     */
    public Classement(Cursor cursor) {
        try {

            player = cursor.getString(cursor.getColumnIndexOrThrow("player"));
            scorePlayer = cursor.getInt(cursor.getColumnIndexOrThrow("score"));

        }catch (IllegalArgumentException e) {
            throw new RangeInvalide();
        }
    }

    /**
     * Getter du nom du joueur
     * @return le nom du joueur
     */
    public String getPlayer() {
        return player;
    }

    /**
     * Getter du score du joueur
     * @return le score du joueur
     */
    public int getScorePlayer() {
        return scorePlayer;
    }

    @NonNull
    @Override
    public String toString() {
        return player + " : " + scorePlayer;
    }
}
