package persistencia;

import controlador.ControladorPrincipal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Desti;
import model.Guia;
import principal.Component;
import principal.GestioExcursionsExcepcio;

/**
 *
 * @author fta
 */
public class GestorJDBC implements ProveedorPersistencia {

    private Desti desti;

    private Connection conn; //Connexió a la base de dades

    private static final String DB_URL = "jdbc:derby://localhost:1527/EAC111819S1;create=true;user=root;password=root123";
    
    private static final String DRIVER_DERBY = "org.apache.derby.jdbc.ClientDriver";

    public Desti getDesti() {
        return desti;
    }

    public void setDesti(Desti desti) {
        this.desti = desti;
    }

    /*
     PreparedStatement necessaris
     */

 /*
     * TODO
     *
     * Obtenir un destí
     * 
     * Sentència select de la taula destins
     * Columnes: totes
     * Files: fila del destí que el seu codi coincideixi amb el codi passat per paràmetre.
     *
     */
    private static String codiDestiSQL = "SELECT * FROM DESTINS WHERE CODI = ?";

    private PreparedStatement codiDestiSt;

    /*
     * TODO
     *
     * Inserir un destí
     * 
     * Sentència d'inserció de la taula destins
     * Els valors d'inserció són els que es donaran per paràmetre
     *
     */
    private static String insereixDestiSQL = "INSERT INTO DESTINS VALUES (?, ?, ?)";

    private PreparedStatement insereixDestiSt;

    /*
     * TODO
     *
     * Actualitzar destí
     * 
     * Sentència d'actualització de la taula destins
     * Files a actualitzar: la que es corresponguin amb el codi donat per paràmetre
     * Columnes a actualitzar: nom i continent amb els valors donats per paràmetre
     *
     */
    private static String actualitzaDestiSQL = "UPDATE DESTINS SET NOM=?, CONTINENT=? WHERE CODI=?";

    private PreparedStatement actualitzaDestiSt;

    /*
     * TODO
     *
     * Eliminar guies (donat el codi d'un desti)
     * 
     * Sentència d'eliminació de la taula guies
     * Files a eliminar: les que es corresponguin amb el codi del destí donat per paràmetre
     *
     */
    private static String eliminaGuiesSQL = "DELETE FROM GUIES WHERE DESTI=?";

    private PreparedStatement eliminaGuiesSt;

    /*
     * TODO
     *
     * Inserir guia
     * 
     * Sentència d'inserció de la taula guies
     * Els valors d'inserció són els que es donaran per paràmetre
     *
     */
    private static String insereixGuiaSQL = "INSERT INTO GUIES VALUES (?, ?, ?, ?, ?)";

    private PreparedStatement insereixGuiaSt;

    /*
     Seleccionar guies d'un destí
     */
 /*
     * TODO
     *
     * Seleccionar els guies d'un destí
     * 
     * Sentència select de la taula guia
     * Columnes: totes
     * Files: totes les que el seu codi de destí coincideixi amb el donat per paràmetre
     *
     */
    private static String selGuiesSQL = "SELECT * FROM GUIES WHERE DESTI=?";

    private PreparedStatement selGuiesSt;

