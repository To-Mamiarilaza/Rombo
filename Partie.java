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
    String hoteIp;
    Client client;      // Interface pour l'interconnection
    Server serveur;
    boolean valider;

/// Encapsulation
    public void setContainer(Fenetre container) {this.container = container;}
    public Fenetre getContainer() {return this.container;}

    public void setListesMur(Vector<Mur> listesMur) {this.listesMur = listesMur;}
    public Vector<Mur> getListesMur() {return this.listesMur;}

    public void setServeur(Server serveur) {this.serveur = serveur;}
    public Server getServeur() {return this.serveur;}

    public void setClient(Client client) {this.client = client;}
    public Client getClient() {return this.client;}

    public void setBoard(Board board) {this.board = board;}
    public Board getBoard() {return this.board;}

    public void setListesJoueur(Vector<Joueur> listesJoueur) {this.listesJoueur = listesJoueur;}
    public Vector<Joueur> getListesJoueur() {return this.listesJoueur;}

    public void setJoueurPrincipale(Joueur principale) {this.principale = principale;}
    public Joueur getJoueurPrincipale() {return this.principale;}

    public void setHoteIp(String hoteIp) {this.hoteIp = hoteIp;}
    public String getHoteIp() {return this.hoteIp;}

    public void setValider(boolean valider) {this.valider = valider;}
    public boolean getValider() {return this.valider;}

/// Constructeur
    public Partie(Vector<Joueur> listesJoueur) {
        setListesJoueur(listesJoueur);

    }

    public Partie(String mode, String joueurName, Fenetre container, String hoteIp) {
        try {
            setContainer(container);
            setListesMur(new Vector<Mur>());
            setListesJoueur(new Vector<Joueur>());
            setHoteIp(hoteIp);
            placeAllWall();
            if(mode.equals("serveur")) setServeur(new Server(this));
            setJoueurPrincipale(new Joueur(joueurName, this));        // Ajout du joueur principale
            getListesJoueur().add(getJoueurPrincipale());
    
            joinServer();
    
            if (getContainer().getMode().equals("serveur")) setValider(true);   
            if (getValider()) {     // Si la connexion au serveur va bien
                System.out.println("Le jeu commence");
                setBoard(new Board(container, this));
            }
            else System.out.println("valider false");
        }
        catch(Exception e) {
            getContainer().goToInput(e.getMessage());
        }
    }

/// Fonction de classe
    public void quitter() {
        try {
            System.out.println("Mode " + getContainer().getMode());
            if (getContainer().getMode().equals("serveur")) {
                System.out.println("J'envoie le message de quit");
                getClient().sendMessage("ServerQuit");
                System.out.println("Fermeture serveur");
                Thread.sleep(1000);
                getServeur().fermerServeur();
            }
            getClient().sendMessage("Quitter:" + getJoueurPrincipale().getNom());        
            getClient().fermer();
            getContainer().goToAcceuil();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void placeAllWall() {
        // Place tous les mur
        getListesMur().add(new Mur(200, 150, 30));
        getListesMur().add(new Mur(423, 300, 42));
        getListesMur().add(new Mur(76, 50, 40));
        getListesMur().add(new Mur(80, 250, 30));
        getListesMur().add(new Mur(354, 200, 37));
        getListesMur().add(new Mur(270, 80, 28));
        getListesMur().add(new Mur(240, 300, 33));
        getListesMur().add(new Mur(400, 30, 36));
    }

    public Joueur findJoueur(String nom) throws Exception {
        for(int i = 0; i < getListesJoueur().size(); i++) {
            if (getListesJoueur().elementAt(i).getNom().equals(nom)) {
                return getListesJoueur().elementAt(i);
            }
        }
        return null;
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

    public void joinServer() throws Exception {
        setClient(new Client(this, hoteIp));
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