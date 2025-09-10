package application.utils;

import application.daoimpl.CriteriaBobotDaoImpl;
import application.daoimpl.CriteriaDaoImpl;
import application.models.CriteriaBobotModel;
import java.text.DecimalFormat;
import java.util.List;

/**
 *
 * @author mahasiswa unindra
 */
public final class AHPCalculation {

    private int n;
    private double[][] pairwiseComparisonMatrix;
    private double[] pairwiseComparisonMatrixSum;
    private double[][] normalizedPairwiseComparisonMatrix;
    private double[] normalizedPairwiseComparisonMatrixSum;
    private double[] priorityVector;
    private double[] weightedSumVector;
    private double[] lambdaVector;
    private double irValue;
    private double ciValue;
    private double crValue;
    private double lambdaMax;

    // Konstruktor default pakai DAO
    public AHPCalculation() {
        setPairwiseComparisonMatrixFromDao();
        calculatePriorityVector();
    }

    // Konstruktor tambahan: langsung dari daftar kriteria + daftar perbandingan (untuk testing)
    public AHPCalculation(List<String> criteriaNames, List<CriteriaBobotModel> bobotList) {
        setPairwiseComparisonMatrix(criteriaNames, bobotList);
        calculatePriorityVector();
    }

    // Ambil data dari DAO (sebelumnya Anda pakai CriteriaDaoImpl & CriteriaBobotDaoImpl)
    private void setPairwiseComparisonMatrixFromDao() {
        List<String> criteriaNames = new CriteriaDaoImpl().findColumns();
        List<CriteriaBobotModel> bobotList = new CriteriaBobotDaoImpl().findAll();
        setPairwiseComparisonMatrix(criteriaNames, bobotList);
    }

