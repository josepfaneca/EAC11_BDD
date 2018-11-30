package persistencia;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseFileLockedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import model.Desti;
import principal.GestioExcursionsExcepcio;
import com.db4o.query.*;
import controlador.ControladorPrincipal;
import javax.swing.JOptionPane;
import vista.MenuDestiVista;

/**
 *
 * @author fta
 */
public class GestorDB4O implements ProveedorPersistencia {

    private ObjectContainer db;
    private Desti desti;
    private String file = "EAC111819S1.db4o";

    public Desti getDesti() {
        return desti;
    }

    public void setDesti(Desti desti) {
        this.desti = desti;
    }

    /*
     *TODO
     * 
     *Paràmetres: cap
     *
     *Acció:
     *  - Heu de crear / obrir la base de dades "EAC111819S1.db4o"
     *  - Aquesta base de dades ha de permetre que Desti s'actualitzi en cascada.
     *
     *Retorn: cap
     *
     */
    public void estableixConnexio() {
        try {
            EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
            config.common().objectClass(Desti.class).cascadeOnUpdate(true);
            db = Db4oEmbedded.openFile(config, file);
        } catch (DatabaseFileLockedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void tancaConnexio() {
        db.close();
    }

    /*
     *TODO
     * 
     *Paràmetres: el nom del fitxer i el destí a desar
     *
     *Acció:
     *  - Establiu la connexio i al final tanqueu-la
     *  - Heu de desar l'objecte Desti passat per paràmetre sobre la base de dades 
     *    (heu d'inserir si no existia ja a la base de dades, o actualitzar en altre cas)
     *  - S'ha de fer la consulta de l'existència amb Predicate
     *
     *Retorn: cap
     *
     */
    @Override
    public void desarDesti(String nomFitxer, Desti pDesti) throws GestioExcursionsExcepcio {
        int codiDesti = Integer.valueOf(nomFitxer);
        estableixConnexio();
        try {
            Predicate p;
            p = new Predicate<Desti>() {
                @Override
                public boolean match(Desti d) {
                    return codiDesti == d.getCodi();
                }
            };
            ObjectSet<Desti> result = db.query(p);
            if (result.hasNext()) {//actualitzar
                desti = result.next();
                desti.setCodi(codiDesti);
                desti.setContinent(pDesti.getContinent());
                desti.setNom(pDesti.getNom());
                db.store(desti);

            } else {//desar
                desti = new Desti(pDesti.getNom(), pDesti.getContinent(), codiDesti);
                db.store(desti);
            }
        } catch (DatabaseClosedException | Db4oIOException e) {
            throw new GestioExcursionsExcepcio("GestorDoB.desar");
        } finally {
            db.close();
            tancaConnexio();
        }
    }

    /*
     *TODO
     * 
     *Paràmetres: el nom del fitxer on està guardat el destí
     *
     *Acció:
     *  - Establiu la connexio i al final tanqueu-la
     *  - Heu de carregar el destí des de la base de dades assignant-lo a l'atribut destí.
     *  - Si no existeix, llanceu l'excepció GestioExcursionsExcepcio amb codi  "GestorDB4O.noExisteix"
     *  - S'ha de fer la consulta amb Predicate
     *
     *Retorn: cap
     *
     */
    @Override
    public void carregarDesti(String nomFitxer) throws GestioExcursionsExcepcio {
        estableixConnexio();
        int codiNomFitxer = Integer.valueOf(nomFitxer);
        try {
            Predicate p;
            p = new Predicate<Desti>() {
                @Override
                public boolean match(Desti d) {
                    return codiNomFitxer == d.getCodi();
                }
            };
            ObjectSet<Desti> result = db.query(p);
            if (result.hasNext()) {
                desti = result.next();
                //imprimir per consola resultat
                System.out.println("CODI: " + desti.getCodi() + " NOM: " + desti.getNom() + " CONTINENT: " + desti.getContinent());
            } else {
                throw new GestioExcursionsExcepcio("GestorDB4O.noExisteix");
            }

        } catch (DatabaseClosedException | Db4oIOException e) {
            throw new GestioExcursionsExcepcio("GestorDB4O.noExisteix");
        } finally {
            db.close();
            tancaConnexio();
        }
    }
}
