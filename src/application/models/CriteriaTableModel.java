package application.models;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class CriteriaTableModel extends AbstractTableModel {
    List<CriteriaModel> criteria;
    
    public CriteriaTableModel(List<CriteriaModel> criteria) {
        this.criteria = criteria;
    }

    @Override
    public int getRowCount() {
        return criteria.size();
    }
    
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "KD Kriteria";
            case 1:
                return "Nama Kriteria";
            case 2:
                return "Prioritas Kepentingan";
            default:
                return null;
        }
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int row, int column) {
        switch (column) {
            case 0:
                return criteria.get(row).getCode();
            case 1:
                return criteria.get(row).getName();
            case 2:
                return criteria.get(row).getPriority();
            default:
                return null;
        }
    }
}