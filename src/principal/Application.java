/*
 * Una classe Application per interactuar amb l'usuari, que contengui els destins 
 * i cridar a la resta de classes i mètodes mitjançant uns menús.
 */
package principal;

import model.Guia;
import model.VisitaLliure;
import model.VisitaPagament;
import java.util.InputMismatchException;
import java.util.Scanner;
import model.Desti;
import model.Excursio;
import persistencia.GestorPersistencia;
import persistencia.GestorXML;

/**
 *
 * @author FTA
 */
public class Application {

    private final static Scanner DADES = new Scanner(System.in);
    static private Desti[] destins = new Desti[5];//Destins de l'agència d'excursions
    static private int posicioDestins = 0; //La propera posició buida del vector destins
    static private Desti destiActual = null; //Destí seleccionat
    static private int tipusComponent = 0;
    static private String FITXER = "desti";
    static private GestorPersistencia gp = new GestorPersistencia();

    public static void main(String[] args) {
        try {
            menuPrincipal();
        } catch (GestioExcursionsExcepcio e) {
            System.out.println(e.getMessage());
        }
    }

    private static void menuPrincipal() throws GestioExcursionsExcepcio {
        int opcio = 0;

        do {
            try {
                System.out.println("\nSelecciona una opció");
                System.out.println("\n0. Sortir");
                System.out.println("\n1. Gestió de destins");
                System.out.println("\n2. Gestió d'excursions");
                System.out.println("\n3. Gestió de guies");
                System.out.println("\n4. Gestió de visites amb entrada lliure");
                System.out.println("\n5. Gestió de visites de pagament");

                opcio = DADES.nextInt();

                switch (opcio) {
                    case 0:
                        break;
                    case 1:
                        menuDestins();
                        break;
                    case 2:
                        if (destiActual != null) {
                            tipusComponent = 4;
                            menuComponents();
                        } else {
                            System.out.println("\nPrimer s'ha de seleccionar el destí en el menú de destins");
                        }
                        break;
                    case 3:
                        if (destiActual != null) {
                            tipusComponent = 1;
                            menuComponents();
                        } else {
                            System.out.println("\nPrimer s'ha de seleccionar el destí en el menú de destins");
                        }
                        break;
                    case 4:
                        if (destiActual != null) {
                            tipusComponent = 2;
                            menuComponents();
                        } else {
                            System.out.println("\nPrimer s'ha de seleccionar el destí en el menú de destins");
                        }
                        break;
                    case 5:
                        if (destiActual != null) {
                            tipusComponent = 3;
                            menuComponents();
                        } else {
                            System.out.println("\nPrimer s'ha de seleccionar el destí en el menú de destins");
                        }
                        break;
                    default:
                        System.out.println("\nS'ha de seleccionar una opció correcta del menú.");
                        break;
                }
            } catch (InputMismatchException e) {
                throw new GestioExcursionsExcepcio("1");
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new GestioExcursionsExcepcio("2");
            }
        } while (opcio != 0);
    }

