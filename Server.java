package socket;
import ecoute.Listen;
import java.io.*;
import java.net.*;
import java.util.Vector;
import manager.Partie;

public class Server extends ConnectionMode {
/// Attributs
    ServerSocket serveur;
    Vector<Socket> clients;
    Vector<DataOutputStream> outputListes;     // pour l'envoie de donnees
    Partie partie;
    ConnectionServerClient clientLogin;

/// Encapsulation
    public void setOutputListes(Vector<DataOutputStream> outputListes) {this.outputListes = outputListes;}
    public Vector<DataOutputStream> getOutputListes() {return this.outputListes;}

    public void setClients(Vector<Socket> clients) {this.clients = clients;}
    public Vector<Socket> getClients() {return this.clients;}

    public void setServeur(ServerSocket serveur) {this.serveur = serveur;}
    public ServerSocket getServeur() {return this.serveur;}

    public void setPartie(Partie partie) {this.partie = partie;}
    public Partie getPartie() {return this.partie;}

    public void setClientLogin(ConnectionServerClient clientLogin) {this.clientLogin = clientLogin;}
    public ConnectionServerClient getClientLogin() {return this.clientLogin;}

/// Constructeur
    public Server(Partie partie) throws Exception {
        try {
            setPartie(partie);
            setServeur(new ServerSocket(6666));
            setClients(new Vector<Socket>());
            setOutputListes(new Vector<DataOutputStream>());
            setClientLogin(new ConnectionServerClient(this));       // Ecoute tous les Client qui entre en permanant
            // testMessage();
        } catch(Exception e) {
            throw new Exception("Quelqu'un creer deja le partie !");
            // getPartie().getContainer().goToInput("Quelqu'un creer deja le partie !");
            
        }
    }

/// Fonction de classes
    public void fermerServeur() throws Exception {
        getClientLogin().fermerEcoute();
        getServeur().close();
    }

    public void findDestination(String message) throws Exception {
        String[] division = message.split(",");
        int port = Integer.valueOf(division[3].split(":")[1]);      // en fonction du place du socket 
        for(int i = 0; i < getOutputListes().size(); i++) {
            if(getClients().elementAt(i).getPort() == port) {
                getOutputListes().elementAt(i).writeUTF(message);
                getOutputListes().elementAt(i).flush();
                return;
            }
        }
    }

    public void sendMessage(String message, Socket envoyeur) {
        /// Pour envoyer message a tous les clients 

        int indiceSocket = 0;
        try {
            if(message.startsWith("Initialisation:") && message.endsWith("Recu")) {
                findDestination(message);
            }
            else {
                for(int i = 0; i < getOutputListes().size(); i++) {
                    indiceSocket = i;   // l'indice du socket a effacer si il y a erreur
                    if(!getClients().elementAt(i).equals(envoyeur)) {
                        getOutputListes().elementAt(i).writeUTF(message);
                        getOutputListes().elementAt(i).flush();
                    }
                }
            }
        } catch (Exception e) {
            getOutputListes().removeElementAt(indiceSocket);    // On jette
            getClients().removeElementAt(indiceSocket);
            // e.printStackTrace();
        }
    }

    public void testMessage() {
        /// test envoie de message au client
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                sendMessage(reader.readLine(), null);
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    public void fermerSocket(Socket socket) {
        try {
            socket.getOutputStream().close();
            socket.getInputStream().close();
            socket.close();
        } catch (Exception e) {
            // TODO: handle exception
            // e.printStackTrace();
        }
    }

}