    // Membentuk matriks dari data yang diberikan
    public void setPairwiseComparisonMatrix(List<String> criteriaNames, List<CriteriaBobotModel> bobotList) {
        if (criteriaNames == null || criteriaNames.size() == 0) {
            throw new IllegalArgumentException("Daftar kriteria kosong.");
        }

        this.n = criteriaNames.size();

        // inisialisasi semua struktur data
        this.pairwiseComparisonMatrix = new double[n][n];
        this.pairwiseComparisonMatrixSum = new double[n];
        this.normalizedPairwiseComparisonMatrix = new double[n][n];
        this.normalizedPairwiseComparisonMatrixSum = new double[n];
        this.priorityVector = new double[n];
        this.weightedSumVector = new double[n];
        this.lambdaVector = new double[n];

        // default semua nilai = 1.0 (jika tidak ada perbandingan eksplisit, diasumsikan sama)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                pairwiseComparisonMatrix[i][j] = 1.0;
            }
        }

        // override dengan nilai dari bobotList dan isi timbal baliknya
        if (bobotList != null) {
            for (CriteriaBobotModel b : bobotList) {
                int i = criteriaNames.indexOf(b.getK1());
                int j = criteriaNames.indexOf(b.getK2());
                double value = b.getBobot();
                if (i >= 0 && j >= 0 && value > 0) {
                    pairwiseComparisonMatrix[i][j] = value;
                    pairwiseComparisonMatrix[j][i] = 1.0 / value;
                }
            }
        }

        printArray2D(this.pairwiseComparisonMatrix, "Matriks Perbandingan Berpasangan (pairwise)");
    }

    public double[][] getPairwiseComparisonMatrix() {
        return pairwiseComparisonMatrix;
    }

    public double[][] getNormalizedPairwiseComparisonMatrix() {
        return normalizedPairwiseComparisonMatrix;
    }

    public double[] getPriorityVector() {
        return priorityVector;
    }

    public double getIRValue() {
        return this.irValue;
    }

    public double getCIValue() {
        return this.ciValue;
    }

    public double getCRValue() {
        return this.crValue;
    }

    public double getLambdaMaxValue() {
        return this.lambdaMax;
    }

    public int getNCriteriaValue() {
        return this.n;
    }

    private void safeZeroToOneIfNeeded(double[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (Double.isNaN(arr[i]) || Double.isInfinite(arr[i])) {
                arr[i] = 0.0;
            }
        }
    }

    public void printArray2D(double[][] array, String title) {
        DecimalFormat df = new DecimalFormat("#.####");
        System.out.println("\n[#" + title + "]");
        for (double[] array1 : array) {
            for (int j = 0; j < array1.length; j++) {
                System.out.print(df.format(array1[j]) + "\t");
            }
            System.out.println();
        }
    }

    public void printArray1D(double[] array, String title) {
        DecimalFormat df = new DecimalFormat("#.####");
        System.out.println("\n[#" + title + "]");
        for (double value : array) {
            System.out.print(df.format(value) + " ");
        }
        System.out.println();
    }

    public void calculatePriorityVector() {
        if (pairwiseComparisonMatrix == null) {
            throw new IllegalStateException("Pairwise matrix belum diset.");
        }

        // 1. Hitung jumlah tiap kolom
        for (int col = 0; col < n; col++) {
            double sum = 0.0;
            for (int row = 0; row < n; row++) {
                sum += pairwiseComparisonMatrix[row][col];
            }
            pairwiseComparisonMatrixSum[col] = sum;
        }
        printArray1D(this.pairwiseComparisonMatrixSum, "Jumlah Tiap Kolom Matriks Perbandingan");

        // 2. Normalisasi matriks (bagi setiap elemen dengan jumlah kolomnya)
        for (int row = 0; row < n; row++) {
            double rowSumNormalized = 0.0;
            for (int col = 0; col < n; col++) {
                // jika pairwiseComparisonMatrixSum[col] == 0, hindari pembagian 0
                double denom = pairwiseComparisonMatrixSum[col];
                if (denom == 0) {
                    normalizedPairwiseComparisonMatrix[row][col] = 0.0;
                } else {
                    normalizedPairwiseComparisonMatrix[row][col] = pairwiseComparisonMatrix[row][col] / denom;
                }
                rowSumNormalized += normalizedPairwiseComparisonMatrix[row][col];
            }
            normalizedPairwiseComparisonMatrixSum[row] = rowSumNormalized;
            priorityVector[row] = normalizedPairwiseComparisonMatrixSum[row] / n; // rata-rata baris
        }

        printArray2D(this.normalizedPairwiseComparisonMatrix, "Matriks Normalized Perbandingan Berpasangan");
        printArray1D(this.normalizedPairwiseComparisonMatrixSum, "Jumlah Baris Matriks Normalized");
        printArray1D(this.priorityVector, "Priority Vector (Bobot)");

        // 3. Weighted sum vector = A * priorityVector
        for (int row = 0; row < n; row++) {
            double sum = 0.0;
            for (int col = 0; col < n; col++) {
                sum += pairwiseComparisonMatrix[row][col] * priorityVector[col];
            }
            weightedSumVector[row] = sum;
        }
        printArray1D(this.weightedSumVector, "Weighted Sum Vector (A * w)");

        // 4. Lambda vector = weightedSumVector[i] / priorityVector[i]
        double lambdaSum = 0.0;
        for (int i = 0; i < n; i++) {
            if (priorityVector[i] == 0) {
                lambdaVector[i] = 0.0; // safety
            } else {
                lambdaVector[i] = weightedSumVector[i] / priorityVector[i];
            }
            lambdaSum += lambdaVector[i];
        }

        // 5. lambdaMax = average of lambdaVector
        if (n > 0) {
            this.lambdaMax = lambdaSum / n;
        } else {
            this.lambdaMax = 0.0;
        }

        printArray1D(this.lambdaVector, "Lambda Vector (weightedSum / priority)");
        System.out.println("\n[Lambda Max] = " + new DecimalFormat("#.####").format(this.lambdaMax));

        // 6. Konsistensi
        double CI = 0.0;
        if (n > 1) {
            CI = (this.lambdaMax - n) / (n - 1);
        }
        this.ciValue = CI;

        double ir = this.getRandomIndex(n);
        this.irValue = ir;

        if (ir == 0) {
            this.crValue = 0.0; // tidak terdefinisi -> set 0
        } else {
            this.crValue = CI / ir;
        }

        System.out.println("CI = " + new DecimalFormat("#.####").format(CI));
        System.out.println("IR (Random Index) = " + new DecimalFormat("#.####").format(ir));
        System.out.println("CR = " + new DecimalFormat("#.####").format(this.crValue));
        System.out.println((this.crValue <= 0.1) ? "Konsisten" : "Tidak Konsisten");
    }

    // Ambil Random Index (RI) berdasarkan ukuran matriks (n)
    private double getRandomIndex(int n) {
        // Nilai RI klasik untuk n = 1..10
        double[] ri = {0.0, 0.0, 0.58, 0.90, 1.12, 1.24, 1.32, 1.41, 1.45, 1.49};
        if (n >= 1 && n <= ri.length) {
            return ri[n - 1];
        }
        // Jika n > 10, Anda bisa menambahkan tabel atau aproksimasi.
        return 1.49; // fallback conservative
    }

    // contoh main untuk test mandiri (jika ingin mencoba tanpa DAO)
    public static void main(String[] args) {
        // contoh kriteria dan bobot (upper-triangle)
        java.util.List<String> criteria = new CriteriaDaoImpl().findColumns();
        

        java.util.List<CriteriaBobotModel> comparisons = new CriteriaBobotDaoImpl().findAll();

        AHPCalculation ahp = new AHPCalculation(criteria, comparisons);

        System.out.println("\n-- Selesai perhitungan --");
        System.out.println("LambdaMax = " + ahp.getLambdaMaxValue());
        System.out.println("CI = " + ahp.getCIValue());
        System.out.println("IR = " + ahp.getIRValue());
        System.out.println("CR = " + ahp.getCRValue());
    }
}
