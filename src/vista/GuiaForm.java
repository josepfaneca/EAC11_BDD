package vista;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author fta
 */
public class GuiaForm {
    private JFrame frame;

    private final int AMPLADA = 300;
    private final int ALCADA = 200;

    private JLabel lCodi;
    private JTextField tCodi;
    private JLabel lNom;
    private JTextField tNom;
    private JLabel lAdreca;
    private JTextField tAdreca;
    private JLabel lTelefon;
    private JTextField tTelefon;

    private JButton bDesar;
    private JButton bSortir;

    public GuiaForm() {

        //Definició de la finestra del menú
        frame = new JFrame("Formulari Guia");
        frame.setLayout(new GridLayout(0, 1));

        //Creació dels controls del formulari
        lCodi = new JLabel("Codi");
        tCodi = new JTextField(20);
        lNom = new JLabel("Nom");
        tNom = new JTextField(20);
        lAdreca = new JLabel("Adreça");
        tAdreca = new JTextField(20);
        lTelefon = new JLabel("Teléfon");
        tTelefon = new JTextField(11);
        

        //Creació dels botons del formulari
        bDesar = new JButton("Desar");
        bSortir = new JButton("Sortir");

        //Addició del tot el formulari a la finestra
        frame.add(lCodi);
        frame.add(tCodi);
        frame.add(lNom);
        frame.add(tNom);
        frame.add(lAdreca);
        frame.add(tAdreca);
        frame.add(lTelefon);
        frame.add(tTelefon);
        frame.add(bDesar);
        frame.add(bSortir);

        //Es mostra la finestra amb propietats per defecte
        frame.setSize(AMPLADA, ALCADA);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public GuiaForm(String codi, String nom, String adreca, String telefon) {
        this();
        tCodi.setText(codi);
        tCodi.setEnabled(false);
        tNom.setText(nom);
        tAdreca.setText(adreca);
        tTelefon.setText(telefon);  
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JLabel getlCodi() {
        return lCodi;
    }

    public void setlCodi(JLabel lCodi) {
        this.lCodi = lCodi;
    }

    public JTextField gettCodi() {
        return tCodi;
    }

    public void settCodi(JTextField tCodi) {
        this.tCodi = tCodi;
    }

    public JLabel getlNom() {
        return lNom;
    }

    public void setlNom(JLabel lNom) {
        this.lNom = lNom;
    }

    public JTextField gettNom() {
        return tNom;
    }

    public void settNom(JTextField tNom) {
        this.tNom = tNom;
    }

    public JLabel getlAdreca() {
        return lAdreca;
    }

    public void setlAdreca(JLabel lAdreca) {
        this.lAdreca = lAdreca;
    }

    public JTextField gettAdreca() {
        return tAdreca;
    }

    public void settAdreca(JTextField tAdreca) {
        this.tAdreca = tAdreca;
    }

    public JLabel getlTelefon() {
        return lTelefon;
    }

    public void setlTelefon(JLabel lTelefon) {
        this.lTelefon = lTelefon;
    }

    public JTextField gettTelefon() {
        return tTelefon;
    }

    public void settTelefon(JTextField tTelefon) {
        this.tTelefon = tTelefon;
    }

    public JButton getbDesar() {
        return bDesar;
    }

    public void setbDesar(JButton bDesar) {
        this.bDesar = bDesar;
    }

    public JButton getbSortir() {
        return bSortir;
    }

    public void setbSortir(JButton bSortir) {
        this.bSortir = bSortir;
    }
    
    
}
