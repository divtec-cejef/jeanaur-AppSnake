package com.example.testsnake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {//implements SensorEventListener {

    //private SensorManager sensorManager;
    // La gravité
    //private Sensor gravity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Constants.SCREEN_WIDTH = displayMetrics.widthPixels;
        Constants.SCREEN_HEIGHT = displayMetrics.heightPixels;
        setContentView(R.layout.activity_main);
        // Service de gestion des capteurs
        //sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Instancier le capteur de gravité
        //gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
    }
}

/**
 * Méthode qui arrête les actions en cours
 * <p>
 * Méthode qui initialise le capteur de gravité
 * <p>
 * Méthode utilisé pour recevoir des notifications du SensorManager lorsque la précision
 * du capteur à changé.Pas utilisé dans ce code
 *
 * @param sensor   Le capteur
 * @param accuracy La précision du capteur
 * <p>
 * Méthode appelée lorsque le capteur détecte un changement
 * @param event événement du changement
 * <p>
 * Méthode qui initialise le capteur de gravité
 * <p>
 * Méthode utilisé pour recevoir des notifications du SensorManager lorsque la précision
 * du capteur à changé.Pas utilisé dans ce code
 * @param sensor   Le capteur
 * @param accuracy La précision du capteur
 * <p>
 * Méthode appelée lorsque le capteur détecte un changement
 * @param event événement du changement
 * <p>
 * Méthode qui initialise le capteur de gravité
 * <p>
 * Méthode utilisé pour recevoir des notifications du SensorManager lorsque la précision
 * du capteur à changé.Pas utilisé dans ce code
 * @param sensor   Le capteur
 * @param accuracy La précision du capteur
 * <p>
 * Méthode appelée lorsque le capteur détecte un changement
 * @param event événement du changement
 */
   /* @Override
    protected void onPause() {
        // Désenregistrer du capteur
        sensorManager.unregisterListener(this, gravity);
        super.onPause();
    }
*/

/**
 * Méthode qui initialise le capteur de gravité
 */
    /*@Override
    protected void onResume() {
        // Enregistrement du capteur et son rythme
        sensorManager.registerListener(this, gravity, SensorManager.SENSOR_DELAY_GAME);
        super.onResume();
    }
*/

/**
 * Méthode utilisé pour recevoir des notifications du SensorManager lorsque la précision
 * du capteur à changé.Pas utilisé dans ce code
 *
 * @param sensor   Le capteur
 * @param accuracy La précision du capteur
 */
   /* @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }*/


/**
 * Méthode appelée lorsque le capteur détecte un changement
 *
 * @param event événement du changement
 */
    /*public void onSensorChanged(SensorEvent event) {
        // Récupération de l'inclinaison et modification en nombre positif pour travailler avec
        //float valeurX = Math.abs(event.values[1] * 100 / SensorManager.GRAVITY_EARTH);
        //float valeurY = Math.abs(event.values[0] * 100 / SensorManager.GRAVITY_EARTH);

        // Marge pour le contrôle de l'inclinaison
        //double marge = 2.5;
        //System.out.println("-----");
        // Variable vérification des collisions
        //int checkCollisionResult = GameView.checkIfCollision();

        //System.out.println("Résultat collision : " + checkCollisionResult);

        // Vérification d'une collision
        /*if (checkCollisionResult > 0) {
            System.out.println("Replacement");
            // Déplacer le snake plus loin
            GameView.restraintBlock(checkCollisionResult);
        } else {
            // Déplacer le snake
            // Vérification de la force d'inclinaison pour que le joueur ne se déplace pas quand le
            // telephone est à plat.
            System.out.println("Déplacement");
            if (( valeurX < marge && valeurY > marge ) ||
                    ( valeurX > marge && valeurY < marge ) ||
                    ( valeurX > marge && valeurY > marge )) {

                // Appel de la méthode moveBlock
                GameView.moveBlock(-event.values[1] * 100 / SensorManager.GRAVITY_EARTH,
                        event.values[0] * 100 / SensorManager.GRAVITY_EARTH);
            }
        } */
//}
//}