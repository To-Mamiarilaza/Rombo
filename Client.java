package socket;
import java.io.*;
import java.net.*;
import ecoute.Listen;
public class Client extends ConnectionMode {
/// Attributs   
    Socket socket;
    DataOutputStream output;     // pour l'envoie de donnees
    DataInputStream input;       // pour la reception de donnees

/// Encapsulation
    public void setInput(DataInputStream input) {this.input = input;}
    public DataInputStream getInput() {return this.input;}

    public void setOutput(DataOutputStream output) {this.output = output;}
    public DataOutputStream getOutput() {return this.output;}

    public void setSocket(Socket socket) {this.socket = socket;}
    public Socket getSocket() {return this.socket;}

/// Constructeur
    public Client() {
        try {
            System.out.println("Demande d'adhesion au serveur");
            setSocket(new Socket("localhost", 6666));
            System.out.println("Connection effectue !");
            setOutput(new DataOutputStream(getSocket().getOutputStream()));     // prepare le tyau d' envoie
            setInput(new DataInputStream(getSocket().getInputStream()));
            new Listen(getSocket());
            testMessage();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

/// Fonctions de classe
    public void testMessage() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                sendMessage(reader.readLine());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sendMessage(String message) {
        /// Pour envoyer message au serveur 
        try {
            getOutput().writeUTF(message);
            getOutput().flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
