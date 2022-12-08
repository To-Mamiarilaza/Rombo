package ecoute;
import java.io.*;
import java.net.*;
import socket.*;

/// Classe qui ecoute les Messages de chaque client  ou du serveur

public class Listen extends Thread {
/// Attributs
    Socket client;
    ConnectionMode mode;
    DataInputStream input;       // pour la reception de donnees

/// Encapsulation
    public void setMode(ConnectionMode mode) {this.mode = mode;}
    public ConnectionMode getMode() {return this.mode;}

    public void setClient(Socket client) {this.client = client;}
    public Socket getClient() {return this.client;}

    public void setInput(DataInputStream input) {this.input = input;}
    public DataInputStream getInput() {return this.input;}

/// Constructeur
    public Listen(Socket client, ConnectionMode mode) {
        setClient(client);
        setMode(mode);
        try {
            setInput(new DataInputStream(getClient().getInputStream()));
            this.start();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

/// Fonctions de classe
    public void findDestination(String message, Server server) throws Exception {
        String[] division = message.split(",");
        int port = Integer.valueOf(division[3].split(":")[1]);      // en fonction du place du socket 
        for(int i = 0; i < server.getOutputListes().size(); i++) {
            if(server.getClients().elementAt(i).getPort() == port) {
                server.getOutputListes().elementAt(i).writeUTF(message);
                server.getOutputListes().elementAt(i).flush();
                return;
            }
        }
    }

    public void ecouteMessage() {
        /// Ecoute les message provenant de ce client
        try {
            while (true) {
                String message = getInput().readUTF();
                if (getMode() instanceof Server) {
                    Server serv = (Server) getMode();
                    serv.sendMessage(message, getClient());      // Test self distribution       // Exact
                }
                else {
                    Client action = (Client) getMode();
                    action.getDistributeur().distribuer(message);
                }
            }
        } catch (Exception e) {
            this.stop();
            e.printStackTrace();
        }
    }

    public void run() {
        ecouteMessage();
    }
    
}
