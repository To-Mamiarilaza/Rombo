package ecoute;
import java.io.*;
import java.net.*;

/// Classe qui ecoute les Messages de chaque client  ou du serveur

public class Listen extends Thread {
/// Attributs
    Socket client;
    DataInputStream input;       // pour la reception de donnees

/// Encapsulation
    public void setClient(Socket client) {this.client = client;}
    public Socket getClient() {return this.client;}

    public void setInput(DataInputStream input) {this.input = input;}
    public DataInputStream getInput() {return this.input;}

/// Constructeur
    public Listen(Socket client) {
        setClient(client);
        try {
            setInput(new DataInputStream(getClient().getInputStream()));
            this.start();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
    }

/// Fonctions de classe
    public void ecouteMessage() {
        /// Ecoute les message provenant de ce client
        try {
            String message = getInput().readUTF();
            System.out.println("MESSAGE (" + getClient().toString() + ") : " + message);
            ecouteMessage();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
    }

    public void run() {
        System.out.println("Pret a ecouter");
        ecouteMessage();
    }
    
}
