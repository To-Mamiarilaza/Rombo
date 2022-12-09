package container;
import manager.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.net.InetAddress;

import weapon.Fleche;
import javax.swing.*;

import joueur.Joueur;
import listener.*;
import socket.Server;

public class Board extends JPanel {
/// Attributs
    Fenetre container;
    Partie jeu;
    long finPartie = 0;

/// Encapsulation
    public void setFinPartie(long finPartie) {this.finPartie = finPartie;}
    public long getFinPartie() {return this.finPartie;}

    public void setContainer(Fenetre container) {this.container = container;}
    public Fenetre getContainer() {return this.container;}

    public void setJeu(Partie jeu) {this.jeu = jeu;}
    public Partie getJeu() {return this.jeu;}

/// Constructeur
    public Board(Fenetre container, Partie jeu) {
        setJeu(jeu);
        
        // getJeu().addJoueur("To");
        // getJeu().addJoueur("Niavo");

        setContainer(container);
        container.add(this);
        initGameBoard();
        
        new GameLoop(this);
        getContainer().refresh();
    }

// / Fonction de classe
    public int countSurvivant() {
        int nombre = 0;
        for(int i = 0; i < getJeu().getListesJoueur().size(); i++) {
            if (!getJeu().getListesJoueur().elementAt(i).getDead()) {
              nombre++;
            }
        }
        return nombre;
    }

    public Joueur findGagnant() {
        // Trouver le seul survivant du match
        for(int i = 0; i < getJeu().getListesJoueur().size(); i++) {
            if (!getJeu().getListesJoueur().elementAt(i).getDead()) {
                return getJeu().getListesJoueur().elementAt(i);
            }
        }
        return null;
    }

    public int initTimeWaiting() {
        if (getFinPartie() == 0) {
            setFinPartie(System.currentTimeMillis());
            return 3;
        }
        else {
            return (int)((double)(4000 - (System.currentTimeMillis() - getFinPartie())) / (double)1000); 
        }
    }

    public void checkPartie(Graphics graph) {
        // Verifie si la partie est terminer
        if (countSurvivant() <= 1 && getJeu().getListesJoueur().size() > 1) {
            int seconde = initTimeWaiting();

            Joueur maitre = findGagnant();

            Color couleur1 = graph.getColor();
            Color couleur2 = graph.getColor();

            String affichage;
            if (maitre == null) affichage = "a revoir au prochain match !";
            else {
                couleur1 = maitre.getCouleur()[0];
                couleur2 = maitre.getCouleur()[1];
                affichage = maitre.getNom();
            }

            graph.setColor(couleur1);
            graph.setFont(new Font("Arial", Font.BOLD, 30));
            graph.drawString("Le gagnant est : " + affichage, 90, 180);

            graph.setColor(couleur2);
            graph.setFont(new Font("Arial", Font.PLAIN, 12));
            graph.drawString("La partie va reprendre dans " + seconde, 110, 200);

            if (seconde == 0) {
                setFinPartie(0);
                getJeu().relancement();
            }

        }
    }

    public void drawAllWall(Graphics graph) {
        for(int i = 0; i < getJeu().getListesMur().size(); i++) {
            getJeu().getListesMur().elementAt(i).selfDraw(graph);
        }
    }

    public void drawAllPlayer(Graphics graph) {
        /// Dessine chaque joueur
        Graphics2D g = (Graphics2D) graph;
        AffineTransform oldXForm = g.getTransform();
        for(int i = 0; i < getJeu().getListesJoueur().size(); i++) {
            if (!getJeu().getListesJoueur().elementAt(i).getDead()) {
                getJeu().getListesJoueur().elementAt(i).selfDraw(graph);        // auto dessine ainsi que toutes ces composant
                getJeu().getListesJoueur().elementAt(i).move();             /// met en mouvement chaque joueur
                g.setTransform(oldXForm);
                getJeu().getJoueurPrincipale().drawPointDeVie(graph);             /// dessine les points de vie
                getJeu().getJoueurPrincipale().drawScore(graph);          /// dessine les points de vie
                getJeu().getJoueurPrincipale().drawEsquiveMark(graph);          /// dessine la marque d'esquive
            }
        }
    }

    public void paintComponent(Graphics graph) {
        /// Fonction de dessin principale
        super.paintComponent(graph);
        drawAllPlayer(graph);
        drawAllWall(graph);
        checkPartie(graph);
    }

    public void initGameBoard() {
        /// Preprare l'affichage principale
        new Input(getJeu().getJoueurPrincipale(), this);
        setFocusable(true);
        requestFocus();
        setLayout(null);
        getContainer().setResizable(false);
        setBackground(new Color(242, 223, 161));

        if(getJeu().getContainer().getMode().equals("serveur")) {
            JLabel adresse = new JLabel();
            try {
                adresse.setText("Mon IP : " + InetAddress.getLocalHost().getHostAddress());
            } catch (Exception e) {
                // TODO: handle exception
            }
            adresse.setBounds(20, 5, 200, 10);
            adresse.setFont(new Font(Font.SERIF, Font.PLAIN, 12));
            adresse.setForeground(Color.BLUE);
            this.add(adresse);
        }
    }
}