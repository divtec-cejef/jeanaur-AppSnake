package com.example.appsnake.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SnakeOpenHelper extends SQLiteOpenHelper {

    // Nom de notre base
    static String DB_NAME = "SnakeClassement.db";
    static int DB_VERSION = 1;

    /**
     * Constructeur de la classe "SnakeOpenHelper"
     *
     * @param context Est l'activité utilisé
     */
    public SnakeOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Création de la table en insérant des données à la main
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS tb_classement(" +
                "idUsers INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nameUsers TEXT," +
                "scoreUsers INTEGER);");
    }

    /**
     * Ajouter le score dans la base de donnée
     *
     * @param player l'utilisateur
     * @param score  le score à ajouter
     * @return true si le score est insérer sinon false
     */
    public boolean addInsertUsers(String player, int score) {
        try {
            // Insérer le nouveau score de l'utilisateur
            getWritableDatabase().execSQL("INSERT INTO tb_classement(player, score)" +
                    " VALUES('" + player + "', " + score + ");");

            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * Donne  le score le plus bas
     *
     * @return le score le plus bas
     */
    private int getLowScore() {
        // Selectionne le score le plus bas qu'un utilisateur a fait.
        Cursor cursor = getReadableDatabase().rawQuery("SELECT MIN(score) FROM tb_classement", null);
        cursor.moveToFirst();
        int scoreBas = cursor.getInt(0);
        cursor.close();

        return scoreBas;
    }

    /**
     * Calcule le nombre de ligne
     *
     * @return le nombre de ligne
     */
    private int getRowCount() {
        return (int) getReadableDatabase().compileStatement("SELECT COUNT(*) FROM tb_classement;").simpleQueryForLong();
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
