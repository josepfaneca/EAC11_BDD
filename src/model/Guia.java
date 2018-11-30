/*
 * Classe que defineix un guia. Un guia es defineix pel seu codi, nom, adreça i 
 * telèfon
 */
package model;

import principal.Component;

/**
 *
 * @author FTA
 */
public class Guia implements Component{
        
    private String codi;
    private String nom;
    private String adreca;
    private String telefon;

    /*
     TODO CONSTRUCTOR
     Paràmetres: valors per tots els atributs de la classe.
     Accions:
     - Assignar als atributs els valors passats com a paràmetres
     */
    public Guia(String pCodi, String pNom, String pAdreca, String pTelefon) {
        codi = pCodi;
        nom = pNom;
        adreca = pAdreca;
        telefon = pTelefon;
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

    public String getAdreca() {
        return adreca;
    }

    public void setAdreca(String adreca) {
        this.adreca = adreca;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
    
    /*
    TODO
     Paràmetres: cap
     Accions:
     - Demanar a l'usuari les dades per consola per crear un nou guia. Les dades
     a demanar són les que necessita el constructor.
     - També heu de tenir en compte que tant el nom com l'adreça, poden ser frases,
     per exemple, Francesc Xavier, o bé, C/ del dofí, 13.
     Retorn: El nou guia creat.
     */
    public static Guia addGuia() {
        
        String codi;
        String nom;        
        String adreca;
        String telefon;

        System.out.println("Codi del guia:");
        codi = DADES.next();
        DADES.nextLine(); //Neteja buffer
        System.out.println("Nom del guia:");
        nom = DADES.nextLine();
        System.out.println("Adreça del guia:");
        adreca = DADES.nextLine();
        System.out.println("Telefon del guia:");
        telefon = DADES.next();

        return new Guia(codi, nom, adreca, telefon);
    }

    /*
     Paràmetres: cap
     Accions:
     - Demanar a l'usuari que introdueixi les noves dades de l'objecte actual
     i modificar els atributs corresponents d'aquest objecte.
     - Li heu de mostrar a l'usuari el valor actual dels atributs de l'objecte
     actual, abans de modificar-los
     Retorn: cap
     */
    public void updateComponent() {
        
        System.out.println("\nCodi del guia: " + codi);
        System.out.println("\nEntra el nou codi:");
        codi = DADES.next();
        DADES.nextLine(); //Neteja buffer
        System.out.println("\nNom del guia: " + nom);
        System.out.println("\nEntra el nou nom:");
        nom = DADES.nextLine();
        System.out.println("\nAdreça del guia: " + adreca);
        System.out.println("\nEntra la nova adreça:");
        adreca = DADES.nextLine();
        System.out.println("\nTelefon del guia: " + telefon);
        System.out.println("\nEntra el nou telefon:");
        telefon = DADES.next();
    }

    public void showComponent() {
        System.out.println("\nLes dades del guia amb codi " + codi + " són:");
        System.out.println("\nNom:" + nom);
        System.out.println("\nAdreça:" + adreca);
        System.out.println("\nTelefon:" + telefon);
    }
    
}
