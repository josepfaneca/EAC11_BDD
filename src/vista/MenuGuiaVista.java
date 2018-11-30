package vista;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author fta
 */
public class MenuGuiaVista {

    private JFrame frame;

    private JButton[] menuButtons = new JButton[4];

    private final int AMPLADA = 800;
    private final int ALCADA = 600;

    public MenuGuiaVista() {

        //Definició de la finestra del menú
        frame = new JFrame("Menú Guia");
        frame.setLayout(new GridLayout(0, 1));

        //Creació dels botons a la llista
        menuButtons[0] = new JButton("0. Sortir");
        menuButtons[1] = new JButton("1. Alta Guia");
        menuButtons[2] = new JButton("2. Modificar Guia");
        menuButtons[3] = new JButton("3. LListar Guia");

        //Addició dels botons a la finestra
        for (JButton unBoto : menuButtons) {
            frame.add(unBoto);
        }

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

    public JButton[] getMenuButtons() {
        return menuButtons;
    }

    public void setMenuButtons(JButton[] menuButtons) {
        this.menuButtons = menuButtons;
    }
}
