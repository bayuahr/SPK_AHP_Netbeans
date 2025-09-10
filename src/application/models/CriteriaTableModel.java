package application.models;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class CriteriaTableModel extends AbstractTableModel {

    private List<String> criteriaNames;
    private double[][] matrix;

    public CriteriaTableModel(List<String> criteriaNames, List<CriteriaBobotModel> comparisons) {
        this.criteriaNames = criteriaNames;

        int n = criteriaNames.size();
        matrix = new double[n][n];

        // isi diagonal dengan 1
        for (int i = 0; i < n; i++) {
            matrix[i][i] = 1.0;
        }

        // isi matriks dari daftar perbandingan
        for (CriteriaBobotModel cmp : comparisons) {
            int i = criteriaNames.indexOf(cmp.getK1());
            int j = criteriaNames.indexOf(cmp.getK2());
            if (i >= 0 && j >= 0) {
                matrix[i][j] = cmp.getBobot();
                matrix[j][i] = 1.0 / cmp.getBobot(); // sifat timbal balik
            }
        }
    }

    @Override
    public int getRowCount() {
        return criteriaNames.size();
    }

    @Override
    public int getColumnCount() {
        return criteriaNames.size() + 1; // kolom pertama untuk nama baris
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "Kriteria";
        }
        return criteriaNames.get(column - 1);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return criteriaNames.get(rowIndex); // nama kriteria pada baris
        }
        return matrix[rowIndex][columnIndex - 1];
    }
}
