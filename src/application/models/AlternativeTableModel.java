package application.models;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.table.AbstractTableModel;

public class AlternativeTableModel extends AbstractTableModel {

    private final List<AlternativeModel> alternatives;
    private final List<CriteriaModel> criteriaList;
    private final NumberFormat rupiahFormat;

    public AlternativeTableModel(List<AlternativeModel> alternatives, List<CriteriaModel> criteriaList) {
        this.alternatives = alternatives;
        this.criteriaList = criteriaList;
        this.rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    }

    @Override
    public int getRowCount() {
        return alternatives.size();
    }

    @Override
    public int getColumnCount() {
        return 2 + criteriaList.size(); // No, Nilai Tarif, Deskripsi, Produk + skor dinamis
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "No.";
        }
        if (column == 1) {
            return "Nama Karyawan";
        }
        int criteriaIndex = column - 2;
        if (criteriaIndex >= 0 && criteriaIndex < criteriaList.size()) {
            return criteriaList.get(criteriaIndex).getName();
        }
        return null;
    }

    @Override
    public Object getValueAt(int row, int column) {
        AlternativeModel alt = alternatives.get(row);
        if (column == 0) {
            return alt.getId();
        }
        if (column == 1) {
            return alt.getK().getNama();
        }
        // Kolom skor dinamis
        int criteriaIndex = column - 2;
        if (criteriaIndex >= 0 && criteriaIndex < criteriaList.size()) {
            String criteriaCode = criteriaList.get(criteriaIndex).getCode().toLowerCase();
            switch (criteriaCode) {
                case "k1":
                    return alt.getK1Score();
                case "k2":
                    return alt.getK2Score();
                case "k3":
                    return alt.getK3Score();
                case "k4":
                    return alt.getK4Score();
                default:
                    return "-"; // Untuk future proof kalau ada kode kriteria di luar k1-k4
            }
        }

        return null;
    }
}