    public static void menuDestins() throws GestioExcursionsExcepcio, InputMismatchException {
        int opcio = 0;

        do {
            int pos = -1;

            System.out.println("\nSelecciona una opció");
            System.out.println("\n0. Sortir");
            System.out.println("\n1. Alta");
            System.out.println("\n2. Seleccionar");
            System.out.println("\n3. Modificar");
            System.out.println("\n4. LListar destins");
            System.out.println("\n5. Carrega destí");
            System.out.println("\n6. Desa destí");

            opcio = DADES.nextInt();

            switch (opcio) {
                case 0:
                    break;
                case 1:
                    destins[posicioDestins] = Desti.addDesti();
                    posicioDestins++;
                    break;
                case 2:
                    pos = selectDesti();
                    if (pos >= 0) {
                        destiActual = destins[pos];
                    } else {
                        System.out.println("\nNo existeix aquest destí");
                    }
                    break;
                case 3:
                    pos = selectDesti();
                    if (pos >= 0) {
                        destins[pos].updateComponent();
                    } else {
                        System.out.println("\nNo existeix aquest destí");
                    }
                    break;
                case 4:
                    for (int i = 0; i < posicioDestins; i++) {
                        destins[i].showComponent();
                    }
                    break;
                case 5: //Carregar destí
                    posicioDestins = 0;
                    destins = new Desti[1];
                    gp.carregarDesti("XML", FITXER);
                    destins[posicioDestins] = ((GestorXML)gp.getGestor()).getDesti();
                    posicioDestins++;
                    break;
                case 6: //Desar destí
                    pos = selectDesti();
                    if (pos >= 0) {
                        gp.desarDesti("XML", FITXER, destins[pos]);
                    } else {
                        System.out.println("\nNo existeix aquest desti");
                    }
                    break;
                default:
                    System.out.println("\nS'ha de seleccionar una opció correcta del menú.");
                    break;
            }
        } while (opcio != 0);
    }

    public static void menuComponents() throws GestioExcursionsExcepcio, InputMismatchException {
        int opcio = 0;

        do {
            System.out.println("\nSelecciona una opció");
            System.out.println("\n0. Sortir");
            System.out.println("\n1. Alta");
            System.out.println("\n2. Modificació");
            System.out.println("\n3. Llista");
            if (tipusComponent == 4) {
                System.out.println("\n4. Afegir guia a excursió");
                System.out.println("\n5. Afegir visita amb entrada lliure a excursió");
                System.out.println("\n6. Afegir visita de pagament a excursió");
            }

            opcio = DADES.nextInt();

            switch (opcio) {
                case 0:
                    break;
                case 1:
                    switch (tipusComponent) {
                        case 1:
                            destiActual.addGuia(null);
                            break;
                        case 2:
                            destiActual.addVisitaLliure(null);
                            break;
                        case 3:
                            destiActual.addVisitaPagament(null);
                            break;
                        case 4:
                            destiActual.addExcursio(null);
                            break;
                    }
                    break;
                case 2:
                    int indexSel = destiActual.selectComponent(tipusComponent, null);
                    if (indexSel >= 0) {
                        destiActual.getComponents().get(indexSel).updateComponent();
                    } else {
                        System.out.println("\nNo existeix aquest component");
                    }
                    break;
                case 3:
                    for (int i = 0; i < destiActual.getComponents().size(); i++) {
                        if (destiActual.getComponents().get(i) instanceof Guia && tipusComponent == 1) {
                            destiActual.getComponents().get(i).showComponent();
                        } else if (destiActual.getComponents().get(i) instanceof VisitaLliure && tipusComponent == 2) {
                            destiActual.getComponents().get(i).showComponent();
                        } else if (destiActual.getComponents().get(i) instanceof VisitaPagament && tipusComponent == 3) {
                            destiActual.getComponents().get(i).showComponent();
                        } else if (destiActual.getComponents().get(i) instanceof Excursio && tipusComponent == 4) {
                            destiActual.getComponents().get(i).showComponent();
                        }
                    }
                    break;
                case 4:
                    destiActual.addComponentExcursio(1);
                    break;
                case 5:
                    destiActual.addComponentExcursio(2);
                    break;
                case 6:
                    destiActual.addComponentExcursio(3);
                    break;
                default:
                    System.out.println("\nS'ha de seleccionar una opció correcta del menú.");
                    break;
            }
        } while (opcio != 0);
    }

    public static Integer selectDesti() {

        System.out.println("\nCodi del destí?:");
        int codi = DADES.nextInt();

        boolean trobat = false;
        int pos = -1;

        for (int i = 0; i < posicioDestins && !trobat; i++) {
            if (destins[i].getCodi() == codi) {
                pos = i;
                trobat = true;
            }
        }

        return pos;
    }
}
