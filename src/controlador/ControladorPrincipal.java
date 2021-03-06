package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import model.Desti;
import persistencia.GestorPersistencia;
import vista.MenuPrincipalVista;

/**
 *
 * @author FTA
 */
public class ControladorPrincipal implements ActionListener {

    static private MenuPrincipalVista menuPrincipalVista;
    static private final int MAXDESTINS = 10;
    static private Desti[] destins = new Desti[MAXDESTINS];
    static private int posicioDestins = 0;
    static private Desti destiActual = null;
    static private int tipusElement = 0;
    static private GestorPersistencia gp = new GestorPersistencia();
    static private final String[] METODESPERSISTENCIA = {"XML","Serial","JDBC","DB4O"}; 
    //static private final String[] METODESPERSISTENCIA = {"XML", "Serial"};

    public ControladorPrincipal() {
        /*
        TODO
        
        S'inicialitza la propietat menuPrincipalVista (això mostrarà el menú principal)
        A cada botó del menú, s'afegeix aquest mateix objecte (ControladorPrincipal) com a listener
        
         */

        menuPrincipalVista = new MenuPrincipalVista();

        //S'AFEGEIX EL CONTROLADOR COM A LISTENER DELS BOTONS
        for (JButton unBoto : menuPrincipalVista.getMenuButtons()) {
            unBoto.addActionListener(this);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        TODO

        S'ha de cridar a bifurcaOpcio segons l'opció premuda. Penseu que l'opció es 
        correspon amb la posició que el botó ocupa a l'array de botons de menuPrincipalVista
        
         */

        JButton[] elsBotons = menuPrincipalVista.getMenuButtons();
        for (int i = 0; i < elsBotons.length; i++) {
            if (e.getSource() == elsBotons[i]) {
                bifurcaOpcio(i);
            }
        }

    }

    private void bifurcaOpcio(int opcio) {

        switch (opcio) {
            case 0:
                System.exit(0);
                break;
            case 1:
                menuPrincipalVista.getFrame().setVisible(false);
                ControladorDesti controladorDesti = new ControladorDesti();
                break;
            case 2:
                menuPrincipalVista.getFrame().setVisible(false);
                ControladorGuia controladorGuia = new ControladorGuia();              
                break;
        }

    }

    public static MenuPrincipalVista getMenuPrincipalVista() {
        return menuPrincipalVista;
    }

    public static void setMenuPrincipalVista(MenuPrincipalVista menuPrincipalVista) {
        ControladorPrincipal.menuPrincipalVista = menuPrincipalVista;
    }

    public static Desti[] getDestins() {
        return destins;
    }

    public static void setDestins(Desti[] destins) {
        ControladorPrincipal.destins = destins;
    }

    public static int getPosicioDestins() {
        return posicioDestins;
    }

    public static void setPosicioDestins() {
        posicioDestins++;
    }

    public static Desti getDestiActual() {
        return destiActual;
    }

    public static void setDestiActual(Desti destiActual) {
        ControladorPrincipal.destiActual = destiActual;
    }

    public static int getTipusElement() {
        return tipusElement;
    }

    public static void setTipusElement(int tipusElement) {
        ControladorPrincipal.tipusElement = tipusElement;
    }

    public static GestorPersistencia getGp() {
        return gp;
    }

    public static void setGp(GestorPersistencia gp) {
        ControladorPrincipal.gp = gp;
    }

    public static int getMAXDESTINS() {
        return MAXDESTINS;
    }

    public static String[] getMETODESPERSISTENCIA() {
        return METODESPERSISTENCIA;
    }
    

}