    /*
     *TODO
     * 
     *Paràmetres: cap
     *
     *Acció:
     *  - Heu d'establir la connexio JDBC amb la base de dades EAC111819S1
     *  - Heu de crear els objectes PrepareStatement declarats com a atributs d'aquesta classe
     *    amb els respectius SQL declarats com a atributs just sobre cadascun d'ells.
     *  - Heu de fer el catch de les possibles excepcions (en aquest mètode no llançarem GestioExcursionsExcepcio,
     *    simplement, mostreu el missatge per consola de l'excepció capturada)
     *
     *Retorn: cap
     *
     */
    public void estableixConnexio() throws SQLException {
        try {
            Class.forName(DRIVER_DERBY).newInstance();
            //establir la connexió, user and password definits a la URL
            conn = DriverManager.getConnection(DB_URL);
            //Objectes PrepareStatement Destí
            codiDestiSt = conn.prepareStatement(codiDestiSQL);//falla
            insereixDestiSt = conn.prepareStatement(insereixDestiSQL);
            actualitzaDestiSt = conn.prepareStatement(actualitzaDestiSQL);

            //Objectes PrepareStatement Guies
            eliminaGuiesSt = conn.prepareStatement(eliminaGuiesSQL);
            insereixGuiaSt = conn.prepareStatement(insereixGuiaSQL);
            selGuiesSt = conn.prepareStatement(selGuiesSQL);

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tanca la connexió i posa la referència de la connexió a null.
     *
     * @throws SQLException
     */
    public void tancaConnexio() throws SQLException {
        try {
            conn.close();
        } finally {
            conn = null;
        }
    }

    /*
     *TODO
     * 
     *Paràmetres: el nom del fitxer i el destí a desar
     *
     *Acció:
     *  - Heu de desar el destí sobre la base de dades:
     *  - El destí s'ha de desar a la taula destins (nomFitxer conté el codi del destí)
     *  - Cada guia del destí, s'ha de desar com registre de la taula guies
     *  - Heu de tenir en compte que si el destí ja existia amb aquest codi, llavors heu de fer el següent:
     *     - Actualitzar el registre destí ja existent
     *     - Eliminar tots els guies d'aquest destí de la taula guies i després inserir els nous com si hagués estat
     *       un destí nou.
     *  - Si al fer qualsevol operació es dona una excepció, llavors heu de llançar l'excepció GestioExcursionsExcepcio amb codi "GestorJDBC.desar"
     *
     *Retorn: cap
     *
     */
    @Override
    public void desarDesti(String nomFitxer, Desti desti) throws GestioExcursionsExcepcio {
        int fitxerNum = Integer.valueOf(nomFitxer);
        try {
            estableixConnexio();
            //comprobar si existeix el codi
            codiDestiSt.setInt(1, fitxerNum);
            ResultSet rs = codiDestiSt.executeQuery();

            if (rs.next()) {//UPDATE DESTINS SET NOM=?, CONTINENT=? WHERE CODI=?
                actualitzaDestiSt.setString(1, desti.getNom());
                actualitzaDestiSt.setString(2, desti.getContinent());
                actualitzaDestiSt.setInt(3, fitxerNum);
                actualitzaDestiSt.execute();
                actualitzaDestiSt.close();
                //Eliminar tots els guies d'aquest destí de la taula guies i 
                //després inserir els nous com si hagués estat
                //un destí nou.
                //"DELETE FROM GUIES WHERE DESTI=?"
                eliminaGuiesSt.setInt(1, fitxerNum);
                eliminaGuiesSt.execute();
                eliminaGuiesSt.close();
                if (desti.getComponents().size() > 0) {
                    for (Component c : desti.getComponents()) {
                        if (c instanceof Guia) {//nomès hi han guies, però ja queda implemetat
                            Guia guia = (Guia) c;//INSERT INTO GUIES VALUES (?, ?, ?, ?, ?)
                            insereixGuiaSt.setString(1, guia.getCodi());
                            insereixGuiaSt.setString(2, guia.getNom());
                            insereixGuiaSt.setString(3, guia.getAdreca());
                            insereixGuiaSt.setString(4, guia.getTelefon());
                            insereixGuiaSt.setInt(5, desti.getCodi());
                            insereixGuiaSt.execute();
                        }
                    }
                    insereixGuiaSt.close();
                }
            } else {
                insereixDestiSt.setInt(1, fitxerNum);
                insereixDestiSt.setString(2, desti.getNom());
                insereixDestiSt.setString(3, desti.getContinent());
                insereixDestiSt.execute(); //INSERT INTO DESTINS VALUES (?, ?, ?)
                insereixDestiSt.close();
                //afegir guies si n'hi han a la llista
                if (desti.getComponents().size() > 0) {
                    for (Component c : desti.getComponents()) {
                        if (c instanceof Guia) {
                            Guia guia = (Guia) c;
                            insereixGuiaSt.setString(1, guia.getCodi());
                            insereixGuiaSt.setString(2, guia.getNom());
                            insereixGuiaSt.setString(3, guia.getAdreca());
                            insereixGuiaSt.setString(4, guia.getTelefon());
                            insereixGuiaSt.setInt(5, desti.getCodi());
                            insereixGuiaSt.execute();
                        }
                    }
                    insereixGuiaSt.close();
                }
            }
            tancaConnexio();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new GestioExcursionsExcepcio("GestorJDBC.desar");
        }
    }

    /*
     *TODO
     * 
     *Paràmetres: el nom del fitxer del destí
     *
     *Acció:
     *  - Heu de carregar el destí des de la base de dades (nomFitxer és el codi del destí)
     *  - Per fer això, heu de cercar el registre destí de la taula amb codi = nomFitxer
     *  - A més, heu d'afegir els guies al destí a partir de la taula guies
     *  - Si al fer qualsevol operació es dona una excepció, llavors heu de llançar l'excepció GestioExcursionsExcepcio 
     *    amb codi "GestorJDBC.carregar"
     *  - Si el nomFitxer donat no existeix a la taula destins (és a dir, el codi = nomFitxer no existeix), llavors
     *    heu de llançar l'excepció GestioExcursionsExcepcio amb codi "GestorJDBC.noexist"
     *
     *Retorn: cap
     *
     */
    @Override
    public void carregarDesti(String nomFitxer) throws GestioExcursionsExcepcio {
        int numFitxer = Integer.valueOf(nomFitxer);
        
        try {
            estableixConnexio();
            //SELECT * FROM DESTINS WHERE CODI = ?
            codiDestiSt.setInt(1, numFitxer);
            ResultSet rs = codiDestiSt.executeQuery();
            if (rs.next()) {
                int codi = rs.getInt("CODI");
                String nom = rs.getString("NOM");
                String continent = rs.getString("CONTINENT");
                desti = new Desti (nom,continent,codi);
                //"SELECT * FROM GUIES WHERE DESTI=?"
                selGuiesSt.setInt(1, numFitxer);
                ResultSet rsg =  selGuiesSt.executeQuery();
                while(rsg.next()){
                    String codiG = rsg.getString("CODI");
                    String nomG = rsg.getString("NOM");
                    String adrecaG = rsg.getString("ADRECA");
                    String telefonG = rsg.getString("TELEFON");
                    Guia g = new Guia(codiG, nomG, adrecaG, telefonG);
                    //afegir els guies al destíActual
                    ControladorPrincipal.getDestiActual().addGuia(g);
                }
            } else {//en cas de no existir el destí llençar excepció
                throw new GestioExcursionsExcepcio("GestorJDBC.noexist");
            }
            //tancar-ho tot
            codiDestiSt.close();
            selGuiesSt.close();
            tancaConnexio();
        } catch (SQLException e) {
            throw new GestioExcursionsExcepcio("GestorJDBC.carregar");
        }
    }
    

}
