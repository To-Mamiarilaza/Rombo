package joueur;
import game.GameObject;
import manager.Partie;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import weapon.Fleche;

public class Joueur extends GameObject {
/// Attributs
    String nom;
    int point;      // Point quand on marque
    int vitesse;    // Vitesse de deplacement du joueur
    Fleche[] listesFleche;      // les fleches du joueur
    int actif;      // indice du fleche actif
    boolean tir;
    Color[] couleur;    // indice 0 couleur principale 1 // autre
    Partie jeu;

    /// Pour les key de deplacements
    protected boolean up = false;
    protected boolean down = false;
    protected boolean left = false;
    protected boolean right = false;

/// Encapsulation

    /// Pour passer les deplacement
    public void setUp(boolean value) {this.up = value;}
    public void setDown(boolean value) {this.down = value;}
    public void setLeft(boolean value) {this.left = value;}
    public void setRight(boolean value) {this.right = value;}

    public void setListesFleche(Fleche[] listesFleche) {this.listesFleche = listesFleche;}
    public Fleche[] getListesFleche() {return this.listesFleche;}
    public Fleche getFleche(int indice) {
        return getListesFleche()[indice];
    } 
    public Fleche getFlecheActive() {return getListesFleche()[getActif()];}

    public void setJeu(Partie jeu) {this.jeu = jeu;}
    public Partie getJeu() {return this.jeu;}

    public void setCouleur(Color[] couleur) {this.couleur = couleur;}
    public Color[] getCouleur() {return this.couleur;}

    public void setNom(String nom) {this.nom = nom;}
    public String getNom() {return this.nom;}

    public void setTir(boolean tir) {
        if(tir) getFlecheActive().setTir(true);
        this.tir = tir;
    }
    public boolean getTir() {return this.tir;}

    public void setPoint(int point) {this.point = point;}
    public int getPoint() {return this.point;}

    public void setActif(int actif) {this.actif = actif;}
    public int getActif() {return this.actif;}

    public void setVitesse(int vitesse) {this.vitesse = vitesse;}
    public int getVitesse() {return this.vitesse;}

/// Constructeur
    public Joueur(String nom, Partie jeu) {
        setNom(nom);
        setX(300);
        setY(300);
        setJeu(jeu);
        setAngle(0);
        setVitesse(1);
        setWidth(40);
        setHeight(40);
        setPoint(0);
        setActif(0);
        prepareCouleur();
        prepareJoueurFleche();
    }

/// Fonctions de classe

    /// Deplacement du joueur
    public void move() {
        if (up) {
            setY(getY() - getVitesse());
            getJeu().getClient().sendMessage("MouvementY:" + getNom() + ",Y:" + getY());
        }
        if (down) {
            setY(getY() + getVitesse());
            getJeu().getClient().sendMessage("MouvementY:" + getNom() + ",Y:" + getY());
        } 
        if (left) {
            setX(getX() - getVitesse());
            getJeu().getClient().sendMessage("MouvementX:" + getNom() + ",X:" + getX());
        }
        if (right)  {
            setX(getX() + getVitesse());
            getJeu().getClient().sendMessage("MouvementX:" + getNom() + ",X:" + getX());
        }
    }

    public double generateRandom(double a, double b) {
        /// Donne un nombre en random entre a et b
        double x = Math.random();
        double resultat = (x * (b - a + 1) + a);
        return resultat;
    } 

    public void prepareCouleur() {
        /// Prepare le couleur 
        Color[] listesColors = new Color[2];
        for(int i = 0; i < listesColors.length; i++) {
            listesColors[i] = new Color((int) generateRandom(0, 255), (int) generateRandom(0, 255), (int) generateRandom(0, 255));
        }
        setCouleur(listesColors);
    }

    public void changeActifFleche() {
        // Changer la fleche actif
        setActif((getActif() + 1) % 5);
        getListesFleche()[getActif()].setActif(true);        // Active le nouveau fleche
    }

    public void prepareJoueurFleche() {
        /// Donne du fleche au joueur
        Fleche[] listes = new Fleche[5];        // Le nombre de fleche donnee
        for(int i = 0; i < listes.length; i++) {
            listes[i] = new  Fleche(this);
        }
        listes[getActif()].setActif(true);       // Active le premier fleche
        setListesFleche(listes);
    }
    
    public void flecheMouvement(Graphics graph) {
        for(int i = 0; i < getListesFleche().length; i++) {
            getListesFleche()[i].selfDraw(graph);
        }
    }

    public void selfDraw(Graphics graph) {
        /// Chaque player dessine eux meme

        graph.setColor(getCouleur()[0]);       // Couleur principale
        graph.fillOval(getX(), getY(), getWidth(), getHeight());        // Le corp du joueur
        graph.setColor(getCouleur()[1]);
        graph.fillOval(getX() + (getWidth() / 2) - (getWidth() / 4), getY() + (getHeight() / 2) - (getHeight() / 4), getWidth() / 2, getHeight() / 2);      // Le centre du joueur
        Graphics2D g = (Graphics2D) graph;
        BasicStroke line = new BasicStroke(2);
        g.setStroke(line);
        g.drawOval(getX(), getY(), getWidth(), getHeight());    // le bordure
        g.setColor(getCouleur()[1]);
        g.rotate(Math.toRadians(getAngle()), getX() + (getWidth() / 2), getY() + (getHeight() / 2));
        g.fillRect(getX() + (getWidth() / 2), getY() + (getHeight() / 2) - (getHeight() / 10), getWidth(), getHeight() / 5);    // Le canon
        g.rotate(Math.toRadians(-1 * getAngle()), getX() + (getWidth() / 2), getY() + (getHeight() / 2));       // inversement pour rendre l'angle a 0
        
        flecheMouvement(graph);
    }

    public String getEncodage() {
        String resultat = "Joueur:" + getNom() + ",X:" + getX() + ",Y:" + getY() + ",Angle:" + getAngle() + ",Vitesse:" + getVitesse() + ",Tir:" + getTir();
        return resultat;
    }

    public void detailJoueur() {
        /// Montre le detail de chaque joueur
        System.out.println("Detail du Joueur : " + getNom());
        System.out.println("--> X position   : " + getX());
        System.out.println("--> Y position   : " + getY());
        System.out.println("--> Point        : " + getPoint());
    }
}