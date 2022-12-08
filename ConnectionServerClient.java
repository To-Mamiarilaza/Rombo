package socket;
import ecoute.Listen;
import java.io.*;
import java.net.*;
import java.util.Vector;

public class ConnectionServerClient extends Thread {
/// Attributs
    Server serveur;
    Vector<Listen> listesEcouteurs;

/// Encapsulation
    public void setListesEcouteurs(Vector<Listen> listesEcouteurs) {this.listesEcouteurs = listesEcouteurs;}
    public Vector<Listen> getListesEcouteurs() {return this.listesEcouteurs;}

    public void setServeur(Server serveur) {this.serveur = serveur;}
    public Server getServeur() {return this.serveur;}

/// Constructeur
    public ConnectionServerClient(Server serveur) {
        setServeur(serveur);
        setListesEcouteurs(new Vector<Listen>());
        this.start();
    }

/// Fonctions
    public void fermerEcoute() {
        for(int i = 0; i < getListesEcouteurs().size(); i++) {
            getListesEcouteurs().elementAt(i).stop();
        }
    }

    public void listenClient() throws Exception {
        /// Ecoute l'entree des clients
        Socket newSocket = getServeur().getServeur().accept(); 
        getServeur().getClients().add(newSocket);
        getServeur().getOutputListes().add(new DataOutputStream(newSocket.getOutputStream()));
        getListesEcouteurs().add(new Listen(newSocket, getServeur()));
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