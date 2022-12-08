package container;
import java.awt.*;
import listener.*;
import javax.swing.*;

public class Menu extends JPanel {
/// Attributs
    Fenetre fenetre;

/// Encapsulation
    public void setFenetre(Fenetre fenetre) {this.fenetre = fenetre;}
    public Fenetre getFenetre() {return this.fenetre;}

/// Constructeur
    public Menu(Fenetre fenetre) {
        setFenetre(fenetre);
        getFenetre().setBounds(500, 200, 500, 400);

        // Prepare le frame
        initFrame();

        // Place tous les composants 
        placeAllComponent();
        getFenetre().refresh();
    }

/// Fonction de classe
    public void placeAllComponent() {

        // Ajout du titre
        JLabel titre = new JLabel("ROMBO");
        titre.setFont(new Font(Font.SERIF, Font.BOLD, 50));
        titre.setForeground(Color.WHITE);
        titre.setBounds(160, 50, 200, 70);
        this.add(titre);

        // Ajout du bouton selection mode   
        JButton serveur = new JButton("Creer une partie");
        serveur.setBounds(100, 150, 300, 50);
        serveur.addActionListener(new MenuListener(getFenetre()));
        this.add(serveur);

        JButton client = new JButton("Joindre une partie");
        client.setBounds(100, 230, 300, 50);
        client.addActionListener(new MenuListener(getFenetre()));

        this.add(client);
    }

    public void initFrame() {
        this.setVisible(true);
        this.setLayout(null);
        this.setBackground(Color.LIGHT_GRAY);
        getFenetre().add(this);
        getFenetre().repaint();
    }
}
