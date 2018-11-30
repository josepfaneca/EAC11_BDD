package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import model.Guia;
import principal.Component;
import principal.GestioExcursionsExcepcio;
import vista.GuiaForm;
import vista.GuiaLlista;
import vista.MenuGuiaVista;

/**
 *
 * @author fta
 */
public class ControladorGuia implements ActionListener {

    private MenuGuiaVista menuGuiaVista;
    private GuiaForm guiaForm = null;
    private Guia guia = null;
    private GuiaLlista guiaLlista = null;
    private int opcioSeleccionada = 0;

    public ControladorGuia() {
        menuGuiaVista = new MenuGuiaVista();
        afegirListenersMenu();
    }

    private void afegirListenersMenu() {
        for (JButton unBoto : menuGuiaVista.getMenuButtons()) {
            unBoto.addActionListener(this);
        }
    }

    private void afegirListenersForm() {
        guiaForm.getbDesar().addActionListener(this);
        guiaForm.getbSortir().addActionListener(this);
    }

    private void afegirListenersLlista() {
        guiaLlista.getbSortir().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            //Accions per al menú
            JButton[] elsBotons = menuGuiaVista.getMenuButtons();
            for (int i = 0; i < elsBotons.length; i++) {
                if (e.getSource() == elsBotons[i]) {
                    menuGuiaVista.getFrame().setVisible(false);
                    opcioSeleccionada = i;
                    bifurcaOpcio(i);
                }
            }

            //Accions per al formulari Guia
            if (guiaForm != null) {

                boolean actiu = true;

                if (e.getSource() == guiaForm.getbDesar()) {

                    if (opcioSeleccionada == 1) {

                        Guia guia = new Guia(guiaForm.gettCodi().getText(), guiaForm.gettNom().getText(), guiaForm.gettAdreca().getText(), guiaForm.gettTelefon().getText());
                        ControladorPrincipal.getDestiActual().addGuia(guia);

                    } else if (opcioSeleccionada == 2) {

                        guia.setNom(guiaForm.gettNom().getText());
                        guia.setAdreca(guiaForm.gettAdreca().getText());
                        guia.setTelefon(guiaForm.gettTelefon().getText());

                    }

                } else if (e.getSource() == guiaForm.getbSortir()) {
                    guiaForm.getFrame().setVisible(false);
                    menuGuiaVista.getFrame().setVisible(true);
                }
            }

            if (guiaLlista != null) {
                if (e.getSource() == guiaLlista.getbSortir()) {
                    guiaLlista.getFrame().setVisible(false);
                    menuGuiaVista.getFrame().setVisible(true);
                }
            }

        } catch (GestioExcursionsExcepcio ex) {
            JOptionPane.showMessageDialog(menuGuiaVista.getFrame(), ex.getMessage());
        }
    }

    private void bifurcaOpcio(Integer opcio) throws GestioExcursionsExcepcio {
        switch (opcio) {
            case 0: //sortir
                ControladorPrincipal.getMenuPrincipalVista().getFrame().setVisible(true);
                break;
            case 1: // alta
                if (ControladorPrincipal.getDestins()[0] != null) {
                    guiaForm = new GuiaForm();
                    afegirListenersForm();
                } else {
                    menuGuiaVista.getFrame().setVisible(true);
                    JOptionPane.showMessageDialog(menuGuiaVista.getFrame(), "Abans s'ha de crear al menys un destí");
                }
                break;
            case 2: // modificar
                if (ControladorPrincipal.getDestins()[0] != null) {
                    //afegir un control d'errors
                    if (!(ControladorPrincipal.getDestiActual().getComponents().isEmpty())) {
                        int pos = ControladorPrincipal.getDestiActual().selectComponent(1, selectGuia());
                        if (pos != -1) {
                            guia = (Guia) ControladorPrincipal.getDestiActual().getComponents().get(pos);
                            guiaForm = new GuiaForm(guia.getCodi(), guia.getNom(), guia.getAdreca(), guia.getTelefon());
                            afegirListenersForm();
                        }
                    } else {
                        menuGuiaVista.getFrame().setVisible(true);
                        JOptionPane.showMessageDialog(menuGuiaVista.getFrame(), "No existeixen guies per aquest destí");
                    }
                } else {
                    menuGuiaVista.getFrame().setVisible(true);
                    JOptionPane.showMessageDialog(menuGuiaVista.getFrame(), "Abans s'ha de crear al menys un destí i existir guies per aquest destí");
                }
                break;
            case 3: // llista
                if (ControladorPrincipal.getDestins()[0] != null) {
                    //afegir un control d'errrors 
                    if (!(ControladorPrincipal.getDestiActual().getComponents().isEmpty())) {
                        guiaLlista = new GuiaLlista();
                        afegirListenersLlista();
                    } else {
                        menuGuiaVista.getFrame().setVisible(true);
                        JOptionPane.showMessageDialog(menuGuiaVista.getFrame(), "No hi ha guies per aquest destí");
                    }
                } else {
                    menuGuiaVista.getFrame().setVisible(true);
                    JOptionPane.showMessageDialog(menuGuiaVista.getFrame(), "Abans s'ha de crear al menys un destí");
                }
                break;
        }
    }

    private String selectGuia() {

        int i = 0;

        for (Component component : ControladorPrincipal.getDestiActual().getComponents()) {

            if (component instanceof Guia) {
                i++;
            }

        }

        String[] codisGuies = new String[i];

        i = 0;

        for (Component component : ControladorPrincipal.getDestiActual().getComponents()) {

            if (component instanceof Guia) {
                codisGuies[i] = ((Guia) component).getCodi();
            }

            i++;

        }

        int messageType = JOptionPane.QUESTION_MESSAGE;
        int code = JOptionPane.showOptionDialog(null, "Selecciona un guia", "Selecció de guia", 0, messageType, null, codisGuies, null);

        if (code != JOptionPane.CLOSED_OPTION) {
            return codisGuies[code];
        }

        return null;
    }
}
