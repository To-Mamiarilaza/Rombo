package mur;
import game.GameObject;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

public class Mur extends GameObject {
/// Attributs

/// Encapsulation

/// Constructeur
    public Mur(int x, int y, int taille) {
        setX(x);
        setY(y);
        setWidth(taille);
        setHeight(taille);
    }

/// Fonction de classe

    public void selfDraw(Graphics g) {
        // dessine le mur
        g.setColor(Color.PINK);
        g.fillOval(getX(), getY(), getWidth(), getHeight());
    }
}
