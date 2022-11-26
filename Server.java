package socket;
import java.io.*;
import java.net.*;
public class Server extends Thread {
/// Attributs
    ServerSocket serveur;

/// Encapsulation
    public void setServeur(ServerSocket serveur) {this.serveur = serveur;}
    public ServerSocket getServeur() {return this.serveur;}

/// Constructeur
    public Server() {
        try {
            setServeur(new ServerSocket(6666));
            System.out.println("Ecoute des clients entrant");
            this.start();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public void listenClient() throws Exception {
        Socket s = getServeur().accept(); 
        System.out.println("---> Un client entre !");
        listenClient();
    }

    public void run() {
        try {
            listenClient();
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
