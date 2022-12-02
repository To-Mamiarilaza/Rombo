package socket;
import ecoute.Listen;
import java.io.*;
import java.net.*;
import java.util.Vector;

public class ConnectionServerClient extends Thread {
/// Attributs
    Server serveur;

/// Encapsulation
    public void setServeur(Server serveur) {this.serveur = serveur;}
    public Server getServeur() {return this.serveur;}

/// Constructeur
    public ConnectionServerClient(Server serveur) {
        setServeur(serveur);
        this.start();
    }

/// Fonctions
    public void listenClient() throws Exception {
        /// Ecoute l'entree des clients
        Socket newSocket = getServeur().getServeur().accept(); 
        getServeur().getClients().add(newSocket);
        getServeur().getOutputListes().add(new DataOutputStream(newSocket.getOutputStream()));
        new Listen(newSocket, getServeur());
        listenClient();
    }

    public void run() {
        try {
            listenClient();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}