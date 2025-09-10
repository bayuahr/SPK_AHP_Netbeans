package application.views;

import application.dao.AlternativeDao;
import application.dao.CriteriaDao;
import application.dao.EvaluationDao;
import application.dao.ResultDao;
import application.daoimpl.AlternativeDaoImpl;
import application.daoimpl.CriteriaDaoImpl;
import application.daoimpl.EvaluationDaoImpl;
import application.daoimpl.KaryawanDaoImpl;
import application.daoimpl.ResultDaoImpl;
import application.models.AlternativeModel;
import application.models.AlternativeWeight2Model;
import application.models.AlternativeWeight2TableModel;
import application.models.CriteriaModel;
import application.models.EvaluationModel;
import application.models.MatrixTableModel;
import application.models.KaryawanModel;
import application.models.ResultModel;
import application.utils.AHPCalculation;
import java.awt.Color;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import application.dao.KaryawanDao;

/**
 *
 * @author mahasiswa unindra
 */
public class CalculationDialog extends javax.swing.JDialog {

    protected AHPCalculation ahpCalculation = new AHPCalculation();
    DecimalFormat df = new DecimalFormat("0.000");
    DecimalFormat df2 = new DecimalFormat("0.000");
    private final AlternativeDao alternativeDao;
    private final KaryawanDao productDao;
    private final CriteriaDao criteriaDao;
    private final EvaluationDao evaluationDao;
    private final ResultDao resultDao;
    private final NumberFormat rupiahFormat;

    private List<AlternativeWeight2Model> alternativeWeightModel = null;
    private final List<List<Object>> matrixComparisonKriteria1 = new ArrayList<>();
    private final List<List<Object>> matrixComparisonKriteria2 = new ArrayList<>();
    private final List<List<Object>> matrixComparisonKriteria3 = new ArrayList<>();
    private final List<List<Object>> matrixComparisonKriteria4 = new ArrayList<>();

    private final List<List<Object>> matrixComparisonNormalizeKriteria1 = new ArrayList<>();
    private final List<List<Object>> matrixComparisonNormalizeKriteria2 = new ArrayList<>();
    private final List<List<Object>> matrixComparisonNormalizeKriteria3 = new ArrayList<>();
    private final List<List<Object>> matrixComparisonNormalizeKriteria4 = new ArrayList<>();
    private int totalCandidates = 0;
    private List<AlternativeModel> alternativesFound;
    private List<String> defaultColumnNames = new ArrayList<>();
    private List<String> defaultColumnNamesTableComparisonNormalize = new ArrayList<>();
    private final Map<String, Integer> productMap = new HashMap<>();

    /**
     * Creates new form AHPCalculationDialog
     */
    public CalculationDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setLocationRelativeTo(null);

        // add Panel, add panel(sebuah panel)
        Pane.add(PanelPerhitungan);
        Pane.repaint();
        Pane.revalidate();

        alternativeDao = new AlternativeDaoImpl();
        productDao = new KaryawanDaoImpl();
        criteriaDao = new CriteriaDaoImpl();
        evaluationDao = new EvaluationDaoImpl();
        resultDao = new ResultDaoImpl();
        this.rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        mulaiHitung.setBackground(Color.white);
        Simpan.setBackground(Color.white);
        reset.setBackground(Color.white);

        List<AlternativeWeight2Model> alternative = new ArrayList<>();

        AlternativeWeight2TableModel alternativeWeightTableModel = new AlternativeWeight2TableModel(alternative);

        jTable1.setModel(alternativeWeightTableModel);

        this.defaultColumnNames.add("Nama");
        this.defaultColumnNames.add("A1");
        this.defaultColumnNames.add("A2");
        this.defaultColumnNames.add("A3");

        List<List<Object>> data = new ArrayList<>();

        MatrixTableModel matrixTableModel = new MatrixTableModel(defaultColumnNames, data);
        jTableMatrixComparisonKriteria1.setModel(matrixTableModel);

        MatrixTableModel matrixTableModel2 = new MatrixTableModel(defaultColumnNames, data);
        jTableMatrixComparisonKriteria2.setModel(matrixTableModel2);

        MatrixTableModel matrixTableModel3 = new MatrixTableModel(defaultColumnNames, data);
        jTableMatrixComparisonKriteria3.setModel(matrixTableModel3);

        MatrixTableModel matrixTableModel4 = new MatrixTableModel(defaultColumnNames, data);
        jTableMatrixComparisonKriteria4.setModel(matrixTableModel4);

        defaultColumnNamesTableComparisonNormalize.add("Nama");
        defaultColumnNamesTableComparisonNormalize.add("A1");
        defaultColumnNamesTableComparisonNormalize.add("A2");
        defaultColumnNamesTableComparisonNormalize.add("A3");
        defaultColumnNamesTableComparisonNormalize.add("Bobot");

        MatrixTableModel matrixTableModel5 = new MatrixTableModel(defaultColumnNamesTableComparisonNormalize, data);
        jTableMatrixComparisonNormalizeKriteria1.setModel(matrixTableModel5);

        MatrixTableModel matrixTableModel6 = new MatrixTableModel(defaultColumnNamesTableComparisonNormalize, data);
        jTableMatrixComparisonNormalizeKriteria2.setModel(matrixTableModel6);

        MatrixTableModel matrixTableModel7 = new MatrixTableModel(defaultColumnNamesTableComparisonNormalize, data);
        jTableMatrixComparisonNormalizeKriteria3.setModel(matrixTableModel7);

        MatrixTableModel matrixTableModel8 = new MatrixTableModel(defaultColumnNamesTableComparisonNormalize, data);
        jTableMatrixComparisonNormalizeKriteria4.setModel(matrixTableModel8);

