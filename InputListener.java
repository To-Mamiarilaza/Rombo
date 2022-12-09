package listener;
import java.awt.event.*;

import javax.swing.JButton;

import container.Fenetre;
import container.InputFrame;

public class InputListener implements ActionListener {
/// Attributs
    Fenetre fenetre;
    InputFrame inputFrame;

/// Encapsulation
    public void setFenetre(Fenetre fenetre) {this.fenetre = fenetre;}
    public Fenetre getFenetre() {return this.fenetre;}

    public void setInputFrame(InputFrame inputFrame) {this.inputFrame = inputFrame;}
    public InputFrame getInputFrame() {return this.inputFrame;}

/// Constructeur
    public InputListener(Fenetre fenetre, InputFrame inputFrame) {
        setFenetre(fenetre);
        setInputFrame(inputFrame);
    }

/// Fonctions de classe
    public void actionPerformed(ActionEvent e) {
        JButton bouton = (JButton) e.getSource();
        if (bouton.getText().equals("Retour")) {
            getFenetre().goToAcceuil();
        }
        else if (getInputFrame().getLabelNom().getText().equals("") || getInputFrame().getLabelNom().getText().contains(" ")) {
            getFenetre().goToInput("Remplir les champs svp ");
        }
        else {
            getFenetre().setNomJoueur(getInputFrame().getLabelNom().getText());
            if (getFenetre().getMode().equals("serveur")) {
                getFenetre().setIp("localhost");
            }
            else {
                getFenetre().setIp(getInputFrame().getLabelIp().getText());
            }
            getFenetre().prepareBoard();
        }
    }
}
