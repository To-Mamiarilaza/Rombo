package socket;

public class NetworkManager {
/// Attributs
    Server serveur;
    Client client;

/// Encapsulation
    public void setServeur(Server serveur) {this.serveur = serveur;}
    public Server getServeur() {return this.serveur;}

    public void setClient(Client client) {this.client = client;}
    public Client getClient() {return this.client;}

/// Constructeur
    public NetworkManager(String mode) {
        if(mode.equals("serveur")) {
            setServeur(new Server());
        }
        else setClient(new Client());
    }

/// Fonctions de classe
}