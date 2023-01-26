package com.example.appsnake.controllers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appsnake.models.Classement;
import com.example.appsnake.models.SnakeOpenHelper;

import java.util.ArrayList;

public class ClassementManager {

    // Appelation de la base de donnée
    private SnakeOpenHelper snakeOpenHelper;
    // Création de base d'une ArrayList d'un classement
    private static ArrayList<Classement> userListe;
    // Variable de vérification si le update a bien été fait
    private boolean checkUpdate = false;

    public ClassementManager(Context context) {

        snakeOpenHelper = new SnakeOpenHelper(context);
        userListe = getObtenirClassementDB();
    }

    /**
     * Charge une liste de joueurs depuis la DB.
     *
     * @return Une arraylist charger de joueurs
     */
    private ArrayList<Classement> getObtenirClassementDB() {

        // Obtenir tous les scores depuis le classement qui est dans le cursor
        SQLiteDatabase db = snakeOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(false, "tb_classement", new String[]{"player", "score"}, null, null, null,null , "score", null);

        // Lire le cursor
        ArrayList<Classement> listeUsers = new ArrayList<>();
        while (cursor.moveToNext()) {
            // Ajouter le score à la liste
            listeUsers.add(new Classement(cursor));
        }
        cursor.close();
        db.close();

        return listeUsers;
    }

    /**
     * Ajouter une rangée à la base de données
     * @param player Le nom du joueur
     * @param score Le score du joueur
     * @return True si le score est ajouté sinon false
     */
    public boolean addRanking(String player, int score) {
        checkUpdate = true;
        return snakeOpenHelper.addInsertUsers(player, score);
    }
    /**
     * Get the lowest ranking
     * @return The lowest ranking if it exists, null otherwise
     */
    public Classement getLowScore() {
        // If any changes were made to the ranking list
        rechargementListeUpdate();

        if (userListe.size() > 0) {
            // Get the lowest ranking
            return userListe.get(userListe.size() - 1);
        }
        return null;
    }

    /**
     * Get the ranking list
     * @return The ranking list
     */
    public ArrayList<Classement> getAllRankings() {
        rechargementListeUpdate();
        return userListe;
    }

    /**
     * Obtenir tous les classements jusqu'à une certaine position
     * @param limit The amount of rankings to get
     * @return The rankings up to the limit
     */
    public ArrayList<Classement> getRankings(int limit) {
        rechargementListeUpdate();

        // Si la limite est supérieure à la taille de la liste
        if (limit > userListe.size()) {
            // Définir la limite de taille de la liste
            limit = userListe.size();
        }

        ArrayList<Classement> list = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            list.add(userListe.get(i));
        }
        return list;
    }

    /**
     * Rechargement de la liste si la mise à jour est à true.
     */
    private void rechargementListeUpdate() {
        // If any changes were made to the ranking list
        if (checkUpdate) {
            // Rechargement du classement de la liste
            userListe = getObtenirClassementDB();
            checkUpdate = false;
        }
    }
}
