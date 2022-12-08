package listener;
import java.awt.event.*;

import javax.swing.JButton;

import container.Fenetre;

public class MenuListener implements ActionListener {
/// Attributs
    Fenetre fenetre;

/// Encapsulation
    public void setFenetre(Fenetre fenetre) {this.fenetre = fenetre;}
    public Fenetre getFenetre() {return this.fenetre;}

/// Constructeur
    public MenuListener(Fenetre fenetre) {
        setFenetre(fenetre);
    }

/// Fonctions de classe
    public void actionPerformed(ActionEvent e) {
        if (((JButton)e.getSource()).getText().equals("Creer une partie")) {
            getFenetre().setMode("serveur");
        }
        else {
            getFenetre().setMode("client");
        }
        getFenetre().goToInput(null);
    }
}
