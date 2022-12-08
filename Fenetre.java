package container;
import manager.Partie;
import java.awt.*;
import javax.swing.*;

public class Fenetre extends JFrame {
/// Attributs
    String mode;
    String nomJoueur;
    String ip;
    Menu menu;
    InputFrame inputFrame;
    Partie partie;

/// Encapsulation
    public void setPartie(Partie partie) {this.partie = partie;} 
    public Partie getPartie() {return this.partie;}

    public void setMode(String mode) {this.mode = mode;}
    public String getMode() {return this.mode;}

    public void setNomJoueur(String nomJoueur) {this.nomJoueur = nomJoueur;}
    public String getNomJoueur() {return this.nomJoueur;}

    public void setIp(String ip) {this.ip = ip;}
    public String getIp() {return this.ip;}

    public void setMenu(Menu menu) {this.menu = menu;}
    public Menu getMenu() {return this.menu;}

    public void setInputFrame(InputFrame inputFrame) {this.inputFrame = inputFrame;}
    public InputFrame getInputFrame() {return this.inputFrame;}

/// Constructeur
    public Fenetre() {
        initUI();
    }

/// Fonctions de classe
    public void initUI() {
        /// Initialiser l'interface graphique
        setVisible(true);
        setFocusable(false);
        // new Partie("serveur", "To", this);
        goToAcceuil();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void prepareBoard() {
        // Lance le jeu
        if(getInputFrame() != null) this.remove(getInputFrame());
        transferFocus();
        setPartie(new Partie(getMode(), getNomJoueur(), this, getIp()));
        System.out.println("Adresse du Partie : " + getPartie());
    }

    public void goToAcceuil() {
        // Menu d'acceuil
        if (getPartie() != null && getPartie().getBoard() != null) this.remove(getPartie().getBoard());
        if(getInputFrame() != null) this.remove(getInputFrame());
        Menu menu = new Menu(this);
        setMenu(menu);
        refresh();
    }

    public void goToInput(String erreur) {
        // Entrer de detail
        this.remove(getMenu());
        if (erreur != null) {
            setNomJoueur(null);
            setIp(null);
            setPartie(null);
        }
        InputFrame inputFrame = new InputFrame(this, erreur);
        setInputFrame(inputFrame);
        refresh();
    }

    public void refresh() {
        setSize(501, 401);
        setSize(500, 400);
    }
}
