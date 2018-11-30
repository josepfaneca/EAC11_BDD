package principal;

/**
 *
 * @author fta
 */
public class GestioExcursionsExcepcio extends Exception {

    private String codiCausa = "desconegut";
    private String missatge;

    public GestioExcursionsExcepcio(String codiCausa) {
        this.codiCausa = codiCausa;
        switch (codiCausa) {
            case "1":
                this.missatge = "L'opció ha de ser correcta";
                break;
            case "2":
                this.missatge = "Ja no hi caben més components";
                break;
            case "3":
                this.missatge = "El codi està repetit";
                break;
            case "4":
                missatge = "El codi no existeix";
                break;
            case "5":
                missatge = "No existeix aquesta excursió";
                break;
            case "GestorXML.model":
                this.missatge = "No s'ha pogut crear el model XML per desar el destí";
                break;
            case "GestorXML.desar":
            case "GestorSerial.desar":
            case "GestorJDBC.desar":
            case "GestorDoB.desar":
                this.missatge = "No s'ha pogut desar el destí a causa d'error d'entrada/sortida";
                break;
            case "GestorXML.carregar":
            case "GestorSerial.carregar":
            case "GestorJDBC.carregar":
                this.missatge = "No s'ha pogut carregar el destí a causa d'error d'entrada/sortida";
                break;
            case "GestorJDBC.noExisteix":
            case "GestorDB4O.noExisteix":
                this.missatge = "El fitxer no existeix";
                break;
            default:
                this.missatge = "Error desconegut";
                break;
        }
    }

    @Override
    public String getMessage() {
        return "Amb el codi de causa:  " + codiCausa + ", s'ha generat el següent missatge: " + missatge;
    }
}
