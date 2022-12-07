package container;
import manager.Partie;
import java.awt.*;
import javax.swing.*;

public class Fenetre extends JFrame {
/// Attributs

/// Encapsulation

/// Constructeur
    public Fenetre() {
        initUI();
    }

/// Fonctions de classe
    public void initUI() {
        /// Initialiser l'interface graphique
        setVisible(true);
        setBounds(100, 50, 800, 800);       // width : 1400 height : 800
        new Partie("serveur", "To", this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
