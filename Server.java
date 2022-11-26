package socket;
import ecoute.Listen;
import java.io.*;
import java.net.*;
import java.util.Vector;

public class Server extends ConnectionMode {
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
            new ConnectionServerClient(this);       // Ecoute tous les Client qui entre en permanant
            // testMessage();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

/// Fonction de classes
    public void sendMessage(String message) {
        /// Pour envoyer message a tous les clients 
        try {
            for(int i = 0; i < getOutputListes().size(); i++) {
                getOutputListes().elementAt(i).writeUTF(message);
                getOutputListes().elementAt(i).flush();
                System.out.println("----> message envoye depuis serveur");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void testMessage() {
        /// test envoie de message au client
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                sendMessage(reader.readLine());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
