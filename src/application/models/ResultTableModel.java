package application.models;

import java.text.NumberFormat;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Locale;

public class ResultTableModel extends AbstractTableModel {

    private final List<ResultModel> results;
    private final NumberFormat rupiahFormat;

    public ResultTableModel(List<ResultModel> results) {
        this.results = results;
        this.rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    }

    @Override
    public int getRowCount() {
        return results.size();
    }

    @Override
    public int getColumnCount() {
        return 5; // Alternative, Score, Rank
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "No";
            case 1:
                return "Alternatif";
            case 2:
                return "Skor";
            case 3:
                return "Peringkat";
            default:
                return null;
        }

    }

    @Override
    public Object getValueAt(int row, int column) {
        ResultModel result = results.get(row);
        switch (column) {
            case 0:
                return row + 1;
            case 1:
                return result.getAlternative() != null ? result.getAlternative().getK().getNama() : "-";
            case 2:
                return result.getScore();
            case 3:
                return result.getRank() != null ? result.getRank() : "-";

            default:
                return null;
        }
    }
}
