package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import model.Desti;
import persistencia.GestorDB4O;
import persistencia.GestorJDBC;
import persistencia.GestorPersistencia;
import persistencia.GestorSerial;
import persistencia.GestorXML;
import principal.GestioExcursionsExcepcio;
import vista.DestiForm;
import vista.DestiLlista;
import vista.MenuDestiVista;

/**
 *
 * @author FTA
 */
public class ControladorDesti implements ActionListener {

    private MenuDestiVista menuDestiVista;
    private DestiForm destiForm = null;
    private DestiLlista destiLlista = null;
    private int opcioSelec = 0;

    public ControladorDesti() {

        /*
        TODO
        
        S'inicialitza l'atribut menuDestiVista (això mostrarà el menú destins)
        Es crida a afegirListenersMenu
        
         */
        
        menuDestiVista = new MenuDestiVista();
        afegirListenersMenu();

    }

    //El controlador com a listener dels controls de les finestres que gestionen els destins
    //S'AFEGEIX EL CONTROLADOR COM A LISTENER DELS BOTONS DEL MENU
    private void afegirListenersMenu() {
        /*
        TODO
        
        A cada botó del menú destins, s'afegeix aquest mateix objecte (ControladorDesti) com a listener
        
         */
        
        for (JButton unBoto : menuDestiVista.getMenuButtons()) {
            unBoto.addActionListener(this);
        }

    }

    //S'AFEGEIX EL CONTROLADOR COM A LISTENER DELS BOTONS DEL FORMULARI
    private void afegirListenersForm() {
        /*
        TODO
        
        A cada botó del formulari del destí, s'afegeix aquest mateix objecte (ControladorDesti) com a listener
        
         */
        
        destiForm.getbDesar().addActionListener(this);
        destiForm.getbSortir().addActionListener(this);

    }

    //S'AFEGEIX EL CONTROLADOR COM A LISTENER DEL BOTO DE LA LLISTA
    private void afegirListenersLlista() {
        /*
        TODO
        
        Al botó de sortir de la llista de destins, s'afegeix aquest mateix objecte (ControladorDesti) com a listener
        */
        
        destiLlista.getbSortir().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        TODO
        
        Nota:
            Com ControladorDesti és listener del menú de destins, del formulari i de la llista, llavors en aquest mètode
            actionPerformed heu de controlar si l'usuari ha premut cap botó de qualsevol dels esmentats frames.
            Ull! En el cas del formulari i de la llista, com provenen del menú (els llança el menú de destins), heu de verificar
            primer que els objectes destiForm o destiLlista no són nulls, per tal de saber si podeu comparar amb
            cap botó d'aquests frames
        
        Accions per al menú:
            S'ha de cridar a bifurcaOpcio segons l'opció premuda. Penseu que l'opció es correspon amb
            la posició que el botó ocupa a l'array de botons de menuDestiVista
            També, heu d'actualitzar la propietat opcioSelec (amb l'opció que ha premut l'usuari)
        
        Accions per al formulari de destí:
            
            ---- DESAR ----
            Si el botó premut per l'usuari és el botó de desar del formulari de destí, llavors
                Si l'opció seleccionada (al menú de destins) és 1 (alta), llavors  
                        Es crea un nou objecte Desti amb les dades del formulari
                        S'afegeix el destí creat a la llista de ControladorPrincipal
                        Es posa aquest destí com destiActual (de ControladorPrincipal) i es canvia l'atribut
                        opcioSelec a 2
                Si l'opció seleccionada (al menú de destins) és 3 (modificació), llavors
                    Nota: no es validen dades amb aquesta opció (per simplificar)
                    Es modifica l'objecte destí amb les dades del formulari (penseu que és el destiActual de ControladorPrincipal)
            
            ---- SORTIR ----
            Si el botó premut per l'usuari és el botó de sortir del formulari de destins, llavors
                Heu de tornar al menú de destí (i amagar el formulari)
        
        Accions per a la llista de destins:
            
            ---- SORTIR ----
            Si el botó premut per l'usuari és el botó de sortir de la llista de destins, llavors
                Heu de tornar al menú de destins (i amagar la llista)
         
         */
        
         //Accions per al menú
        JButton[] elsBotons = menuDestiVista.getMenuButtons();

        for (int i = 0; i < elsBotons.length; i++) {
            if (e.getSource() == elsBotons[i]) {
                menuDestiVista.getFrame().setVisible(false);
                opcioSelec = i;
                bifurcaOpcio(i);
            }
        }

        //Accions per al formulari Desti
        if (destiForm != null) {

            if (e.getSource() == destiForm.getbDesar()) {

                if (opcioSelec == 1) {//Nou desti

                        Desti desti = new Desti(destiForm.gettNom().getText(), destiForm.gettContinent().getText());
                        ControladorPrincipal.getDestins()[ControladorPrincipal.getPosicioDestins()] = desti;
                        ControladorPrincipal.setPosicioDestins();
                        destiForm.gettCodi().setText(String.valueOf(desti.getCodi()));
                        ControladorPrincipal.setDestiActual(desti);
                        opcioSelec = 2;

                } else if (opcioSelec == 3) {//Modificar desti

                    ControladorPrincipal.getDestiActual().setNom(destiForm.gettNom().getText());
                    ControladorPrincipal.getDestiActual().setContinent(destiForm.gettContinent().getText());

                }

            } else if (e.getSource() == destiForm.getbSortir()) { //Sortir

                destiForm.getFrame().setVisible(false);
                menuDestiVista.getFrame().setVisible(true);

            }

        }

        if (destiLlista != null) {

            if (e.getSource() == destiLlista.getbSortir()) {

                destiLlista.getFrame().setVisible(false);
                menuDestiVista.getFrame().setVisible(true);

            }

        }

    }

