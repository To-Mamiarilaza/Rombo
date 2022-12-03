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
        setBounds(100, 100, 700, 700);
        add(new Board(this));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}