package listener;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import joueur.Joueur;
import manager.Partie;
import socket.Client;
import container.Board;

public class Input implements MouseListener, KeyListener, MouseMotionListener {
/// Attributs
    Joueur joueur;
    Client client;

/// Encapsulation
    public void setClient(Client client) {this.client = client;}
    public Client getClient() {return this.client;}

    public void setJoueur(Joueur joueur) {this.joueur = joueur;}
    public Joueur getJoueur() {return this.joueur;}

/// Constructeur
    public Input(Joueur joueur, Board container) {
        setJoueur(joueur);
        setClient(container.getJeu().getClient());
        container.addMouseListener(this);
        container.addKeyListener(this);
        container.addMouseMotionListener(this);
    }

/// Fonctions de classe

    /// Fonctions pour le tir
    public void mouseClicked(MouseEvent e) {
        getJoueur().setTir(true);
        getClient().sendMessage("Tir:" + getJoueur().getNom() + ",tir:true");
    }
    public void mouseEntered(MouseEvent e) {

    }
    public void mouseExited(MouseEvent e) {

    }
    public void mousePressed(MouseEvent e) {

    }
    public void mouseReleased(MouseEvent e) {

    }

    /// Fonctions de deplacements
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            getJoueur().setUp(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            getJoueur().setDown(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            getJoueur().setRight(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            getJoueur().setLeft(true);
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            getJoueur().setUp(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            getJoueur().setDown(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            getJoueur().setRight(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            getJoueur().setLeft(false);
        }
    }

    public void keyTyped(KeyEvent e) {

    }

    /// input pour les viseur
    public double formatAngle(int x0, int y0, int x, int y, double angle) {     //Ajuste la valeur de l'angle
        if (y > y0) angle = -1 * angle; // l'origine est en haut a gauche   
        if (x < x0) angle = 180 - angle;
        return angle; 
    } 

    public int findAngle(int x, int y) {
        int x0 = getJoueur().getX() + (getJoueur().getWidth() / 2);
        int y0 = getJoueur().getY() + (getJoueur().getHeight() / 2);

        double hyp = Math.sqrt(Math.pow((x - x0), 2) + Math.pow((y - y0), 2));
        double adj = Math.abs(x - x0);
        double angle = Math.toDegrees(Math.acos(adj/hyp));
        angle = -1 * formatAngle(x0, y0, x, y, angle);      // -1 pour l'inversement du resultat
        return (int) angle;
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        getJoueur().setAngle(findAngle(e.getX(), e.getY()));
        getJoueur().getJeu().getClient().sendMessage("Angle:" + getJoueur().getNom() + ",angle:" + getJoueur().getAngle());
    }
}
