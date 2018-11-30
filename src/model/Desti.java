/*
 * Classe que defineix un desti. Un destí es defineix per un codi, un nom i continent 
 * al qual pertany el destí. A més, contindrà un vector amb excursions, guies, visites 
 * amb entrada lliure  i visites de pagament.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import principal.Component;
import static principal.Component.DADES;
import principal.GestioExcursionsExcepcio;

/**
 *
 * @author FTA
 */
public class Desti implements Component, Serializable{

    private int codi;
    private static int properCodi = 1; //El proper codi a assignar
    private String nom;
    private String continent;
    private List<Component> components = new ArrayList();

    /*
     TODO
     CONSTRUCTOR
     Paràmetres: valors per tots els atributs de la classe menys els vectors i el
     codi.
     Accions:
     - Assignar als atributs corresponents els valors passats com a paràmetres
     - Assignar a l'atribut codi el valor de l'atribut properCodi i actualitzar
     properCodi amb el següent codi a assignar.
     */
    public Desti(String pNom, String pContinent) {
        codi = properCodi;
        properCodi++;
        nom = pNom;
        continent = pContinent;
    }

    //Nou constructor
    public Desti(String pNom, String pContinent, int pCodi) {
        codi = pCodi;
        nom = pNom;
        continent = pContinent;
    }

    /*
     TODO Mètodes accessors    
     */
    public int getCodi() {
        return codi;
    }

    public void setCodi(int codi) {
        this.codi = codi;
    }

    public static int getProperCodi() {
        return properCodi;
    }

    public static void setProperCodi() {
        properCodi++;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    /*
    TODO
     Paràmetres: cap
     Accions:
     - Demanar a l'usuari les dades per consola per crear un nou destí. Les dades
     a demanar són les que necessita el constructor.
     - Heu de tenir en compte que el nom pot ser una frase, per exemple, New York.
     Retorn: El nou destí creat.
     */
    public static Desti addDesti() {

        String nom;
        String continent;

        System.out.println("Nom del destí:");
        nom = DADES.nextLine();
        System.out.println("Continent del destí:");
        continent = DADES.nextLine();
        return new Desti(nom, continent);
    }

    public void updateComponent() {

        System.out.println("\nNom del destí: " + nom);
        System.out.println("\nEntra el nou nom:");
        nom = DADES.nextLine();
        System.out.println("\nContinent del destí: " + continent);
        System.out.println("\nEntra el nou continent:");
        continent = DADES.next();
    }

    public void showComponent() {
        System.out.println("\nLes dades del destí amb codi " + codi + " són:");
        System.out.println("\nNom:" + nom);
        System.out.println("\nContinent:" + continent);
    }

    public void addGuia(Guia guia) throws GestioExcursionsExcepcio {

        if (guia == null) {
            guia = Guia.addGuia();
        }

        if (selectComponent(1, guia.getCodi()) == -1) {
            components.add(guia);
        } else {
            throw new GestioExcursionsExcepcio("3");
        }
    }

    public void addVisitaLliure(VisitaLliure visitaLliure) throws GestioExcursionsExcepcio {

        if (visitaLliure == null) {
            visitaLliure = VisitaLliure.addVisitaLliure();
        }

        if (selectComponent(2, visitaLliure.getCodi()) == -1) {
            components.add(visitaLliure);
        } else {
            throw new GestioExcursionsExcepcio("3");
        }
    }

    public void addVisitaPagament(VisitaPagament visitaPagament) throws GestioExcursionsExcepcio {

        if (visitaPagament == null) {
            visitaPagament = VisitaPagament.addVisitaPagament();
        }

        if (selectComponent(3, visitaPagament.getCodi()) == -1) {
            components.add(visitaPagament);
        } else {
            throw new GestioExcursionsExcepcio("3");
        }
    }

    public void addExcursio(Excursio excursio) throws GestioExcursionsExcepcio {

        if (excursio == null) {
            excursio = Excursio.addExcursio();
        }

        if (selectComponent(4, excursio.getCodi()) == -1) {
            components.add(excursio);
        } else {
            throw new GestioExcursionsExcepcio("3");
        }
    }

    public void addComponentExcursio(int tipusComponent) throws GestioExcursionsExcepcio {
        Excursio excursioSel = null;
        int pos = selectComponent(4, null);

        if (pos >= 0) {
            excursioSel = (Excursio) this.getComponents().get(pos);

            pos = selectComponent(tipusComponent, null);

            if (pos >= 0) {
                excursioSel.addComponent(getComponents().get(pos));
            } else {
                throw new GestioExcursionsExcepcio("4");
            }
        } else {
            throw new GestioExcursionsExcepcio("5");
        }
    }

    public int selectComponent(int tipusComponent, String codi) {

        if (codi == null) {
            //Demanem quin tipus de component vol seleccionar i el seu codi
            switch (tipusComponent) {
                case 1:
                    System.out.println("Codi del guia?:");
                    break;
                case 2:
                    System.out.println("Codi de la visita lliure?:");
                    break;

                case 3:
                    System.out.println("Codi de la visita de pagament?:");
                    break;
                case 4:
                    System.out.println("Codi de l'excursió?:");
                    break;
            }

            codi = DADES.next();
        }

        int posElement = -1; //Posició que ocupa el component seleccionat dins l'ArrayList de components del destí
        boolean trobat = false;

        //Seleccionem la posició que ocupa el component dins l'ArrayList de components
        // del destí
        for (int i = 0; i < components.size() && !trobat; i++) {
            if (components.get(i) instanceof Guia && tipusComponent == 1) {
                if (((Guia) components.get(i)).getCodi().equals(codi)) {
                    posElement = i;
                    trobat = true;
                }
            } else if (components.get(i) instanceof VisitaLliure && tipusComponent == 2) {
                if (((VisitaLliure) components.get(i)).getCodi().equals(codi)) {
                    posElement = i;
                    trobat = true;
                }
            } else if (components.get(i) instanceof VisitaPagament && tipusComponent == 3) {
                if (((VisitaPagament) components.get(i)).getCodi().equals(codi)) {
                    posElement = i;
                    trobat = true;
                }
            } else if (components.get(i) instanceof Excursio && tipusComponent == 4) {
                if (((Excursio) components.get(i)).getCodi().equals(codi)) {
                    posElement = i;
                    trobat = true;
                }
            }
        }

        return posElement;
    }
}
