package joueur;
public class CollisionAnimation extends Thread {
/// Attributs
    Joueur joueur;

/// Encapsulation
    public void setJoueur(Joueur joueur) {this.joueur = joueur;}
    public Joueur getJoueur() {return this.joueur;}

/// Constructeur
    public CollisionAnimation(Joueur joueur) {
        setJoueur(joueur);
    }

/// Fonctions de classe
    public void startCollision() {
        long debut = System.currentTimeMillis();
        while(System.currentTimeMillis() - debut < 1000) {  // duree de mouvement collision 1 seconde
            getJoueur().setAngle(getJoueur().getAngle() + 1);
        }
        this.stop();
    }

    public void run() {
        startCollision();
    }
}