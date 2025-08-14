package application.models;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.table.AbstractTableModel;

public class KaryawanTableModel extends AbstractTableModel {
    private final List<KaryawanModel> karyawan;
    private final NumberFormat rupiahFormat;

    public KaryawanTableModel(List<KaryawanModel> karyawan) {
        this.karyawan = karyawan;
        this.rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    }

    @Override
    public int getRowCount() {
        return karyawan.size();
    }

    @Override
    public int getColumnCount() {
        return 4; 
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "No.";
            case 1:
                return "Nama Karyawan";
            case 2:
                return "Jenis Kelamin";
            case 3:
                return "Posisi";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int row, int column) {
        KaryawanModel k = karyawan.get(row);
        switch (column) {
            case 0:
                return String.valueOf(k.getId());// Nomor baris (mulai dari 1)
            case 1:
                return k.getNama();
            case 2:
                return k.getJenis_kelamin();
            case 3:
                return k.getPosisi();
            default:
                return null;
        }
    }
}
