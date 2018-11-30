package persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;
import principal.GestioExcursionsExcepcio;
import model.Desti;

/**
 *
 * @author FTA
 */
public class GestorSerial implements ProveedorPersistencia{
    
    private Desti desti;

    public Desti getDesti() {
        return desti;
    }

    public void setDesti(Desti desti) {
        this.desti = desti;
    }

    @Override
    public void desarDesti(String nomFitxer, Desti desti) throws GestioExcursionsExcepcio {
        /*
         *TODO
         *
         *Paràmetres: nom del fitxer i desti
         *
         *Acció:
         * - Ha de desar l'objecte Desti serialitzat sobre un fitxer del sistema 
         *   operatiu amb nom nomFitxer i extensió ".ser".
         * - Heu de controlar excepcions d'entrada/sortida i en cas de produïrse alguna, 
         *   llavors llançar GestioExcursionsExcepcio amb codi GestorSerial.desar 
         *
         *Nota: podeu comprovar que la classe Desti implementa Serializable  
         *
         *Retorn: cap
         */
        
        try(ObjectOutputStream oos =new ObjectOutputStream(new FileOutputStream(new File(nomFitxer + ".ser")))) {
            oos.writeObject(desti);
        } catch (IOException ex) {
            throw new GestioExcursionsExcepcio("GestorSerial.desar");
        }

    }

    @Override
    public void carregarDesti(String nomFitxer) throws GestioExcursionsExcepcio {
        /*
         *TODO
         *
         *Paràmetres: nom del fitxer
         *
         *Acció:
         * - Ha de carregar el fitxer del sistema operatiu amb nom nomFitxer i extensió 
         *   ".ser" sobre un nou objecte Desti que es retornarà com a resultat.               
         * - Heu de controlar excepcions d'entrada/sortida i en cas de produïrse alguna, 
         *   llavors llançar GestioExcursionsExcepcio amb codi GestorSerial.carrega 
         *
         *Retorn: cap
         */
        
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(nomFitxer + ".ser")))) {
            desti = (Desti) ois.readObject();
        } catch (IOException ex) {
            throw new GestioExcursionsExcepcio("GestorSerial.carregar");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error de classe: " + ex.getMessage());
        }

    }
}