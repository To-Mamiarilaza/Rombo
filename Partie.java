package manager;
import java.util.Vector;
import joueur.Joueur;

public class Partie {
/// Attributs
    Vector<Joueur> listesJoueur;

/// Encapsulation
    public void setListesJoueur(Vector<Joueur> listesJoueur) {this.listesJoueur = listesJoueur;}
    public Vector<Joueur> getListesJoueur() {return this.listesJoueur;}

/// Constructeur
    public Partie(Vector<Joueur> listesJoueur) {
        setListesJoueur(listesJoueur);
        
    }

    public Partie() {
        setListesJoueur(new Vector<Joueur>());
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