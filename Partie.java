package manager;
import java.util.Vector;
import joueur.Joueur;
import container.Board;
import listener.*;
import socket.*;

public class Partie {
/// Attributs
    Vector<Joueur> listesJoueur;
    Joueur principale;
    Board board;

/// Encapsulation
    public void setBoard(Board board) {this.board = board;}
    public Board getBoard() {return this.board;}

    public void setListesJoueur(Vector<Joueur> listesJoueur) {this.listesJoueur = listesJoueur;}
    public Vector<Joueur> getListesJoueur() {return this.listesJoueur;}

    public void setJoueurPrincipale(Joueur principale) {this.principale = principale;}
    public Joueur getJoueurPrincipale() {return this.principale;}

/// Constructeur
    public Partie(Vector<Joueur> listesJoueur) {
        setListesJoueur(listesJoueur);

    }

    public Partie(String mode) {
        setListesJoueur(new Vector<Joueur>());
        if(mode.equals("serveur")) new NetworkManager(mode);
        setJoueurPrincipale(new Joueur("Koto"));        // Ajout du joueur principale
        getListesJoueur().add(getJoueurPrincipale());
    }

/// Fonction de classe
    public void addJoueur(String nom) {
        /// Permet d'ajouter un joueur au jeu
        System.out.println("Ajout au jeux : ");
        Joueur nouveau = new Joueur(nom);
        getListesJoueur().add(nouveau);
        nouveau.detailJoueur();
    }
}