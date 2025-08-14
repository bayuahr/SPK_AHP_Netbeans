package application.models;

import application.dao.CriteriaDao;
import application.daoimpl.CriteriaDaoImpl;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class AlternativeWeight2TableModel extends AbstractTableModel {

    List<AlternativeWeight2Model> alternatives;
    DecimalFormat df2 = new DecimalFormat("0.000");

    private final CriteriaDao criteriaDao;
    private String[] criteriaNames;

    public AlternativeWeight2TableModel(List<AlternativeWeight2Model> alternatives) {
        this.alternatives = alternatives;
        this.criteriaDao = new CriteriaDaoImpl();
        loadCriteriaNames();
    }

    private void loadCriteriaNames() {
        criteriaNames = new String[4];
        // Default names if no data found
        criteriaNames[0] = "Kriteria 1";
        criteriaNames[1] = "Kriteria 2";
        criteriaNames[2] = "Kriteria 3";
        criteriaNames[3] = "Kriteria 4";

        try {
            List<CriteriaModel> criterias = criteriaDao.findAll();

            // Map criteria names based on their codes K1-K4
            for (CriteriaModel criteria : criterias) {
                String code = criteria.getCode();
                String name = criteria.getName();

                switch (code) {
                    case "K1":
                        criteriaNames[0] = name;
                        break;
                    case "K2":
                        criteriaNames[1] = name;
                        break;
                    case "K3":
                        criteriaNames[2] = name;
                        break;
                    case "K4":
                        criteriaNames[3] = name;
                        break;
                }
            }
        } catch (Exception e) {
            // If error occurs, keep default names
            System.out.println("Error loading criteria names: " + e.getMessage());
        }
    }

    @Override
    public int getRowCount() {
        return alternatives.size();
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID";
            case 1:
                return "Alternatif";
            case 2:
                return criteriaNames[0]; // K1
            case 3:
                return criteriaNames[1]; // K2
            case 4:
                return criteriaNames[2]; // K3
            case 5:
                return criteriaNames[3]; // K4
            case 6:
                return "Bobot";
            default:
                return null;
        }
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int row, int column) {
        switch (column) {
            case 0:
                return alternatives.get(row).getId();
            case 1:
                return alternatives.get(row).getName();
            case 2:
                return df2.format(alternatives.get(row).getKriteria1Score());
            case 3:
                return df2.format(alternatives.get(row).getKriteria2Score());
            case 4:
                return df2.format(alternatives.get(row).getKriteria3Score());
            case 5:
                return df2.format(alternatives.get(row).getKriteria4Score());
            case 6:
                return df2.format(alternatives.get(row).getWeight());
            default:
                return null;
        }
    }

    public void clearData() {
        alternatives.clear();
        fireTableDataChanged();
    }

    // Method to refresh criteria names if needed
    public void refreshCriteriaNames() {
        loadCriteriaNames();
        fireTableStructureChanged();
    }
}
