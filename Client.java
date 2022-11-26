package socket;
import java.io.*;
import java.net.*;
public class Client {
/// Attributs   
    Socket socket;

/// Encapsulation
    public void setSocket(Socket socket) {this.socket = socket;}
    public Socket getSocket() {return this.socket;}

/// Constructeur
    public Client() {
        try {
            System.out.println("Demande d'adhesion au serveur");
            setSocket(new Socket("localhost", 6666));
            System.out.println("Connection effectue !");
        } catch(Exception e) {
            System.out.println(e);
        }
    }

/// Fonctions de classe
}
