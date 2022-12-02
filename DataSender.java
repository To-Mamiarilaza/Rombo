package datasender;
import java.io.*;
import java.net.*;

import joueur.Joueur;
import manager.Partie;
import socket.*;

/// Classe qui ecoute les Messages de chaque client  ou du serveur

public class DataSender extends Thread {
/// Attributs
    Socket client;
    Partie jeu;
    DataOutputStream output;       // pour la reception de donnees

/// Encapsulation
    public void setJeu(Partie jeu) {this.jeu = jeu;}
    public Partie getJeu() {return this.jeu;}

    public void setClient(Socket client) {this.client = client;}
    public Socket getClient() {return this.client;}

    public void setOutput(DataOutputStream output) {this.output = output;}
    public DataOutputStream getOutput() {return this.output;}

/// Constructeur
    public DataSender(Socket client, Partie partie) {
        setClient(client);
        setJeu(partie);
        try {
            setOutput(new DataOutputStream(getClient().getOutputStream()));
            this.start();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

/// Fonctions de classe
    public void sendData() {
        /// Envoie les coordonnes du joueur principale vers le serveur
        try {
            while(true) {
                getOutput().writeUTF(getJeu().getJoueurPrincipale().getEncodage());
                getOutput().flush();
                Thread.sleep(5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public void run() {
        sendData();
    }
    
}
