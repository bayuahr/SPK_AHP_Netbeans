package application.models;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class EvaluationTableModel extends AbstractTableModel {

    private final List<EvaluationModel> evaluations;

    public EvaluationTableModel(List<EvaluationModel> evaluations) {
        this.evaluations = evaluations;
    }

    @Override
    public int getRowCount() {
        return evaluations.size();
    }

    @Override
    public int getColumnCount() {
        return 7; // No, Nama Seleksi, Produk, Admin Id, Tanggal Dibuat
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 1:
                return "Nama Karyawan";
            case 0:
                return "Kode Karyawan";
            case 2:
                return "Admin Id";
            case 5:
                return "Dibuat Pada";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int row, int column) {
        EvaluationModel sel = evaluations.get(row);
        switch (column) {
            case 0:
                return row + 1; // Nomor urut baris
            case 1:
                return sel.getId();
            case 2:
                return sel.getKaryawan().getNama() != null ? sel.getKaryawan().getNama() : "-";
            case 3:
                return String.valueOf(sel.getKaryawan_id()) != null ? sel.getKaryawan_id() : "-";
            case 4:
                return sel.getAdminId();
            case 5:
                return sel.getCreatedAt();
            default:
                return null;
        }
    }
}
