/*
 * Classe que defineix una visita de pagament. Una visita de pagament es defineix 
 * pel seu codi, nom, adreça, coordenades, durada en minuts i preu.
 */
package model;

/**
 *
 * @author FTA
 */
public class VisitaPagament extends Visita {

    private double preu;

    /*
     TODO CONSTRUCTOR
     Paràmetres: valors per tots els atributs de la classe.
     Accions:
     - Assignar als atributs els valors passats com a paràmetres
     */
    public VisitaPagament(String pCodi, String pNom, String pAdreca, String pCoordenades, int pDurada, double pPreu) {
        super(pCodi, pNom, pAdreca, pCoordenades, pDurada);
        preu = pPreu;
    }

    /*
     TODO Mètodes accessors    
     */
    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    /*
    TODO
     Paràmetres: cap
     Accions:
     - Demanar a l'usuari les dades per consola per crear un nova visita de pagament. 
     Les dades a demanar són les que necessita el constructor.
     - També heu de tenir en compte que tant el nom com l'adreça, poden ser frases,
     per exemple, Francesc Xavier, o bé, C/ del dofí, 13.
     Retorn: La nova visita lliure de pagament creada.
     */
    public static VisitaPagament addVisitaPagament() {

        String codi;
        String nom;
        String adreca;
        String coordenades;
        int durada;
        double preu;

        System.out.println("Codi de la visita de pagament:");
        codi = DADES.next();
        DADES.nextLine(); //Neteja buffer
        System.out.println("Nom de la visita de pagament:");
        nom = DADES.nextLine();
        System.out.println("Adreça de la visita de pagament:");
        adreca = DADES.nextLine();
        System.out.println("Coordenades de la visita de pagament:");
        coordenades = DADES.next();
        System.out.println("Durada de la visita de pagament:");
        durada = DADES.nextInt();
        System.out.println("Preu de la visita de pagament:");
        preu = DADES.nextDouble();

        return new VisitaPagament(codi, nom, adreca, coordenades, durada, preu);
    }

    /*
     Modificar la visita de pagament
     */
    public void updateComponent() {
        super.updateComponent();
        System.out.println("\nPreu de la visita de pagament: " + preu);
        System.out.println("\nEntra el nou preu:");
        preu = DADES.nextDouble();
    }

    public void showComponent() {
        super.showComponent();
        System.out.println("\nPreu:" + preu);
    }
}
