package manager;
import java.util.Vector;
import joueur.Joueur;
import container.Board;
import container.Fenetre;
import mur.Mur;
import java.awt.Color;
import listener.*;
import socket.*;

public class Partie {
/// Attributs
    Vector<Joueur> listesJoueur;
    Vector<Mur> listesMur;
    Joueur principale;
    Fenetre container;
    Board board;
    Client client;      // Interface pour l'interconnection

/// Encapsulation
    public void setContainer(Fenetre container) {this.container = container;}
    public Fenetre getContainer() {return this.container;}

    public void setListesMur(Vector<Mur> listesMur) {this.listesMur = listesMur;}
    public Vector<Mur> getListesMur() {return this.listesMur;}

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

    public Partie(String mode, String joueurName, Fenetre container) {
        setListesMur(new Vector<Mur>());
        setListesJoueur(new Vector<Joueur>());
        placeAllWall();
        if(mode.equals("serveur")) new Server();
        setJoueurPrincipale(new Joueur(joueurName, this));        // Ajout du joueur principale
        getListesJoueur().add(getJoueurPrincipale());
        joinServer();

        setBoard(new Board(container, this));

        // Conception du jeu
        // getListesJoueur().add(new Joueur("To", this));
    }

/// Fonction de classe
    public void placeAllWall() {
        // Place tous les mur
        getListesMur().add(new Mur(200, 150, 30));
        getListesMur().add(new Mur(423, 500, 70));
        getListesMur().add(new Mur(76, 50, 40));
        getListesMur().add(new Mur(80, 250, 30));
        getListesMur().add(new Mur(500, 245, 50));
        getListesMur().add(new Mur(1000, 20, 50));
        getListesMur().add(new Mur(1000, 750, 70));
        getListesMur().add(new Mur(850, 400, 60));
        getListesMur().add(new Mur(200, 580, 50));
        getListesMur().add(new Mur(800, 700, 20));
        getListesMur().add(new Mur(1300, 400, 70));
        getListesMur().add(new Mur(1200, 650, 45));
        getListesMur().add(new Mur(1200, 650, 45));
    }

    public Joueur findJoueur(String nom) throws Exception {
        for(int i = 0; i < getListesJoueur().size(); i++) {
            if (getListesJoueur().elementAt(i).getNom().equals(nom)) {
                return getListesJoueur().elementAt(i);
            }
        }
        throw new Exception("Ce joueur : " + nom + " n'existe pas !");
    }

    public void removeJoueur(Joueur joueur) {
        for(int i = 0; i < getListesJoueur().size(); i++) {
            if (getListesJoueur().elementAt(i).getNom().equals(joueur.getNom())) {
                getListesJoueur().removeElementAt(i);
                return;
            }
        }
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