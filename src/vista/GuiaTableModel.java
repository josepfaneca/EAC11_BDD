package vista;

import controlador.ControladorPrincipal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import model.Guia;
import principal.Component;
import principal.GestioExcursionsExcepcio;

/**
 *
 * @author fta
 */
public class GuiaTableModel extends AbstractTableModel {

    private final String[] columnNames = {"Codi", "Nom", "Adreça", "Telefon"};

    String[][] dada = new String[ControladorPrincipal.getDestiActual().getComponents().size()][4];

    public GuiaTableModel() {
        int i = 0;
        for (Component component : ControladorPrincipal.getDestiActual().getComponents()) {
            
            if (component instanceof Guia) {

                dada[i][0] = ((Guia) component).getCodi();
                dada[i][1] = ((Guia) component).getNom();
                dada[i][2] = ((Guia) component).getAdreca();
                dada[i][3] = ((Guia) component).getTelefon();

                i++;
            }
        }
    }

    @Override
    public int getRowCount() {
        return dada.length;
    }

    @Override
    public int getColumnCount() {
        return dada[0].length;
    }
    

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int column) {
        return dada[row][column];
    }

}
