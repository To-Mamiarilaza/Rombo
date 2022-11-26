package socket;
import ecoute.Listen;
import java.io.*;
import java.net.*;
import java.util.Vector;

public class Server extends Thread {
/// Attributs
    ServerSocket serveur;
    Vector<Socket> clients;
    Vector<DataOutputStream> outputListes;     // pour l'envoie de donnees

/// Encapsulation
    public void setOutputListes(Vector<DataOutputStream> outputListes) {this.outputListes = outputListes;}
    public Vector<DataOutputStream> getOutputListes() {return this.outputListes;}

    public void setClients(Vector<Socket> clients) {this.clients = clients;}
    public Vector<Socket> getClients() {return this.clients;}

    public void setServeur(ServerSocket serveur) {this.serveur = serveur;}
    public ServerSocket getServeur() {return this.serveur;}

/// Constructeur
    public Server() {
        try {
            setServeur(new ServerSocket(6666));
            setClients(new Vector<Socket>());
            setOutputListes(new Vector<DataOutputStream>());
            System.out.println("Ecoute des clients entrant");
            this.start();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public void listenClientMessage() {

    }

    public void listenClient() throws Exception {
        /// Ecoute l'entree des clients
        Socket newSocket = getServeur().accept(); 
        System.out.println("---> Un client entre !");
        getClients().add(newSocket);
        getOutputListes().add(new DataOutputStream(newSocket.getOutputStream()));
        new Listen(newSocket);
        if (getClients().size() < 2) {
            listenClient();
        }
    }

    public void run() {
        try {
            listenClient();
            System.out.println("Clients complet : vous pouvez envoyer des messages maintenant .");
            listenClientMessage();
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
