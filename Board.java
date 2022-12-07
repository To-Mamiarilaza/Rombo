package container;
import manager.Partie;
import java.awt.*;
import java.awt.geom.AffineTransform;
import weapon.Fleche;
import javax.swing.*;
import listener.*;

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
        try {
            Thread.sleep(2);
        } catch (Exception e) {
            // TODO: handle exception
        }
        repaint();
    }

    public void initGameBoard() {
        /// Preprare l'affichage principale
        new Input(getJeu().getJoueurPrincipale(), this);
        setFocusable(true);
        requestFocus();
        setLayout(null);
        setBackground(new Color(242, 223, 161));
    }
}