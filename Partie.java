package manager;
import java.util.Vector;
import joueur.Joueur;
import container.Board;
import java.awt.Color;
import listener.*;
import socket.*;

public class Partie {
/// Attributs
    Vector<Joueur> listesJoueur;
    Joueur principale;
    Board board;
    Client client;      // Interface pour l'interconnection

/// Encapsulation
    public void setClient(Client client) {this.client = client;}
    public Client getClient() {return this.client;}

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

    public Partie(String mode, Board board) {
        setBoard(board);
        setListesJoueur(new Vector<Joueur>());
        if(mode.equals("serveur")) new Server();
        setJoueurPrincipale(new Joueur("Mikalo", this));        // Ajout du joueur principale
        getListesJoueur().add(getJoueurPrincipale());
        joinServer();
    }

/// Fonction de classe
    public Joueur findJoueur(String nom) throws Exception {
        for(int i = 0; i < getListesJoueur().size(); i++) {
            if (getListesJoueur().elementAt(i).getNom().equals(nom)) {
                return getListesJoueur().elementAt(i);
            }
        }
        throw new Exception("Ce joueur : " + nom + " n'existe pas !");
    }

    public String prepareColorCode() {
        String resultat = "";
        Color[] couleurs = getJoueurPrincipale().getCouleur();
        resultat += ",color1:" + couleurs[0].getRed() + "-" + couleurs[0].getGreen() + "-" + couleurs[0].getBlue();
        resultat += ",color2:" + couleurs[1].getRed() + "-" + couleurs[1].getGreen() + "-" + couleurs[1].getBlue();
        return resultat;
    }

    public void joinServer() {
        setClient(new Client(this));
    }

    public void addJoueur(String nom) {
        /// Permet d'ajouter un joueur au jeu
        System.out.println("Un nouveau joueur entre : " + nom);
        Joueur nouveau = new Joueur(nom, this);
        getListesJoueur().add(nouveau);
    }

    public void stopPartie() {
        System.out.println("Le nom de joueur doit etre unique !");
        System.exit(1);
    }
}