package game;
public class GameObject {
/// Attributs
    int height; // lonqueur
    int width;  // largeur
    int x;      // position en x
    int y;      // position en y
    int angle;      // valeur de l'angle de rotation
    // int size;   // taille

/// Encapsulation
    public void setAngle(int angle) {this.angle = angle;}
    public int getAngle() {return this.angle;}

    public void setHeight(int height) {this.height = height;}
    public int getHeight() {return this.height;}

    public void setWidth(int width) {this.width = width;}
    public int getWidth() {return this.width;}

    public void setX(int x) {this.x = x;}
    public int getX() {return this.x;}

    public void setY(int y) {this.y = y;}
    public int getY() {return this.y;}  

    // public void setSize(int size) {this.size = size;}
    // public int getSize() {return this.size;}

/// Constructeur
    public GameObject() {}

/// Fonctions de classe
}