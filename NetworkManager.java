package socket;

public class NetworkManager {
/// Attributs
    ConnectionMode mode;

/// Encapsulation
    public void setConnectionMode(ConnectionMode mode) {this.mode = mode;}
    public ConnectionMode getConnectionMode() {return this.mode;}

/// Constructeur
    public NetworkManager(String mode) {
        if(mode.equals("serveur")) {
            setConnectionMode(new Server());
        }
        else setConnectionMode(new Client());
    }

/// Fonctions de classe
}