        loadData();
    }

    private void loadData() {
        try {
            final List<KaryawanModel> products = productDao.findAll();
            final List<CriteriaModel> criterias = criteriaDao.findAll();

            // Clear existing items and add default option
            jComboBoxProduct.removeAllItems();
            productMap.clear();

            // Always add default option first
            jComboBoxProduct.addItem("Pilih Karyawan -");

            // Populate cbProduct with products
            for (KaryawanModel product : products) {
                String displayText = product.getNama();
                jComboBoxProduct.addItem(displayText);
                productMap.put(displayText, product.getId());
            }
            List<String> listCriteria = new ArrayList<String>();
            // Update criteria labels based on criterias list
            // Map criteria labels based on criteria code
            for (CriteriaModel criteria : criterias) {
                String code = criteria.getCode(); // Assuming CriteriaModel has getCode() method
                String name = criteria.getName(); // Assuming CriteriaModel has getName() method
                listCriteria.add(criteria.getCode() + " - " + criteria.getName());
                String title = "Kriteria " + criteria.getName();

                switch (criteria.getCode()) {
                    case "K1":
                        jLabelComparisonKriteria1.setText(title);
                        jLabelWeightKriteria1.setText(title);
                        break;
                    case "K2":
                        jLabelComparisonKriteria2.setText(title);
                        jLabelWeightKriteria2.setText(title);
                        break;
                    case "K3":
                        jLabelComparisonKriteria3.setText(title);
                        jLabelWeightKriteria3.setText(title);
                        break;
                    case "K4":
                        jLabelComparisonKriteria4.setText(title);
                        jLabelWeightKriteria4.setText(title);
                        break;
                    default:
                        throw new AssertionError();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    public void loadTable(List<AlternativeWeight2Model> list) {
        AlternativeWeight2TableModel alternativeWeightTableModel = new AlternativeWeight2TableModel(list);

        jTable1.setModel(alternativeWeightTableModel);

        // TableRowSorter<TableModel> sorter = new
        // TableRowSorter<TableModel>(jTable1.getModel());
        // jTable1.setRowSorter(sorter);
        //
        // List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
        // sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        // sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        // sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
        // sorter.setSortKeys(sortKeys);
        List<String> columnNames = new ArrayList<>();
        columnNames.add("Nama");

        // if (this.totalCandidates > 3) {
        // this.totalCandidates = 3;
        // }
        MatrixTableModel matrixTableModel1 = new MatrixTableModel(columnNames, this.matrixComparisonKriteria1);
        jTableMatrixComparisonKriteria1.setModel(matrixTableModel1);

        MatrixTableModel matrixTableModel2 = new MatrixTableModel(columnNames, this.matrixComparisonKriteria2);
        jTableMatrixComparisonKriteria2.setModel(matrixTableModel2);

        MatrixTableModel matrixTableModel3 = new MatrixTableModel(columnNames, this.matrixComparisonKriteria3);
        jTableMatrixComparisonKriteria3.setModel(matrixTableModel3);

        MatrixTableModel matrixTableModel4 = new MatrixTableModel(columnNames, this.matrixComparisonKriteria4);
        jTableMatrixComparisonKriteria4.setModel(matrixTableModel4);

        List<String> columnNamesTableComparisonNormalize = new ArrayList<>();
        columnNamesTableComparisonNormalize.add("Nama");

        columnNamesTableComparisonNormalize.add("Bobot");

        MatrixTableModel matrixTableModel5 = new MatrixTableModel(columnNamesTableComparisonNormalize,
                this.matrixComparisonNormalizeKriteria1);
        jTableMatrixComparisonNormalizeKriteria1.setModel(matrixTableModel5);

        MatrixTableModel matrixTableModel6 = new MatrixTableModel(columnNamesTableComparisonNormalize,
                this.matrixComparisonNormalizeKriteria2);
        jTableMatrixComparisonNormalizeKriteria2.setModel(matrixTableModel6);

        MatrixTableModel matrixTableModel7 = new MatrixTableModel(columnNamesTableComparisonNormalize,
                this.matrixComparisonNormalizeKriteria3);
        jTableMatrixComparisonNormalizeKriteria3.setModel(matrixTableModel7);

        MatrixTableModel matrixTableModel8 = new MatrixTableModel(columnNamesTableComparisonNormalize,
                this.matrixComparisonNormalizeKriteria4);
        jTableMatrixComparisonNormalizeKriteria4.setModel(matrixTableModel8);
    }

    private void calculateComparisonCriteriaMatrix() {
        try {
            matrixComparisonKriteria1.clear();
            matrixComparisonKriteria2.clear();
            matrixComparisonKriteria3.clear();
            matrixComparisonKriteria4.clear();

            matrixComparisonNormalizeKriteria1.clear();
            matrixComparisonNormalizeKriteria2.clear();
            matrixComparisonNormalizeKriteria3.clear();
            matrixComparisonNormalizeKriteria4.clear();

            List<AlternativeModel> candidatesFound = this.alternativesFound;

            int size = candidatesFound.size();

            this.totalCandidates = size;

            Object[][] alternativeWeight = new Object[size][7];

            int[][] criterias = new int[4][size];

            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < size; col++) {
                    switch (row) {
                        case 0:
                            criterias[row][col] = candidatesFound.get(col).getK1Score();
                            break;
                        case 1:
                            criterias[row][col] = candidatesFound.get(col).getK2Score();
                            break;
                        case 2:
                            criterias[row][col] = candidatesFound.get(col).getK3Score();
                            break;
                        default:
                            criterias[row][col] = candidatesFound.get(col).getK4Score();
                            break;
                    }
                }
            }

            System.out.println("\n[#" + "LOOP ALTERNATIF TERHADAP MASING-MASING KRITERIA" + "]");
            for (int criteria = 0; criteria < criterias.length; criteria++) {

                int[] scores = criterias[criteria];

                double[][] alternativeScoreMatrix = new double[size][size];

                double[] totalColumnAlternativeScoreMatrix = new double[size];

                // Mengisi matriks perbandingan pasangan
                System.out.println("=== Perhitungan Matriks Perbandingan Alternatif ===");

                for (int row = 0; row < size; row++) {
                    for (int col = 0; col < size; col++) {
                        alternativeScoreMatrix[row][col] = (double) scores[row] / scores[col];
                        totalColumnAlternativeScoreMatrix[col] += alternativeScoreMatrix[row][col];

                        // Log per elemen matriks
                        System.out.printf("M[%d][%d] = %.4f (%.2f / %.2f)\n",
                                row, col, alternativeScoreMatrix[row][col], (double) scores[row], (double) scores[col]);
                    }
                }

                System.out.println("\n=== Total Kolom Matriks Perbandingan Alternatif ===");
                for (int col = 0; col < size; col++) {
                    System.out.printf("Total kolom %d: %.4f\n", col, totalColumnAlternativeScoreMatrix[col]);
                }

                for (int row = 0; row < size; row++) {
                    List<Object> row1 = new ArrayList<>();
                    for (int col = 0; col < size; col++) {
                        row1.add(df2.format(alternativeScoreMatrix[row][col]));
                    }
                    switch (criteria) {
                        case 0:
                            this.matrixComparisonKriteria1.add(row1);
                            break;
                        case 1:
                            this.matrixComparisonKriteria2.add(row1);
                            break;
                        case 2:
                            this.matrixComparisonKriteria3.add(row1);
                            break;
                        default:
                            this.matrixComparisonKriteria4.add(row1);
                            break;
                    }
                }

                System.out.println("size: " + this.matrixComparisonKriteria1.size());

                for (double num : totalColumnAlternativeScoreMatrix) {
                    System.out.print(num + " ");
                }

                printArray2D(alternativeScoreMatrix, "RES alternativeScoreMatrix");

                double[][] normalizedAlternativeScoreMatrix = new double[size][size];
                double[] normalizedAlternativeScoreMatrixSum = new double[size];
                double[] priorityVector = new double[size];

                // Calculate the normalized matrix and column sums
                for (int row = 0; row < alternativeScoreMatrix.length; row++) {
                    for (int col = 0; col < alternativeScoreMatrix.length; col++) {
                        normalizedAlternativeScoreMatrix[row][col] = alternativeScoreMatrix[row][col]
                                / totalColumnAlternativeScoreMatrix[col];
                        normalizedAlternativeScoreMatrixSum[row] += normalizedAlternativeScoreMatrix[row][col];
                        priorityVector[row] = normalizedAlternativeScoreMatrixSum[row] / size; // atau rata-rata
                    }
                }

                printArray2D(normalizedAlternativeScoreMatrix, "RES normalizedAlternativeScoreMatrix");

                for (double num : priorityVector) {
                    System.out.println(num + " === ");
                }

                for (int row = 0; row < size; row++) {
                    List<Object> row1 = new ArrayList<>();
                    for (int col = 0; col < size; col++) {
                        row1.add(df2.format(normalizedAlternativeScoreMatrix[row][col]));
                    }
                    row1.add(df2.format(priorityVector[row]));
                    switch (criteria) {
                        case 0:
                            this.matrixComparisonNormalizeKriteria1.add(row1);
                            break;
                        case 1:
                            this.matrixComparisonNormalizeKriteria2.add(row1);
                            break;
                        case 2:
                            this.matrixComparisonNormalizeKriteria3.add(row1);
                            break;
                        default:
                            this.matrixComparisonNormalizeKriteria4.add(row1);
                            break;
                    }
                }

                for (int i = 0; i < size; i++) {
                    if (criteria == 0) {
                        alternativeWeight[i][0] = candidatesFound.get(i).getId();
                    }

                    for (int p = 0; p < priorityVector.length; p++) {
                        switch (criteria) {
                            case 0:
                                alternativeWeight[p][2] = priorityVector[p];
                                break;
                            case 1:
                                alternativeWeight[p][3] = priorityVector[p];
                                break;
                            case 2:
                                alternativeWeight[p][4] = priorityVector[p];
                                break;
                            case 3:
                                alternativeWeight[p][5] = priorityVector[p];
                                break;
                            default:
                                break;
                        }
                    }

                }

            }

            double[] priorityCriteria = ahpCalculation.getPriorityVector();

            for (int row = 0; row < alternativeWeight.length; row++) {
                double[] scoreCriteria = new double[4];
                for (int col = 0; col < alternativeWeight[row].length; col++) {
                    if (col > 1 && col <= 5) {
                        scoreCriteria[col - 2] = (double) alternativeWeight[row][col];
                    }
                }
                alternativeWeight[row][6] = calculateScore(priorityCriteria, scoreCriteria);
                System.out.println();
            }

            System.out.println("alternativeWeight");
            for (int row = 0; row < alternativeWeight.length; row++) {
                double[] scoreCriteria = new double[4];
                for (int col = 0; col < alternativeWeight[row].length; col++) {
                    System.out.print(alternativeWeight[row][col] + " ");
                }
                System.out.println();
            }

            this.alternativeWeightModel = AlternativeWeight2Model.fromArray(alternativeWeight);

            loadTable(alternativeWeightModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public double calculateScore(double[] weights, double[] values) {
        double score = 0.0;
        for (int i = 0; i < weights.length; i++) {
            score += weights[i] * values[i];
        }
        return score;
    }

    public void printArray2D(double[][] array, String title) {
        System.out.println("\n[#" + title + "]");
        for (double[] array1 : array) {
            for (int j = 0; j < array1.length; j++) {
                System.out.print(array1[j] + " ");
            }
            System.out.println();
        }
    }

    // Add this helper method to update the alternative list
    private void updateAlternativeList(List<AlternativeModel> alternatives) {
        this.alternativesFound = alternatives;
        if (alternatives != null && !alternatives.isEmpty()) {
            String[] alternativeNames = new String[alternatives.size()];
            for (int i = 0; i < alternatives.size(); i++) {
                AlternativeModel alt = alternatives.get(i);
                alternativeNames[i] = (i + 1) + "). ";
            }
        }
    }

    // Add this helper method to clear calculation results
    private void clearCalculationResults() {
        // TODO add your handling code here:
        AlternativeWeight2TableModel model = (AlternativeWeight2TableModel) jTable1.getModel();
        model.clearData();

        MatrixTableModel model1 = (MatrixTableModel) jTableMatrixComparisonKriteria1.getModel();
        model1.clearData(this.defaultColumnNames);

        MatrixTableModel model2 = (MatrixTableModel) jTableMatrixComparisonKriteria2.getModel();
        model2.clearData(this.defaultColumnNames);

        MatrixTableModel model3 = (MatrixTableModel) jTableMatrixComparisonKriteria3.getModel();
        model3.clearData(this.defaultColumnNames);

        MatrixTableModel model4 = (MatrixTableModel) jTableMatrixComparisonKriteria4.getModel();
        model4.clearData(this.defaultColumnNames);

        MatrixTableModel model5 = (MatrixTableModel) jTableMatrixComparisonNormalizeKriteria1.getModel();
        model5.clearData(this.defaultColumnNamesTableComparisonNormalize);

        MatrixTableModel model6 = (MatrixTableModel) jTableMatrixComparisonNormalizeKriteria2.getModel();
        model6.clearData(this.defaultColumnNamesTableComparisonNormalize);

        MatrixTableModel model7 = (MatrixTableModel) jTableMatrixComparisonNormalizeKriteria3.getModel();
        model7.clearData(this.defaultColumnNamesTableComparisonNormalize);

        MatrixTableModel model8 = (MatrixTableModel) jTableMatrixComparisonNormalizeKriteria4.getModel();
        model8.clearData(this.defaultColumnNamesTableComparisonNormalize);

        // Optionally, clear the sorter
        // TableRowSorter<TableModel> sorter = new TableRowSorter<>(jTable1.getModel());
        // jTable1.setRowSorter(sorter);
        k1k1.setText("");
        k1k2.setText("");
        k1k3.setText("");
        k1k4.setText("");
        k2k1.setText("");
        k2k2.setText("");
        k2k3.setText("");
        k2k4.setText("");
        k3k1.setText("");
        k3k2.setText("");
        k3k3.setText("");
        k3k4.setText("");
        k4k1.setText("");
        k4k2.setText("");
        k4k3.setText("");
        k4k4.setText("");

        k1k1N.setText("");
        k1k2N.setText("");
        k1k3N.setText("");
        k1k4N.setText("");
        k2k1N.setText("");
        k2k2N.setText("");
        k2k3N.setText("");
        k2k4N.setText("");
        k3k1N.setText("");
        k3k2N.setText("");
        k3k3N.setText("");
        k3k4N.setText("");
        k4k1N.setText("");
        k4k2N.setText("");
        k4k3N.setText("");
        k4k4N.setText("");

        Prior1.setText("");
        Prior2.setText("");
        Prior3.setText("");
        Prior4.setText("");

        lambdaMax.setText("");
        ciValue1.setText("");
        nCriteria.setText("");

        ciValue.setText("");
        irValues.setText("");
        crValue.setText("");
        conclusion.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnG = new javax.swing.ButtonGroup();
        PanelPerhitungan = new javax.swing.JPanel();
        judul = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        mulaiHitung = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        k1k1 = new javax.swing.JTextField();
        k1k2 = new javax.swing.JTextField();
        k1k3 = new javax.swing.JTextField();
        k1k4 = new javax.swing.JTextField();
        k2k1 = new javax.swing.JTextField();
        k2k2 = new javax.swing.JTextField();
        k2k3 = new javax.swing.JTextField();
        k2k4 = new javax.swing.JTextField();
        k3k1 = new javax.swing.JTextField();
        k3k2 = new javax.swing.JTextField();
        k3k3 = new javax.swing.JTextField();
        k3k4 = new javax.swing.JTextField();
        k4k1 = new javax.swing.JTextField();
        k4k2 = new javax.swing.JTextField();
        k4k3 = new javax.swing.JTextField();
        k4k4 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        k1k1N = new javax.swing.JTextField();
        k1k2N = new javax.swing.JTextField();
        k1k3N = new javax.swing.JTextField();
        k1k4N = new javax.swing.JTextField();
        k2k1N = new javax.swing.JTextField();
        k2k2N = new javax.swing.JTextField();
        k2k3N = new javax.swing.JTextField();
        k2k4N = new javax.swing.JTextField();
        k3k1N = new javax.swing.JTextField();
        k3k2N = new javax.swing.JTextField();
        k3k3N = new javax.swing.JTextField();
        k3k4N = new javax.swing.JTextField();
        k4k1N = new javax.swing.JTextField();
        k4k2N = new javax.swing.JTextField();
        k4k3N = new javax.swing.JTextField();
        k4k4N = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        Prior1 = new javax.swing.JTextField();
        Prior2 = new javax.swing.JTextField();
        Prior3 = new javax.swing.JTextField();
        Prior4 = new javax.swing.JTextField();
        Simpan = new javax.swing.JButton();
        reset = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        irValues = new javax.swing.JTextField();
        conclusion = new javax.swing.JTextField();
        crValue = new javax.swing.JTextField();
        ciValue = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        nCriteria = new javax.swing.JTextField();
        ciValue1 = new javax.swing.JTextField();
        lambdaMax = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableMatrixComparisonKriteria4 = new javax.swing.JTable();
        jLabelComparisonKriteria2 = new javax.swing.JLabel();
        jLabelComparisonKriteria1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableMatrixComparisonKriteria1 = new javax.swing.JTable();
        jLabelComparisonKriteria3 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableMatrixComparisonKriteria2 = new javax.swing.JTable();
        jLabelComparisonKriteria4 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTableMatrixComparisonKriteria3 = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        jLabelWeightKriteria1 = new javax.swing.JLabel();
        jLabelWeightKriteria2 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTableMatrixComparisonNormalizeKriteria1 = new javax.swing.JTable();
        jLabelWeightKriteria3 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTableMatrixComparisonNormalizeKriteria3 = new javax.swing.JTable();
        jLabelWeightKriteria4 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTableMatrixComparisonNormalizeKriteria4 = new javax.swing.JTable();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTableMatrixComparisonNormalizeKriteria2 = new javax.swing.JTable();
        jLabel34 = new javax.swing.JLabel();
        jLabelSelectProduct = new javax.swing.JLabel();
        jComboBoxProduct = new javax.swing.JComboBox<>();
        Pane = new javax.swing.JPanel();

        PanelPerhitungan.setMinimumSize(new java.awt.Dimension(0, 0));
        PanelPerhitungan.setPreferredSize(new java.awt.Dimension(0, 0));

        judul.setBackground(new java.awt.Color(213, 235, 255));
        judul.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        judul.setForeground(new java.awt.Color(0, 120, 218));
        judul.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        judul.setText("Perhitungan Alternatif Menggunakan Metode AHP");
        judul.setOpaque(true);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(900, 900));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(900, 900));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 1000));
        jPanel1.setRequestFocusEnabled(false);

        mulaiHitung.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        mulaiHitung.setForeground(new java.awt.Color(0, 120, 218));
        mulaiHitung.setText("Mulai Hitung");
        mulaiHitung.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 210, 226)));
        mulaiHitung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mulaiHitungMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mulaiHitungMouseExited(evt);
            }
        });
        mulaiHitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mulaiHitungActionPerformed(evt);
            }
        });

        jLabel2.setText("1. Matriks Perbandingan Kriteria");

        jLabel3.setText("K1");

        jLabel4.setText("K2");

        jLabel5.setText("K3");

        jLabel6.setText("K4");

        k1k1.setEditable(false);
        k1k1.setBackground(new java.awt.Color(245, 247, 250));
        k1k1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k1k2.setEditable(false);
        k1k2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k1k3.setEditable(false);
        k1k3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k1k4.setEditable(false);
        k1k4.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k2k1.setEditable(false);
        k2k1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k2k2.setEditable(false);
        k2k2.setBackground(new java.awt.Color(245, 247, 250));
        k2k2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k2k3.setEditable(false);
        k2k3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k2k4.setEditable(false);
        k2k4.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k3k1.setEditable(false);
        k3k1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k3k2.setEditable(false);
        k3k2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k3k3.setEditable(false);
        k3k3.setBackground(new java.awt.Color(245, 247, 250));
        k3k3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k3k4.setEditable(false);
        k3k4.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k4k1.setEditable(false);
        k4k1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k4k2.setEditable(false);
        k4k2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k4k3.setEditable(false);
        k4k3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k4k4.setEditable(false);
        k4k4.setBackground(new java.awt.Color(245, 247, 250));
        k4k4.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel7.setText("K1");

        jLabel8.setText("K2");

        jLabel9.setText("K3");

        jLabel10.setText("K4");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(k2k1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(k2k2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(k3k1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(k3k2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(k4k1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(k4k2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(k1k1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(k1k2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(10, 10, 10)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(k1k3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(k2k3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(k3k3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(k4k3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(k1k4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(k2k4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(k3k4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(k4k4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(29, 29, 29)
                            .addComponent(jLabel7)
                            .addGap(40, 40, 40)
                            .addComponent(jLabel8)
                            .addGap(38, 38, 38)
                            .addComponent(jLabel9)
                            .addGap(42, 42, 42)
                            .addComponent(jLabel10)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(102, 102, 102)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(k1k1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(k2k1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(k3k1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(k4k1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(k1k3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(k1k2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(k2k3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(k2k2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(k3k3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(k3k2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(k4k3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(k4k2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(k1k4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(k2k4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(k3k4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(k4k4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jLabel11.setText("2. Matriks Normalisasi");

        jLabel12.setText("K1");

        jLabel13.setText("K2");

        jLabel14.setText("K3");

        jLabel15.setText("K4");

        k1k1N.setEditable(false);
        k1k1N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k1k2N.setEditable(false);
        k1k2N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k1k3N.setEditable(false);
        k1k3N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k1k4N.setEditable(false);
        k1k4N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k2k1N.setEditable(false);
        k2k1N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k2k2N.setEditable(false);
        k2k2N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k2k3N.setEditable(false);
        k2k3N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k2k4N.setEditable(false);
        k2k4N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k3k1N.setEditable(false);
        k3k1N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k3k2N.setEditable(false);
        k3k2N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k3k3N.setEditable(false);
        k3k3N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k3k4N.setEditable(false);
        k3k4N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k4k1N.setEditable(false);
        k4k1N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k4k2N.setEditable(false);
        k4k2N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k4k3N.setEditable(false);
        k4k3N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        k4k4N.setEditable(false);
        k4k4N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel16.setText("K1");

        jLabel17.setText("K2");

        jLabel18.setText("K3");

        jLabel19.setText("K4");

        jLabel20.setText("Prioritas");

        Prior1.setEditable(false);
        Prior1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        Prior2.setEditable(false);
        Prior2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        Prior3.setEditable(false);
        Prior3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        Prior4.setEditable(false);
        Prior4.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(k2k1N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(k2k2N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(k3k1N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(k3k2N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(k4k1N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(k4k2N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(k1k1N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(k1k2N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(k1k3N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(k2k3N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(k3k3N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(k4k3N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(k1k4N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(k2k4N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(k3k4N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(k4k4N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel16)
                                .addGap(40, 40, 40)
                                .addComponent(jLabel17)
                                .addGap(36, 36, 36)
                                .addComponent(jLabel18)
                                .addGap(40, 40, 40)
                                .addComponent(jLabel19)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Prior4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(Prior3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Prior1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Prior2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(k1k1N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(k2k1N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(k3k1N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(k4k1N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(k1k3N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(k1k2N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(k2k3N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(k2k2N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(k3k3N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(k3k2N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(k4k3N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(k4k2N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(k1k4N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Prior1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(k2k4N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Prior2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(k3k4N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Prior3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(k4k4N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Prior4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        Simpan.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Simpan.setForeground(new java.awt.Color(11, 91, 179));
        Simpan.setText("Simpan Data");
        Simpan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 210, 226)));
        Simpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SimpanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                SimpanMouseExited(evt);
            }
        });
        Simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SimpanActionPerformed(evt);
            }
        });

        reset.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        reset.setText("Reset");
        reset.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 210, 226)));
        reset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                resetMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                resetMouseExited(evt);
            }
        });
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        jLabel30.setText("4. Menghitung Nilai Consistency Ratio");

        jLabel40.setText("CR =  CI / IR");

        jLabel41.setText("Penjelasan: ");

        jLabel42.setText("CI  = Consistency Index");

        jLabel43.setText("IR = Index Ratio Consistency");

        jLabel44.setText("CR = Consistency Ratio");

        irValues.setEditable(false);
        irValues.setBackground(new java.awt.Color(245, 247, 250));
        irValues.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        irValues.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                irValuesActionPerformed(evt);
            }
        });

        conclusion.setEditable(false);
        conclusion.setBackground(new java.awt.Color(245, 247, 250));
        conclusion.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        crValue.setEditable(false);
        crValue.setBackground(new java.awt.Color(245, 247, 250));
        crValue.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        crValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crValueActionPerformed(evt);
            }
        });

        ciValue.setEditable(false);
        ciValue.setBackground(new java.awt.Color(245, 247, 250));
        ciValue.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ciValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ciValueActionPerformed(evt);
            }
        });

        jLabel45.setText("Kesimpulan");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addGap(18, 18, 18)
                        .addComponent(conclusion))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30)
                            .addComponent(jLabel40)
                            .addComponent(jLabel41))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42)
                            .addComponent(jLabel43)
                            .addComponent(jLabel44))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(crValue, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(irValues)
                            .addComponent(ciValue))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(ciValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(irValues, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(crValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(conclusion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel31.setText("3. Menghitung Nilai Index Consistency");

        jLabel46.setText("CI =  ( λ maks - n ) / ( n - 1 )");

        jLabel47.setText("Penjelasan: ");

        jLabel48.setText("λ maks = Nilai Eigen Vector");

        jLabel49.setText("n  = Jumlah Kriteria");

        jLabel50.setText("CI   = Consistency Index");

        nCriteria.setEditable(false);
        nCriteria.setBackground(new java.awt.Color(245, 247, 250));
        nCriteria.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        nCriteria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nCriteriaActionPerformed(evt);
            }
        });

        ciValue1.setEditable(false);
        ciValue1.setBackground(new java.awt.Color(245, 247, 250));
        ciValue1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ciValue1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ciValue1ActionPerformed(evt);
            }
        });

        lambdaMax.setEditable(false);
        lambdaMax.setBackground(new java.awt.Color(245, 247, 250));
        lambdaMax.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lambdaMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lambdaMaxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(jLabel46)
                            .addComponent(jLabel47))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel48)
                            .addComponent(jLabel49)
                            .addComponent(jLabel50))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ciValue1, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(nCriteria)
                            .addComponent(lambdaMax))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(lambdaMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(nCriteria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(ciValue1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setShowGrid(true);
        jScrollPane2.setViewportView(jTable1);

        jLabel21.setText("5. Matriks Perbandingan Alternatif terhadap Kriteria");

        jTableMatrixComparisonKriteria4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTableMatrixComparisonKriteria4);

        jLabelComparisonKriteria2.setText("Kriteria 2");

        jLabelComparisonKriteria1.setText("Kriteria 1");

        jTableMatrixComparisonKriteria1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTableMatrixComparisonKriteria1);

        jLabelComparisonKriteria3.setText("Kriteria 3");

        jTableMatrixComparisonKriteria2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTableMatrixComparisonKriteria2);

        jLabelComparisonKriteria4.setText("Kriteria 4");

        jTableMatrixComparisonKriteria3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(jTableMatrixComparisonKriteria3);

        jLabel27.setText("6. Matriks Bobot Alternatif terhadap Kriteria");

        jLabelWeightKriteria1.setText("Kriteria 1");

        jLabelWeightKriteria2.setText("Kriteria 2");

        jTableMatrixComparisonNormalizeKriteria1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(jTableMatrixComparisonNormalizeKriteria1);

        jLabelWeightKriteria3.setText("Kriteria 3");

        jTableMatrixComparisonNormalizeKriteria3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane9.setViewportView(jTableMatrixComparisonNormalizeKriteria3);

        jLabelWeightKriteria4.setText("Kriteria 4");

        jTableMatrixComparisonNormalizeKriteria4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane10.setViewportView(jTableMatrixComparisonNormalizeKriteria4);

        jTableMatrixComparisonNormalizeKriteria2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane11.setViewportView(jTableMatrixComparisonNormalizeKriteria2);

        jLabel34.setText("7. Hasil Akhir");

        jLabelSelectProduct.setText("Pilih");

        jComboBoxProduct.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih" }));
        jComboBoxProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxProductActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabelSelectProduct)
                                .addComponent(jComboBoxProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(mulaiHitung, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(Simpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 669, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelComparisonKriteria2)
                    .addComponent(jLabelComparisonKriteria1)
                    .addComponent(jLabelComparisonKriteria3)
                    .addComponent(jLabelComparisonKriteria4)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelWeightKriteria2)
                    .addComponent(jLabelWeightKriteria1)
                    .addComponent(jLabelWeightKriteria3)
                    .addComponent(jLabelWeightKriteria4)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelSelectProduct)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mulaiHitung, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(jLabel27))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelComparisonKriteria1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelWeightKriteria1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelComparisonKriteria2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelWeightKriteria2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelWeightKriteria3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelWeightKriteria4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelComparisonKriteria3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelComparisonKriteria4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(249, 249, 249))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Simpan, mulaiHitung});

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout PanelPerhitunganLayout = new javax.swing.GroupLayout(PanelPerhitungan);
        PanelPerhitungan.setLayout(PanelPerhitunganLayout);
        PanelPerhitunganLayout.setHorizontalGroup(
            PanelPerhitunganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(judul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PanelPerhitunganLayout.setVerticalGroup(
            PanelPerhitunganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPerhitunganLayout.createSequentialGroup()
                .addComponent(judul, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(900, 900));
        setSize(new java.awt.Dimension(900, 900));

        Pane.setMinimumSize(new java.awt.Dimension(945, 800));
        Pane.setPreferredSize(new java.awt.Dimension(945, 800));
        Pane.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(888, 577));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxProductActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxProductActionPerformed
        try {
            // Ambil item terpilih
            Object selectedItemObj = jComboBoxProduct.getSelectedItem();

            // Jika belum ada pilihan, jangan lanjut
            if (selectedItemObj == null) {
                return; // langsung keluar agar tidak invoke null
            }

            String selectedItem = selectedItemObj.toString();

            // Pastikan bukan pilihan default
            if (!selectedItem.equals("Pilih Karyawan -") && productMap.containsKey(selectedItem)) {
                // Ambil productId
                int productId = productMap.get(selectedItem);

                // Ambil data alternatif dari DB
                List<AlternativeModel> alternatives = alternativeDao.findByProductId(productId);

                // Update tampilan
                updateAlternativeList(alternatives);

                // Optional: reset hasil perhitungan
                clearCalculationResults();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading alternatives: " + e.getMessage());
            e.printStackTrace();
        }
    }// GEN-LAST:event_jComboBoxProductActionPerformed

    private void lambdaMaxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_lambdaMaxActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_lambdaMaxActionPerformed

    private void ciValue1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ciValue1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_ciValue1ActionPerformed

    private void nCriteriaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_nCriteriaActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_nCriteriaActionPerformed

    private void ciValueActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ciValueActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_ciValueActionPerformed

    private void crValueActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_crValueActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_crValueActionPerformed

    private void irValuesActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_irValuesActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_irValuesActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_resetActionPerformed
        clearCalculationResults();
        loadData();
    }// GEN-LAST:event_resetActionPerformed

    private void resetMouseExited(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_resetMouseExited
        // TODO add your handling code here:
    }// GEN-LAST:event_resetMouseExited

    private void resetMouseEntered(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_resetMouseEntered
        // TODO add your handling code here:
    }// GEN-LAST:event_resetMouseEntered

    // simpan data
    private void SimpanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_SimpanActionPerformed
        // Check if product is selected
        if (jComboBoxProduct.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Pilih Karyawan terlebih dahulu.");
            return;
        }

        if (this.alternativeWeightModel == null || this.alternativeWeightModel.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Data bobot alternatif belum tersedia. Silakan lakukan perhitungan terlebih dahulu.");
            return;
        }

        try {
            System.out.println("AlternativeWeightModel.length: " + this.alternativeWeightModel.size());

            // Get selected product ID
            String selectedItem = jComboBoxProduct.getSelectedItem().toString();
            int productId = productMap.get(selectedItem);

            // Step 1: Save to EvaluationDao
            EvaluationModel evaluation = new EvaluationModel();
            evaluation.setKaryawan_id(productId);
            evaluation.setAdminId(1); // Assuming admin ID 1, adjust as needed
            evaluation.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));

            int evaluationId = evaluationDao.insertOne(evaluation);

            if (evaluationId <= 0) {
                JOptionPane.showMessageDialog(null, "Gagal menyimpan data seleksi.");
                return;
            }

            System.out.println("Evaluation saved with ID: " + evaluationId);

            // Step 2: Sort alternatives by weight (descending) for ranking
            List<AlternativeWeight2Model> sortedAlternatives = new ArrayList<>(this.alternativeWeightModel);
            sortedAlternatives.sort((a, b) -> Double.compare(b.getWeight(), a.getWeight()));

            // Step 3: Save ranking results to ResultDao
            for (int i = 0; i < sortedAlternatives.size(); i++) {
                AlternativeWeight2Model item = sortedAlternatives.get(i);
                System.out.println(
                        "Processing ranked item " + (i + 1) + ": id=" + item.getId() + " weight=" + item.getWeight());

                ResultModel result = new ResultModel();
                result.setEvaluationId(evaluationId);
                result.setAlternativeId(item.getId());
                result.setScore(item.getWeight());
                result.setRank(i + 1); // Rank starts from 1

                System.out.println("Before insert result: evaluationId=" + result.getEvaluationId()
                        + " alternativeId=" + result.getAlternativeId()
                        + " score=" + result.getScore()
                        + " rank=" + result.getRank());

                try {
                    int resultInsert = resultDao.insertOne(result);
                    System.out.println("Insert result for alternative id " + item.getId() + ": " + resultInsert);
                } catch (Exception e) {
                    System.err.println(
                            "Error inserting result for alternative id " + item.getId() + ": " + e.getMessage());
                    e.printStackTrace();
                }

                System.out.println("Completed processing ranked item " + (i + 1));
            }

            JOptionPane.showMessageDialog(null, "Data evaluasi dan hasil perangkingan berhasil disimpan.");
            this.resetActionPerformed(evt);

        } catch (Exception e) {
            System.err.println("Gagal Simpan: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan: " + e.getMessage());
        }
    }// GEN-LAST:event_SimpanActionPerformed

    private void SimpanMouseExited(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_SimpanMouseExited
        // TODO add your handling code here:
        Simpan.setBackground(Color.white);
    }// GEN-LAST:event_SimpanMouseExited

    private void SimpanMouseEntered(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_SimpanMouseEntered
        // TODO add your handling code here:
        Simpan.setBackground(new Color(250, 239, 245));
    }// GEN-LAST:event_SimpanMouseEntered

    private void mulaiHitungActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mulaiHitungActionPerformed
        try {

            if (jComboBoxProduct.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Pilih Karyawan Terlebih Dahulu.");
                return;
            }
            double[][] pairwiseComparisonMatrix = ahpCalculation.getPairwiseComparisonMatrix();
            k1k1.setText(df2.format(pairwiseComparisonMatrix[0][0]));
            k1k2.setText(df2.format(pairwiseComparisonMatrix[0][1]));
            k1k3.setText(df2.format(pairwiseComparisonMatrix[0][2]));
            k1k4.setText(df2.format(pairwiseComparisonMatrix[0][3]));
            k2k1.setText(df.format(pairwiseComparisonMatrix[1][0]));
            k2k2.setText(df2.format(pairwiseComparisonMatrix[1][1]));
            k2k3.setText(df2.format(pairwiseComparisonMatrix[1][2]));
            k2k4.setText(df2.format(pairwiseComparisonMatrix[1][3]));
            k3k1.setText(df.format(pairwiseComparisonMatrix[2][0]));
            k3k2.setText(df.format(pairwiseComparisonMatrix[2][1]));
            k3k3.setText(df2.format(pairwiseComparisonMatrix[2][2]));
            k3k4.setText(df2.format(pairwiseComparisonMatrix[2][3]));
            k4k1.setText(df.format(pairwiseComparisonMatrix[3][0]));
            k4k2.setText(df.format(pairwiseComparisonMatrix[3][1]));
            k4k3.setText(df.format(pairwiseComparisonMatrix[3][2]));
            k4k4.setText(df2.format(pairwiseComparisonMatrix[3][3]));

            double[][] normalizedPairwiseComparisonMatrix = ahpCalculation.getNormalizedPairwiseComparisonMatrix();
            k1k1N.setText(df.format(normalizedPairwiseComparisonMatrix[0][0]));
            k1k2N.setText(df.format(normalizedPairwiseComparisonMatrix[0][1]));
            k1k3N.setText(df.format(normalizedPairwiseComparisonMatrix[0][2]));
            k1k4N.setText(df.format(normalizedPairwiseComparisonMatrix[0][3]));
            k2k1N.setText(df.format(normalizedPairwiseComparisonMatrix[1][0]));
            k2k2N.setText(df.format(normalizedPairwiseComparisonMatrix[1][1]));
            k2k3N.setText(df.format(normalizedPairwiseComparisonMatrix[1][2]));
            k2k4N.setText(df.format(normalizedPairwiseComparisonMatrix[1][3]));
            k3k1N.setText(df.format(normalizedPairwiseComparisonMatrix[2][0]));
            k3k2N.setText(df.format(normalizedPairwiseComparisonMatrix[2][1]));
            k3k3N.setText(df.format(normalizedPairwiseComparisonMatrix[2][2]));
            k3k4N.setText(df.format(normalizedPairwiseComparisonMatrix[2][3]));
            k4k1N.setText(df.format(normalizedPairwiseComparisonMatrix[3][0]));
            k4k2N.setText(df.format(normalizedPairwiseComparisonMatrix[3][1]));
            k4k3N.setText(df.format(normalizedPairwiseComparisonMatrix[3][2]));
            k4k4N.setText(df.format(normalizedPairwiseComparisonMatrix[3][3]));

            double[] priorityVector = ahpCalculation.getPriorityVector();
            Prior1.setText(df.format(priorityVector[0]));
            Prior2.setText(df.format(priorityVector[1]));
            Prior3.setText(df.format(priorityVector[2]));
            Prior4.setText(df.format(priorityVector[3]));

            lambdaMax.setText(df.format(ahpCalculation.getLambdaMaxValue()));
            ciValue1.setText(df.format(ahpCalculation.getCIValue()));
            nCriteria.setText(ahpCalculation.getNCriteriaValue() + "");

            ciValue.setText(df.format(ahpCalculation.getCIValue()));
            irValues.setText(df.format(ahpCalculation.getIRValue()));
            crValue.setText(df.format(ahpCalculation.getCRValue()));

            if (ahpCalculation.getCRValue() <= 0.1) {
                conclusion.setText("kurang dari 0.1 konsisten");
            } else {
                conclusion.setText("lebih dari 0.1 tidak konsisten");
            }

            calculateComparisonCriteriaMatrix();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }// GEN-LAST:event_mulaiHitungActionPerformed

    private void mulaiHitungMouseExited(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_mulaiHitungMouseExited
        // TODO add your handling code here:
        mulaiHitung.setBackground(Color.white);
    }// GEN-LAST:event_mulaiHitungMouseExited

    private void mulaiHitungMouseEntered(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_mulaiHitungMouseEntered
        // TODO add your handling code here:
        mulaiHitung.setBackground(new Color(250, 239, 245));
    }// GEN-LAST:event_mulaiHitungMouseEntered

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CalculationDialog.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CalculationDialog.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CalculationDialog.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CalculationDialog.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CalculationDialog dialog = new CalculationDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Pane;
    private javax.swing.JPanel PanelPerhitungan;
    private javax.swing.JTextField Prior1;
    private javax.swing.JTextField Prior2;
    private javax.swing.JTextField Prior3;
    private javax.swing.JTextField Prior4;
    private javax.swing.JButton Simpan;
    private javax.swing.ButtonGroup btnG;
    private javax.swing.JTextField ciValue;
    private javax.swing.JTextField ciValue1;
    private javax.swing.JTextField conclusion;
    private javax.swing.JTextField crValue;
    private javax.swing.JTextField irValues;
    private javax.swing.JComboBox<String> jComboBoxProduct;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelComparisonKriteria1;
    private javax.swing.JLabel jLabelComparisonKriteria2;
    private javax.swing.JLabel jLabelComparisonKriteria3;
    private javax.swing.JLabel jLabelComparisonKriteria4;
    private javax.swing.JLabel jLabelSelectProduct;
    private javax.swing.JLabel jLabelWeightKriteria1;
    private javax.swing.JLabel jLabelWeightKriteria2;
    private javax.swing.JLabel jLabelWeightKriteria3;
    private javax.swing.JLabel jLabelWeightKriteria4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTableMatrixComparisonKriteria1;
    private javax.swing.JTable jTableMatrixComparisonKriteria2;
    private javax.swing.JTable jTableMatrixComparisonKriteria3;
    private javax.swing.JTable jTableMatrixComparisonKriteria4;
    private javax.swing.JTable jTableMatrixComparisonNormalizeKriteria1;
    private javax.swing.JTable jTableMatrixComparisonNormalizeKriteria2;
    private javax.swing.JTable jTableMatrixComparisonNormalizeKriteria3;
    private javax.swing.JTable jTableMatrixComparisonNormalizeKriteria4;
    private javax.swing.JLabel judul;
    private javax.swing.JTextField k1k1;
    private javax.swing.JTextField k1k1N;
    private javax.swing.JTextField k1k2;
    private javax.swing.JTextField k1k2N;
    private javax.swing.JTextField k1k3;
    private javax.swing.JTextField k1k3N;
    private javax.swing.JTextField k1k4;
    private javax.swing.JTextField k1k4N;
    private javax.swing.JTextField k2k1;
    private javax.swing.JTextField k2k1N;
    private javax.swing.JTextField k2k2;
    private javax.swing.JTextField k2k2N;
    private javax.swing.JTextField k2k3;
    private javax.swing.JTextField k2k3N;
    private javax.swing.JTextField k2k4;
    private javax.swing.JTextField k2k4N;
    private javax.swing.JTextField k3k1;
    private javax.swing.JTextField k3k1N;
    private javax.swing.JTextField k3k2;
    private javax.swing.JTextField k3k2N;
    private javax.swing.JTextField k3k3;
    private javax.swing.JTextField k3k3N;
    private javax.swing.JTextField k3k4;
    private javax.swing.JTextField k3k4N;
    private javax.swing.JTextField k4k1;
    private javax.swing.JTextField k4k1N;
    private javax.swing.JTextField k4k2;
    private javax.swing.JTextField k4k2N;
    private javax.swing.JTextField k4k3;
    private javax.swing.JTextField k4k3N;
    private javax.swing.JTextField k4k4;
    private javax.swing.JTextField k4k4N;
    private javax.swing.JTextField lambdaMax;
    private javax.swing.JButton mulaiHitung;
    private javax.swing.JTextField nCriteria;
    private javax.swing.JButton reset;
    // End of variables declaration//GEN-END:variables

    void show(JRootPane rootPane) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }
}
