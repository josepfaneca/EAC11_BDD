/*
 * Classe que defineix una visita amb entrada lliure. Una visita amb entrada lliure 
 * es defineix pel seu codi, nom, adreça, coordenades i durada en minuts
 */
package model;


/**
 *
 * @author FTA
 */
public class VisitaLliure extends Visita{

    /*
     TODO CONSTRUCTOR
     Paràmetres: valors per tots els atributs de la classe.
     Accions:
     - Assignar als atributs els valors passats com a paràmetres
     */
    public VisitaLliure(String pCodi, String pNom, String pAdreca, String pCoordenades, int pDurada) {
        super(pCodi, pNom, pAdreca, pCoordenades, pDurada);
    }

    
    /*
    TODO
     Paràmetres: cap
     Accions:
     - Demanar a l'usuari les dades per consola per crear una nova visita amb entrada 
     lliure. Les dades a demanar són les que necessita el constructor.
     - També heu de tenir en compte que tant el nom com l'adreça, poden ser frases,
     per exemple, Francesc Xavier, o bé, C/ del dofí, 13.
     Retorn: La nova visita lliure de pagament creada.
     */
    public static VisitaLliure addVisitaLliure() {
        
        String codi;
        String nom;        
        String adreca;
        String coordenades;
        int durada;

        System.out.println("Codi de la visita amb entrada lliure:");
        codi = DADES.next();
        DADES.nextLine(); //Neteja buffer
        System.out.println("Nom de la visita amb entrada lliure:");
        nom = DADES.nextLine();
        System.out.println("Adreça de la visita amb entrada lliure:");
        adreca = DADES.nextLine();
        System.out.println("Coordenades de la visita amb entrada lliure:");
        coordenades = DADES.next();
        System.out.println("Durada de la visita amb entrada lliure:");
        durada = DADES.nextInt();

        return new VisitaLliure(codi, nom, adreca, coordenades, durada);
    }

}
