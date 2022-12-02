package socket;
import java.io.*;
import java.net.*;
import datasender.DataSender;
import manager.*;
import traducteur.Traducteur;
import ecoute.Listen;
public class Client extends ConnectionMode {
/// Attributs   
    Socket socket;
    DataOutputStream output;     // pour l'envoie de donnees
    DataInputStream input;       // pour la reception de donnees
    Partie jeu;     // Le jeu
    Listen ecouteur;    // Ecoutes les reponses du serveur
    DataSender partageur;       // Partages des donnees vers tous les clients
    Distributeur distributeur;

/// Encapsulation
    public void setDistributeur(Distributeur distributeur) {this.distributeur = distributeur;}
    public Distributeur getDistributeur() {return this.distributeur;}

    public void setJeu(Partie jeu)  {this.jeu = jeu;}
    public Partie getJeu() {return this.jeu;}

    public void setInput(DataInputStream input) {this.input = input;}
    public DataInputStream getInput() {return this.input;}

    public void setOutput(DataOutputStream output) {this.output = output;}
    public DataOutputStream getOutput() {return this.output;}

    public void setSocket(Socket socket) {this.socket = socket;}
    public Socket getSocket() {return this.socket;}

    public void setEcouteur(Listen ecouteur) {this.ecouteur = ecouteur;}
    public Listen getEcouteur() {return this.ecouteur;}

    public void setPartageur(DataSender partageur) {this.partageur = partageur;}
    public DataSender getPartageur() {return this.partageur;}

/// Constructeur
    public Client(Partie jeu) {
        try {
            System.out.println("Demande d'adhesion au serveur");
            setSocket(new Socket("localhost", 6666));
            setJeu(jeu);
            System.out.println("Connection effectue !");
            setOutput(new DataOutputStream(getSocket().getOutputStream()));     // prepare le tyau d' envoie
            setInput(new DataInputStream(getSocket().getInputStream()));
            setDistributeur(new Distributeur(this));


            sendMessage("Initialisation:" + getJeu().getJoueurPrincipale().getNom() + getJeu().prepareColorCode() +",port:" + getSocket().getLocalPort() + ",X:" + getJeu().getJoueurPrincipale().getX() + ",Y:" + getJeu().getJoueurPrincipale().getY() + ",Angle:" + getJeu().getJoueurPrincipale().getAngle());

            setEcouteur(new Listen(getSocket(), this));
            
            Thread.sleep(2000);     // Attendre 2 seconde pour l'etablissement
            getDistributeur().setReady(true);
            
            // setPartageur(new DataSender(getSocket(), getJeu()));

            // testMessage();
        } catch(Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        /// Pour envoyer message au serveur 
        try {
            getOutput().writeUTF(message);
            getOutput().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
