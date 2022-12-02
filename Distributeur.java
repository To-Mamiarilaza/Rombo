package manager;

import java.util.Vector;
import socket.*;
import java.awt.Color;
import joueur.Joueur;
import manager.Partie;
import manipulateur.Manipulateur;

public class Distributeur {
/// Attributs
    Client client;
    Partie jeu;
    boolean ready;

/// Encapsulation
    public void setReady(boolean ready) {this.ready = ready;}
    public boolean getReady() {return this.ready;}

    public void setClient(Client client) {this.client = client;}
    public Client getClient() {return this.client;}

    public void setJeu(Partie jeu) {this.jeu = jeu;}
    public Partie getJeu() {return this.jeu;}

/// Constructeur
    public Distributeur(Client client) {
        setReady(false);
        setClient(client);
        setJeu(client.getJeu());
    }

/// Fonctions de classe
    public int[] getColorValue(String code) {
        // RGB : 125-35-45 decode se string en int[]
        int[] resultat = new int[3];
        String[] division = code.split("-");
        resultat[0] = Integer.valueOf(division[0]);
        resultat[1] = Integer.valueOf(division[1]);
        resultat[2] = Integer.valueOf(division[2]);
        return resultat;
    }

    public Color[] decodageCouleur(String[] division) {
        // decoder le couleur en color
        Color[] couleurs = new Color[2];
        int[] colorValue = getColorValue(division[1].split(":")[1]);
        couleurs[0] = new Color(colorValue[0], colorValue[1], colorValue[2]);
        colorValue = getColorValue(division[2].split(":")[1]);
        couleurs[1] = new Color(colorValue[0], colorValue[1], colorValue[2]);
        return couleurs;
    }

    public boolean checkDoublon(Joueur nouveau) {
        //Empeche les joueurs de meme nom
        for(int i = 0; i < getJeu().getListesJoueur().size(); i++) {
            if (getJeu().getListesJoueur().elementAt(i).getNom().equals(nouveau.getNom())) {
                return true;
            }
        }
        return false;
    }

    public void initialisation(String code) throws Exception {
        // Initialisation:Koto,color1:214-231-75,color2:117-35-28,port:66522,X:300,Y:400
        String[] division = code.split(",");
        Joueur nouveau = new Joueur(division[0].split(":")[1], getJeu());
        
        if(division[1].equals("Erreur")) {   // Verifie si c'est une reponse d'erreur
            getJeu().stopPartie();
        }

        nouveau.setX(Integer.valueOf(division[4].split(":")[1]));
        nouveau.setY(Integer.valueOf(division[5].split(":")[1]));
        nouveau.setAngle(Integer.valueOf(division[6].split(":")[1]));
        nouveau.setCouleur(decodageCouleur(division));

        if(!checkDoublon(nouveau)) {        // si il n'y a pas de doublons on procede normallement
            getJeu().getListesJoueur().add(nouveau);
            if(!code.endsWith("Recu")) {
                code = "Initialisation:" + getJeu().getJoueurPrincipale().getNom() + getJeu().prepareColorCode() + ",port:" + division[3].split(":")[1] + ",X:" + getJeu().getJoueurPrincipale().getX() + ",Y:" + getJeu().getJoueurPrincipale().getY() + ",Angle:" + getJeu().getJoueurPrincipale().getAngle() + ",Recu";
                getClient().sendMessage(code);
            }
        }
        else {
            code = "Initialisation:" + getJeu().getJoueurPrincipale().getNom() + ",Erreur,Erreur" + ",port:" + division[3].split(":")[1] + ",Recu";
            getClient().sendMessage(code);
        }
    }

    public void actionJoueur(String code) throws Exception {
        // Traduis le code en action
        // Joueur:To,X:200,Y:350,Angle:280,Vitesse:18,tir:false
        // MouvementX:To,X:200
        // MouvementY:To,Y:200
        // Angle:To,Angle:45
        // Tir:To,tir:true

        String[] division = code.split(","); 
        Joueur marionette = getJeu().findJoueur(division[0].split(":")[1]);

        switch (division[0].split(":")[0]) {
            case "MouvementX":
                marionette.setX(Integer.valueOf(division[1].split(":")[1])); 
                break;
        
            case "MouvementY":
                marionette.setY(Integer.valueOf(division[1].split(":")[1])); 
                break;

            case "Angle":
                marionette.setAngle(Integer.valueOf(division[1].split(":")[1])); 
                break;

            case "Tir":
                marionette.setTir(Boolean.valueOf(division[1].split(":")[1])); 
                break;

            default:
                break;
        }
        /// A activer pour reinitialiser
        // getJoueur().setX(Integer.valueOf(division[1].split(":")[1]));
        // getJoueur().setY(Integer.valueOf(division[2].split(":")[1]));
        // getJoueur().setAngle(Integer.valueOf(division[3].split(":")[1]));
        // getJoueur().setVitesse(Integer.valueOf(division[4].split(":")[1]));
        // getJoueur().setTir(Boolean.valueOf(division[5].split(":")[1]));
    }

    public void distribuer(String code) throws Exception {
        /// Distribuer le code vers le manipulateur destinee
        if(code.startsWith("Initialisation")) initialisation(code);
        else {
            if (getReady()) {       // Attends que l'initialisation soit faites avant d'executer des code
                actionJoueur(code);
            }
        }
    }
}
