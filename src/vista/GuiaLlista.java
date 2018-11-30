package vista;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author fta
 */
public class GuiaLlista {
    
    private JFrame frame;
    
    private final int AMPLADA = 600;
    private final int ALCADA = 200;
    
    private JTable tGuia;

    private JButton bSortir;   
    

    public GuiaLlista() {
               
        //Definició de la finestra del menú
        frame = new JFrame("Llistat guies");
        frame.setLayout(new GridLayout(0, 1));

        //Creació de la taula en base al model
        tGuia = new JTable(new GuiaTableModel());
        

        //Creació dels botons del formulari
        bSortir = new JButton("Sortir");

        //Addició del tot el formulari a la finestra
        frame.add(new JScrollPane(tGuia));  
        frame.add(bSortir);

        //Es mostra la finestra amb propietats per defecte
        frame.setSize(AMPLADA, ALCADA);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JTable gettGuia() {
        return tGuia;
    }

    public void settGuia(JTable tGuia) {
        this.tGuia = tGuia;
    }

    public JButton getbSortir() {
        return bSortir;
    }

    public void setbSortir(JButton bSortir) {
        this.bSortir = bSortir;
    }
    
    
}
