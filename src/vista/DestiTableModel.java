package vista;

import controlador.ControladorPrincipal;
import javax.swing.table.AbstractTableModel;
import model.Desti;

/**
 *
 * @author FTA
 */
public class DestiTableModel extends AbstractTableModel{
    
    private final String[] columnNames = {"Codi", "Nom", "Continent"};

    String[][] data = new String[ControladorPrincipal.getMAXDESTINS()][3];

    public DestiTableModel() {
        int i = 0;
        for (Desti desti : ControladorPrincipal.getDestins()) {
            if (desti != null) {
                data[i][0] = String.valueOf(desti.getCodi());
                data[i][1] = desti.getNom();
                data[i][2] = desti.getContinent();
                i++;
            }
        }
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return data[0].length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int column) {
        return data[row][column];
    }
    
}
