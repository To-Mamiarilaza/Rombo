package weapon;
import game.GameObject;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import joueur.Joueur;

public class Fleche extends GameObject {
/// Attributs de classe
    int vitesse;            // vitesse du fleche
    int longueur;           // Longueur du fleche
    int degat;              // taux de degats
    Joueur proprietaire;    // le joueur lanceur
    boolean actif = false;          // true si c'est la fleche actif    // La fleche active se trouve a l'indice 0
    boolean tir = false;            // true si une tir est effectue
    boolean demande = false;            // en attends deja que si tu a fini de tirer tu te place au canon
    long duration;          // temps de voyage lorsqu'on tir

/// Encapsulation
    public void setDuration(long duration) {this.duration = duration;}
    public long getDuration() {return this.duration;}

    public void setTir(boolean tir) {
        if (tir)  {
            setDuration(System.currentTimeMillis()); 
        }
        this.tir = tir;
    }
    public boolean getTir() {return this.tir;}

    public void setVitesse(int vitesse) {this.vitesse = vitesse;}
    public int getVitesse() {return this.vitesse;}

    public void setLongueur(int longueur) {this.longueur = longueur;}
    public int getLongueur() {return this.longueur;}

    public void setDemande(boolean demande) {this.demande = demande;}
    public boolean getDemande() {return this.demande;}

    public void setActif(boolean actif) {this.actif = actif;}
    public boolean getActif() {return this.actif;}

    public void setDegat(int degat) {this.degat = degat;}
    public int getDegat() {return this.degat;}

    public void setProprietaire(Joueur proprietaire) {this.proprietaire = proprietaire;}
    public Joueur getProprietaire() {return this.proprietaire;}

/// Constructeur
    public Fleche(Joueur proprietaire) {
        setProprietaire(proprietaire);
        setX(getProprietaire().getX());
        setVitesse(6);
        setY(getProprietaire().getY());
        setLongueur(35);
        setDemande(true);
    }

/// Fonctions de classe
    public void checkDuration() {
        // Attends un peu avant de recharger la balle
        if (getDemande() && System.currentTimeMillis() - getDuration() >= 500) {
            setDemande(false);      // Pour que ce soit unique
            getProprietaire().changeActifFleche();
            getProprietaire().setTir(false);
            getProprietaire().getJeu().getClient().sendMessage("Tir:" + getProprietaire().getNom() + ",tir:false");
        }

        // Verifier si la fleche a deja vecu plus de cind seconde
        if (System.currentTimeMillis() - getDuration() >= 2000) {
            setTir(false);
            setActif(false);
            setDemande(true);
        }
    }

    public float[] getCoordDeplacement(double angle) {       
        // transforme l'angle en un coordonees de deplacement  
        angle = Math.toRadians(angle);
        float[] resultat = new float[2];
        resultat[0] = (float) Math.cos(angle);
        resultat[1] = (float) Math.sin(angle);
        return resultat;
    }

    public void feu() {
        /// Appeler lorsque le joueur Tir
        float[] coord = getCoordDeplacement((double) getAngle());
        setX(Math.round((float)getX() + 1 * (float)getVitesse() * coord[0]));
        setY(Math.round((float)getY() + 1 * (float)getVitesse() * coord[1]));
        checkDuration();    
    }

    public void followJoueur() {        
        /// Suivre la position du joueur
        setX(getProprietaire().getX() + (getProprietaire().getWidth() / 2) + (getProprietaire().getWidth() / 4));
        setY(getProprietaire().getY() + (getProprietaire().getHeight() / 2));
        setAngle(getProprietaire().getAngle());
    }

    public void selfDraw(Graphics graph) {
        if (actif) {
            Graphics2D g = (Graphics2D) graph;
            if (!tir) {
                followJoueur();
                // g.rotate(Math.toRadians(getAngle()), getProprietaire().getX() + (getProprietaire().getWidth() / 2),getProprietaire().getY() + (getProprietaire().getHeight() / 2));
            }
            else {
                // g.rotate(Math.toRadians(getAngle()), getX(), getY());
                feu();
            }
            
            graph.fillOval(getX() + getLongueur(), getY() - getProprietaire().getHeight() / 12, getProprietaire().getWidth() / 4, getProprietaire().getHeight() / 6);    // Le canon
            graph.setColor(Color.red);
            graph.drawLine(getX(), getY(), getX() + getLongueur(), getY());    // Le canon
            // g.rotate(Math.toRadians(-1 * getAngle()), getX(), getY());
        }
    }

    public int[] getRotationPosition(double x, double y, double x0, double y0, double angle) {
        int resultX =(int)(Math.cos(angle) * (x - x0) - (y - y0) * Math.sin(angle) + x0);
        int resultY = (int)(Math.cos(angle) * (y - y0) + (x - x0) * Math.sin(angle) + y0);
        int[] resultat = new int[2];
        resultat[0] = resultX;
        resultat[1] = resultY;
        return resultat;
    }   

}