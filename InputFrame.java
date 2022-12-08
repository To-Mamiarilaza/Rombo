package container;
import java.awt.*;
import listener.*;
import javax.swing.*;
import javax.swing.text.AttributeSet.ColorAttribute;

import container.Fenetre;

public class InputFrame extends JPanel {
/// Attributs
    Fenetre fenetre;
    JTextField labelNom;
    JTextField labelIp;
    String erreur;

/// Encapsulation
    public void setFenetre(Fenetre fenetre) {this.fenetre = fenetre;}
    public Fenetre getFenetre() {return this.fenetre;}

    public void setLabelNom(JTextField labelNom) {this.labelNom = labelNom;}
    public JTextField getLabelNom() {return this.labelNom;}

    public void setLabelIp(JTextField labelIp) {this.labelIp = labelIp;}
    public JTextField getLabelIp() {return this.labelIp;}

    public void setErreur(String erreur) {this.erreur = erreur;}
    public String getErreur() {return this.erreur;}

/// Constructeur
    public InputFrame(Fenetre fenetre, String erreur) {
        setFenetre(fenetre);
        setErreur(erreur);
        getFenetre().setBounds(500, 200, 500, 400);

        // Prepare le frame
        initFrame();

        // Place tous les composants 
        placeAllComponent();
    }

/// Fonction de classe
    public void placeAllComponent() {

        // Ajout du titre
        JLabel titre = new JLabel("ROMBO");
        titre.setFont(new Font(Font.SERIF, Font.BOLD, 50));
        titre.setForeground(Color.WHITE);
        titre.setBounds(160, 50, 200, 70);
        this.add(titre);

        // Ajout du bouton pour remplir  
        JLabel labelNom = new JLabel("Entrer le nom de ton joueur : ");
        JTextField champNom = new JTextField();
        labelNom.setBounds(100, 130, 300, 30);
        champNom.setBounds(100, 170, 300, 30);

        setLabelNom(champNom);
        this.add(labelNom);
        this.add(champNom);

        if (getFenetre().getMode().equals("client")) {
            JLabel labelIp = new JLabel("Adresse Ip de l'hote : ");
            JTextField champIp = new JTextField();
            labelIp.setBounds(100, 210, 300, 30);
            champIp.setBounds(100, 250, 300, 30);

            setLabelIp(champIp);
            this.add(labelIp);
            this.add(champIp);
        }

        int y = 305;
        if (!getFenetre().getMode().equals("client")) y -= 70; 
        JButton valider = new JButton("valider");
        valider.setBounds(150, y, 200, 40);
        valider.addActionListener(new InputListener(getFenetre(), this));
        this.add(valider);
        getFenetre().repaint();

        // Indication d'erreur
        if (getErreur() != null) {
            JLabel labelErreur = new JLabel(getErreur());
            labelErreur.setBounds(200, 285, 200, 10);
            labelErreur.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
            labelErreur.setForeground(Color.RED);
            this.add(labelErreur);
        }

        // Bouton retour
        JButton retour = new JButton("Retour");
        retour.setBounds(360, 323, 70, 20);
        retour.setFont(new Font(Font.SERIF, Font.PLAIN, 8));
        retour.addActionListener(new InputListener(getFenetre(), this));
        add(retour);

    }

    public void initFrame() {
        this.setVisible(true);
        this.setLayout(null);
        this.setBackground(Color.LIGHT_GRAY);
        getFenetre().add(this);
        getFenetre().repaint();
    }

}
