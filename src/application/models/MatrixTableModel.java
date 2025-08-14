package application.models;

import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class MatrixTableModel extends AbstractTableModel {

    DecimalFormat df2 = new DecimalFormat("0.000");
    private List<String> columnNames;
    private List<List<Object>> data;

    public MatrixTableModel(List<String> columnNames, List<List<Object>> data) {
        this.columnNames = columnNames;
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (getRowCount() > 0) {
            return getValueAt(0, columnIndex).getClass();
        } else {
            return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; // Set to true if you want cells to be editable
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        data.get(rowIndex).set(columnIndex, aValue);
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void clearData(List<String> newColumnNames) {
        if(newColumnNames != null){
            this.columnNames = newColumnNames;   
        }
        data.clear();
        fireTableStructureChanged();
        fireTableDataChanged();
    }
}
