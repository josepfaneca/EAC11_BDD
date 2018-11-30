/*
 * Classe abstracta que defineix una visita. Una visita es defineix pel seu codi, 
 * nom, adreça, coordenades i durada en minuts.
 */
package model;

import principal.Component;

/**
 *
 * @author FTA
 */
public abstract class Visita implements Component{
    
    private String codi;
    private String nom;
    private String adreca;
    private String coordenades;
    private int durada; //Durada en minuts

    //Constructor
    public Visita(String pCodi, String pNom, String pAdreca, String pCoordenades, int pDurada) {
        codi = pCodi;
        nom = pNom;
        adreca = pAdreca;
        coordenades = pCoordenades;
        durada = pDurada;
    }

    //Mètodes accessors    
    
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

    public String getCoordenades() {
        return coordenades;
    }

    public void setCoordenades(String coordenades) {
        this.coordenades = coordenades;
    }

    public int getDurada() {
        return durada;
    }

    public void setDurada(int durada) {
        this.durada = durada;
    }
    
    public void updateComponent() {
        
        System.out.println("\nCodi de la visita: " + codi);
        System.out.println("\nEntra el nou codi:");
        codi = DADES.next();
        DADES.nextLine(); //Neteja buffer
        System.out.println("\nNom de la visita: " + nom);
        System.out.println("\nEntra el nou nom:");
        nom = DADES.nextLine();
        System.out.println("\nAdreça de la visita: " + adreca);
        System.out.println("\nEntra la nova adreça:");
        adreca = DADES.nextLine();
        System.out.println("\nCoordenades de la visita: " + coordenades);
        System.out.println("\nEntra les noves coordenades:");
        coordenades = DADES.next();
        System.out.println("\nDurada de la visita: " + durada);
        System.out.println("\nEntra la nova durada:");
        durada = DADES.nextInt();
    }

    public void showComponent() {
        System.out.println("\nLes dades de la visita amb codi " + codi + " són:");
        System.out.println("\nNom:" + nom);
        System.out.println("\nAdreça:" + adreca);
        System.out.println("\nCoordenades:" + coordenades);
        System.out.println("\nDurada:" + durada);
    }
    
}
