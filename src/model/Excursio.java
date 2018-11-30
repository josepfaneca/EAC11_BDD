/*
 * Classe que defineix una excursió. Una excursió es defineix per un codi, nom i 
 * un preu. A més, contindrà un vector amb guies, visites
 * amb entrada lliure i visites de pagament.
 */
package model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import principal.Component;
import static principal.Component.DADES;

/**
 *
 * @author FTA
 */
public class Excursio implements Component {

    private String codi;
    private String nom;
    private double preu;
    private Map<String, Component> components = new HashMap();

    /*
     TODO CONSTRUCTOR
     Paràmetres: valors per tots els atributs de la classe menys els vectors.
     Accions:
     - Assignar als atributs corresponents els valors passats com a paràmetres
     */
    public Excursio(String pCodi, String pNom, double pPreu) {
        codi = pCodi;
        nom = pNom;
        preu = pPreu;
    }

    /*
     TODO Mètodes accessors
     */
    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    public Map<String, Component> getComponents() {
        return components;
    }

    public void setComponents(Map<String, Component> components) {
        this.components = components;
    }

    /*
     TODO
     Paràmetres: cap
     Accions:
     - Demanar a l'usuari les dades per consola per crear una nova excursió. Les dades
     a demanar són les que necessita el constructor.
     - També heu de tenir en compte que el nom pot ser una frases, per exemple, 
     Museu de la ciència.
     Retorn: La nova excursió creada-.
     */
    public static Excursio addExcursio() {

        String codi;
        String nom;
        int preu;

        System.out.println("Codi de l'excursió:");
        codi = DADES.next();
        DADES.nextLine(); //Neteja buffer
        System.out.println("Nom de l'excursió:");
        nom = DADES.nextLine();
        System.out.println("Preu de l'excursió:");
        preu = DADES.nextInt();

        return new Excursio(codi, nom, preu);
    }

    public void updateComponent() {
        System.out.println("\nCodi de l'excursió: " + codi);
        System.out.println("\nEntra el nou codi:");
        codi = DADES.next();
        DADES.nextLine();
        System.out.println("\nNom de l'excursió: " + nom);
        System.out.println("\nEntra el nou nom:");
        nom = DADES.nextLine();
        System.out.println("\nPreu de l'excursió: " + preu);
        System.out.println("\nEntra el nou preu:");
        preu = DADES.nextDouble();
    }

    /*
     Afegir components
     */
    public void addComponent(Component component) {

        if (component instanceof Guia) {
            components.put(((Guia) component).getCodi(), component);
        } else if (component instanceof VisitaLliure) {
            components.put(((VisitaLliure) component).getCodi(), component);
        } else if (component instanceof VisitaPagament) {
            components.put(((VisitaPagament) component).getCodi(), component);
            preu += ((VisitaPagament) component).getPreu();
        }

    }

    public void showComponent() {
        System.out.println("\nLes dades de l'excursió amb codi " + codi + " són:");
        System.out.println("\nNom:" + nom);
        System.out.println("\nPreu:" + preu);

        Iterator<String> codi = components.keySet().iterator();
       
        while(codi.hasNext()){ //Mentres el codi no sigui l'últim...
            components.get(codi.next()).showComponent();             
        } 

    }

}
