package persistencia;

import principal.GestioExcursionsExcepcio;
import model.Desti;

/**
 *
 * @author FTA
 */
public class GestorPersistencia {

    private ProveedorPersistencia gestor;

    public ProveedorPersistencia getGestor() {
        return gestor;
    }

    public void setGestor(ProveedorPersistencia pGestor) {
        gestor = pGestor;
    }

    public void desarDesti(String tipusPersistencia, String nomFitxer, Desti desti) throws GestioExcursionsExcepcio {

        switch (tipusPersistencia) {

            case "XML":
                gestor = new GestorXML();
                break;
            case "Serial":
                gestor = new GestorSerial();
                break;
            case "JDBC":
                gestor = new GestorJDBC();
                break;
            default:
                gestor = new GestorDB4O();
                break;

        }

        gestor.desarDesti(nomFitxer, desti);
    }

    public void carregarDesti(String tipusPersistencia, String nomFitxer) throws GestioExcursionsExcepcio {

        switch (tipusPersistencia) {

            case "XML":
                gestor = new GestorXML();
                break;
            case "Serial":
                gestor = new GestorSerial();
                break;
            case "JDBC":
                gestor = new GestorJDBC();
                break;
            default:
                gestor = new GestorDB4O();
                break;
        }
        gestor.carregarDesti(nomFitxer);
    }

}
