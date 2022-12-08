package joueur;
import game.GameObject;
import manager.Partie;
import mur.Mur;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Vector;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import weapon.Fleche;

public class Joueur extends GameObject {
/// Attributs
    String nom;
    int point;      // Point quand on marque
    int vitesse;    // Vitesse de deplacement du joueur
    Fleche[] listesFleche;      // les fleches du joueur
    int actif;      // indice du fleche actif
    boolean tir;
    long debutCollision; // temps de debut collision
    long debutEsquive;      // temps de debut esquive
    Color[] couleur;    // indice 0 couleur principale 1 // autre
    Partie jeu;
    Fleche tireur;  // le bal qui m'a touche
    int vie;
    boolean dead;
    boolean canEsquive;     // true si on a le droit d'esquiver

    /// Pour les key de deplacements
    protected boolean up = false;
    protected boolean down = false;
    protected boolean left = false;
    protected boolean right = false;
    protected boolean esquive = false;

/// Encapsulation

    /// Pour passer les deplacement
    public void setUp(boolean value) {this.up = value;}
    public void setDown(boolean value) {this.down = value;}
    public void setLeft(boolean value) {this.left = value;}
    public void setRight(boolean value) {this.right = value;}
    public void setEsquive(boolean value) {
        if (value) {
            setCanEsquive(false);
            setDebutEsquive(System.currentTimeMillis());
        }
        this.esquive = value;
    }

    public void setListesFleche(Fleche[] listesFleche) {this.listesFleche = listesFleche;}
    public Fleche[] getListesFleche() {return this.listesFleche;}
    public Fleche getFleche(int indice) {
        return getListesFleche()[indice];
    } 
    public Fleche getFlecheActive() {return getListesFleche()[getActif()];}

    public void setDebutEsquive(long debutEsquive) {this.debutEsquive = debutEsquive;}
    public long getDebutEsquive() {return this.debutEsquive;}

    public void setDebutCollision(long debutCollision) {this.debutCollision = debutCollision;}
    public long getDebutCollision() {return this.debutCollision;}

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

    public void setTireur(Fleche tireur) {this.tireur = tireur;}
    public Fleche getTireur() {return this.tireur;}

    public void setPoint(int point) {this.point = point;}
    public int getPoint() {return this.point;}

    public void setActif(int actif) {this.actif = actif;}
    public int getActif() {return this.actif;}

    public void setVitesse(int vitesse) {this.vitesse = vitesse;}
    public int getVitesse() {return this.vitesse;}

    public void setVie(int vie) {this.vie = vie;}
    public int getVie() {return this.vie;}

    public void setDead(boolean dead) {this.dead = dead;}
    public boolean getDead() {return this.dead;}

    public void setCanEsquive(boolean canEsquive) {this.canEsquive = canEsquive;}
    public boolean getCanEsquive() {return this.canEsquive;}

/// Constructeur
    public Joueur(String nom, Partie jeu) {
        setNom(nom);
        // setInitialPosition();
        setJeu(jeu);
        setX(300);
        setY(300);
        setAngle(0);
        setVitesse(1);
        setWidth(30);
        setHeight(30);
        setPoint(0);
        setActif(0);
        setVie(5);
        setDead(false);
        setCanEsquive(true);
        prepareCouleur();
        prepareJoueurFleche();
    }

/// Fonctions de classe
    // redefinie le setPosition du classe mere
    @Override
    public void setX(int x) {
        if (isFree(x, getY())) super.setX(x); 
    }
    @Override
    public void setY(int y) {
        if (isFree(getX(), y)) super.setY(y); 
    }

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

    /// Verification des deplacement possible
    public boolean isFree(int x, int y) {
        Vector<Mur> listesMur = getJeu().getListesMur();
        for(int i = 0; i < listesMur.size(); i++) {
            Mur m = listesMur.elementAt(i);
            if (x < 0 || x + getWidth() + 15 >= getJeu().getContainer().getWidth() || y < 0 || y + getHeight() + 40 >= getJeu().getContainer().getHeight()) return false;
            if (x >= m.getX() && x <= m.getX() + m.getWidth() && y >= m.getY() && y <= m.getY() + m.getHeight()) return false;
            if (x + getWidth() >= m.getX() && x + getWidth() <= m.getX() + m.getWidth() && y >= m.getY() && y <= m.getY() + m.getHeight()) return false;
            if (x + getWidth() >= m.getX() && x + getWidth() <= m.getX() + m.getWidth() && y + getHeight() >= m.getY() && y + getHeight() <= m.getY() + m.getHeight()) return false;
            if (x >= m.getX() && x <= m.getX() + m.getWidth() && y + getHeight() >= m.getY() && y + getHeight() <= m.getY() + m.getHeight()) return false;
        }
        return true;
    }

    public void processEsquive() {
        if (System.currentTimeMillis() - getDebutEsquive() < 100) setVitesse(5);
        else setVitesse(1);

        // fin
        if (System.currentTimeMillis() - getDebutEsquive() > 800) {
            setEsquive(false);
            setCanEsquive(true);
        }
    }

    public void startCollision(Fleche tireur) {
        setDebutCollision(System.currentTimeMillis());
        System.out.println("Ma vie est : " + getVie());
        setTireur(tireur);
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

    public void checkRotation() {
        if (getDebutCollision() != 0) {
            // Repoussement
            float[] coord = getTireur().getCoordDeplacement((double) getTireur().getAngle());
            setX(Math.round((float)getX() + 1 * (float)getVitesse() * coord[0]));
            setY(Math.round((float)getY() + 1 * (float)getVitesse() * coord[1]));
            
            int add = 3;   // vitesse de rotation
            if(System.currentTimeMillis() - getDebutCollision() > 700) add = 1;
            setAngle(getAngle() + add);
            if (System.currentTimeMillis() - getDebutCollision() >= 1000) {
                setDebutCollision(0);
            }
        }
        else {
            checkLife();
        }
    }

    public void checkLife() {
        if (getVie() <= 0) setDead(true);
    }

    public void drawPointDeVie(Graphics graph) {
        graph.setColor(Color.RED);
        for(int i = getVie(); i > 0; i--) {
            graph.fillOval(170 + 13 * (i - 1), 6, 10, 10);
        }
    }

    public void drawScore(Graphics graph) {
        graph.setColor(Color.BLUE);
        graph.drawString("Point : " + getPoint(), 250, 15);
        graph.setFont(new Font("Arial", Font.PLAIN, 12));
    }

    public void drawEsquiveMark(Graphics graph) {
        // Dessine un cyan si on peut esquiver
        if (getCanEsquive()) {
            graph.setColor(Color.CYAN);
            graph.fillOval(310, 6, 10, 10);
        }
    }

    public void selfDraw(Graphics graph) {
        /// Chaque player dessine eux meme
        if (esquive) processEsquive();
        checkRotation();        

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