    private void bifurcaOpcio(Integer opcio) {

        switch (opcio) {

            case 0: //sortir
                ControladorPrincipal.getMenuPrincipalVista().getFrame().setVisible(true);
                break;

            case 1: // alta
                if (ControladorPrincipal.getPosicioDestins()< ControladorPrincipal.getMAXDESTINS()) {
                    destiForm = new DestiForm();
                    destiForm.gettCodi().setEnabled(false);
                    afegirListenersForm();
                } else {
                    menuDestiVista.getFrame().setVisible(true);
                    JOptionPane.showMessageDialog(menuDestiVista.getFrame(), "Màxim nombre de destins assolits.");
                }
                break;

            case 2: //seleccionar
                menuDestiVista.getFrame().setVisible(true);
                if (ControladorPrincipal.getDestins()[0] != null) {
                    seleccionarDesti();
                } else {
                    JOptionPane.showMessageDialog(menuDestiVista.getFrame(), "Abans s'ha de crear al menys un destí");
                }
                break;

            case 3: //modificar
                if (ControladorPrincipal.getDestins()[0] != null) {
                    seleccionarDesti();
                    destiForm = new DestiForm(ControladorPrincipal.getDestiActual().getCodi(), ControladorPrincipal.getDestiActual().getNom(), ControladorPrincipal.getDestiActual().getContinent());
                    destiForm.gettCodi().setEnabled(false);
                    afegirListenersForm();
                } else {
                    menuDestiVista.getFrame().setVisible(true);
                    JOptionPane.showMessageDialog(menuDestiVista.getFrame(), "Abans s'ha de crear al menys un destí");
                }
                break;

            case 4: // llistar
                if (ControladorPrincipal.getDestins()[0] != null) {
                    destiLlista = new DestiLlista();
                    afegirListenersLlista();
                } else {
                    menuDestiVista.getFrame().setVisible(true);
                    JOptionPane.showMessageDialog(menuDestiVista.getFrame(), "Abans s'ha de crear al menys un destí");
                }
                break;

            case 5: //carregar
            /*
            TODO
                
            Es mostra un dialog (JOptionPane.showOptionDialog) amb botons, on cadascun d'ells és un mètode de càrrega 
            (propietat a Controlador Principal: ara XML i Serial)
            Un cop seleccionat el mètode, amb un altre dialog, es demana el codi del destí a carregar 
            (recordeu que el nom del fitxer és el codi del destí i l'extensió)
            Un cop l'usuari ha entrat el codi i ha premut OK,
                Es crea un objecte destí (nouDesti) com retorn de cridar a carregarDesti del gestor de persistència. Penseu que la
                carrega pots ser d'un fitxer XML o bé d'un fitxer serial.
                Es comprova si el codi del nouDesti ja existeix al vector de destins (això donarà la posició on s'ha trobat a la llista). Penseu
                que en aquesta classe teniu un mètode per fer la comprovació.
                Si existeix,
                    es mostra un dialog notificant a l'usuari si vol substituir el destí del vector pel que es carregarà des de el fitxer (JOptionPane.showOptionDialog)
                    Si l'usuari cancela, no es fa res
                    Si l'usuari accepta, llavors es posa el nouDesti al vector a la mateixa posició on s'havia trobat aquest codi
                Si no existeix,
                    S'afegeix el nouDesti al vector de destins a la darrera posició
                    Es mostra un missatge confirmant l'addició (JOptionPane.showMessageDialog)
            
            */
                
                menuDestiVista.getFrame().setVisible(true);
                
                int code = JOptionPane.showOptionDialog(null, "Selecciona un mètode", "Carregar destí", 0, JOptionPane.QUESTION_MESSAGE, null, ControladorPrincipal.getMETODESPERSISTENCIA(), "XML");
                
                if (code != JOptionPane.CLOSED_OPTION) {
                    
                    GestorPersistencia gestor = new GestorPersistencia();
                    
                    Desti desti;
                    
                    try {
                        
                        String elCodi = JOptionPane.showInputDialog("Quin és el codi del destí que vols carregar?");
                        
                        gestor.carregarDesti(ControladorPrincipal.getMETODESPERSISTENCIA()[code], elCodi);
                        
                        if(ControladorPrincipal.getMETODESPERSISTENCIA()[code].equals("XML")){
                            desti=((GestorXML)gestor.getGestor()).getDesti();
                        }else if (ControladorPrincipal.getMETODESPERSISTENCIA()[code].equals("Serial")){
                            desti=((GestorSerial)gestor.getGestor()).getDesti();
                        }else if (ControladorPrincipal.getMETODESPERSISTENCIA()[code].equals("JDBC")){
                            desti=((GestorJDBC)gestor.getGestor()).getDesti();
                        }else{ //GestorDB4O
                            desti=((GestorDB4O)gestor.getGestor()).getDesti();
                        }
                        
                        int pos = comprovarDesti(desti.getCodi());
                        
                        if (pos >= 0) {
                            
                            Object[] options = {"OK", "Cancel·lar"};                            
                            int i = JOptionPane.showOptionDialog(null, "Premeu OK per substituir-lo.", "Destí ja existent",
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                                    null, options, options[0]);
                            
                            if (i == 0) {
                                ControladorPrincipal.getDestins()[pos] = desti;
                            }
                            
                        } else {
                            
                            ControladorPrincipal.getDestins()[ControladorPrincipal.getPosicioDestins()] = desti;
                            ControladorPrincipal.setPosicioDestins();
                            JOptionPane.showMessageDialog(menuDestiVista.getFrame(), "Destí afegit correctament");
                        
                        }

                    } catch (GestioExcursionsExcepcio e) {
                        JOptionPane.showMessageDialog(menuDestiVista.getFrame(), e.getMessage());
                    }
                }
                
                break;

            case 6: //desar
                /*
                TODO
                
                Es comprova si s'ha seleccionat el destí, mostrant, si correspon, missatges d'error (JOptionPane.showMessageDialog)
                Si s'ha sseleccionat el destí, 
                    Es mostra un dialog (JOptionPane.showOptionDialog) amb botons, on cadascun d'ells és un mètode de càrrega
                    (propietat a Controlador Principal: ara XML i Serial)
                    Un cop escollit el mètode, es desa el destí cridant a desarDesti del gestor de persistència
                 */
                
                menuDestiVista.getFrame().setVisible(true);
                
                if (ControladorPrincipal.getDestiActual() != null) {
                    
                    int messageTyped = JOptionPane.QUESTION_MESSAGE;
                    int coded = JOptionPane.showOptionDialog(null, "Selecciona un mètode", "Desar destí", 0, messageTyped, null, ControladorPrincipal.getMETODESPERSISTENCIA(), "XML");
                    
                    if (coded != JOptionPane.CLOSED_OPTION) {
                        
                        GestorPersistencia gestor = new GestorPersistencia();
                        
                        try {
                            gestor.desarDesti(ControladorPrincipal.getMETODESPERSISTENCIA()[coded], String.valueOf(ControladorPrincipal.getDestiActual().getCodi()), ControladorPrincipal.getDestiActual());
                        } catch (GestioExcursionsExcepcio e) {
                            JOptionPane.showMessageDialog(menuDestiVista.getFrame(), e.getMessage());
                        }
                        
                    }
                    
                } else {
                    JOptionPane.showMessageDialog(menuDestiVista.getFrame(), "Abans s'ha de seleccionar un destí");
                }

                break;

        }

    }

    private void seleccionarDesti() {

        String[] nomDesti = new String[ControladorPrincipal.getPosicioDestins()];

        int i = 0;

        for (Desti desti : ControladorPrincipal.getDestins()) {

            if (desti != null) {
                nomDesti[i] = desti.getNom();
            }

            i++;

        }

        int messageType = JOptionPane.QUESTION_MESSAGE;
        int code = JOptionPane.showOptionDialog(null, "Selecciona un desti", "Selecció de desti", 0, messageType, null, nomDesti, "A");
        
        if (code != JOptionPane.CLOSED_OPTION) {
            ControladorPrincipal.setDestiActual(ControladorPrincipal.getDestins()[code]);
        }

    }

    private Integer comprovarDesti(int codi) {

        boolean trobat = false;

        int pos = -1;

        for (int i = 0; i < ControladorPrincipal.getDestins().length && !trobat; i++) {

            if (ControladorPrincipal.getDestins()[i] != null) {
                if (ControladorPrincipal.getDestins()[i].getCodi() == codi) {
                    pos = i;
                    trobat = true;
                }
            }
        }

        return pos;
    }

}