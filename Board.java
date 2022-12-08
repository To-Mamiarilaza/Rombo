package container;
import manager.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.net.InetAddress;

import weapon.Fleche;
import javax.swing.*;
import listener.*;
import socket.Server;

public class Board extends JPanel {
/// Attributs
    Fenetre container;
    Partie jeu;

/// Encapsulation
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
            getJeu().getListesJoueur().elementAt(i).selfDraw(graph);        // auto dessine ainsi que toutes ces composant
            getJeu().getListesJoueur().elementAt(i).move();             /// met en mouvement chaque joueur
            g.setTransform(oldXForm);
            getJeu().getJoueurPrincipale().drawPointDeVie(graph);             /// dessine les points de vie
            getJeu().getJoueurPrincipale().drawScore(graph);          /// dessine les points de vie
            getJeu().getJoueurPrincipale().drawEsquiveMark(graph);          /// dessine la marque d'esquive
        }
        for(int i = 0; i < getJeu().getListesJoueur().size(); i++) {
            if (getJeu().getListesJoueur().elementAt(i).getDead()) getJeu().removeJoueur(getJeu().getListesJoueur().elementAt(i));
        }
    }

    public void paintComponent(Graphics graph) {
        /// Fonction de dessin principale
        super.paintComponent(graph);
        drawAllPlayer(graph);
        drawAllWall(graph);
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