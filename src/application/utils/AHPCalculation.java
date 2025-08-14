/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.utils;

import java.text.DecimalFormat;

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
    private double[][] matriksPenjumlahan;
    private double[] jumlahMatriksPenjumlahan;
    private double[] priorityVector;
    private double[] jumlahCekKonsistensi;
    private double[] eigenVector;
    private double irValue;
    private double ciValue;
    private double crValue;
    private double lambdaMax;
    
    public AHPCalculation(){
        setPairwiseComparisonMatrix();
        calculatePriorityVector();
    }
    
    public void setPairwiseComparisonMatrix() {
        // Membuat matriks perbandingan
//        double pairwiseComparisonMatrix[][] =  {
//            {1, 5.0, 7.0, 9.0},
//            {1.0/5.0, 1, 5.0, 7.0},
//            {1.0/7.0, 1.0/5.0, 1, 5.0},
//            {1.0/9.0, 1.0/7.0, 1.0/5.0, 1}
//        };

        double pairwiseComparisonMatrix[][] =  {
            {1.0, 2.0, 3.0, 4.0},
            {1.0 / 2.0, 1.0, 2.0, 3.0},
            {1.0 / 3.0, 1.0 / 2.0, 1.0, 3.0},
            {1.0 / 4.0, 1.0 / 3.0, 1.0 / 3.0, 1.0}
        };

        this.n = pairwiseComparisonMatrix.length;
        
        this.pairwiseComparisonMatrix = new double[n][n];
        this.pairwiseComparisonMatrixSum = new double[n];
        
        this.normalizedPairwiseComparisonMatrix = new double[n][n];
        this.normalizedPairwiseComparisonMatrixSum = new double[n];
        
        this.matriksPenjumlahan = new double[n][n];
        this.jumlahMatriksPenjumlahan = new double[n];
        
        this.priorityVector = new double[n];
        
        this.eigenVector = new double[n];
        
        this.jumlahCekKonsistensi = new double[n];
    
        for (int row = 0; row < this.n; row++) {
            for (int col = 0; col < this.n; col++) {
                this.pairwiseComparisonMatrix[row][col] = pairwiseComparisonMatrix[row][col];
            }
        }
        
        printArray2D(this.pairwiseComparisonMatrix, "Matriks Perbandingan Berpasangan");

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
    
    public void printArray2D(double[][] array, String title) {
        System.out.println("\n[#" + title +"]");
        for (double[] array1 : array) {
            for (int j = 0; j < array1.length; j++) {
                System.out.print(array1[j] + " ");
            }
            System.out.println();
        }
    }
    
    public void printArray1D(double[] array, String title) {
        System.out.println("\n[#" + title +"]");
        for (double value : array) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
    
    public void calculatePriorityVector(){       
        // Hitung jumlah kolom
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                this.pairwiseComparisonMatrixSum[col] += pairwiseComparisonMatrix[row][col];
            }
        }
        printArray1D(this.pairwiseComparisonMatrixSum, "Menjumlah Setiap Kolom Matriks Perbandingan Berpasangan");

        // Normalisasi matriks perbandingan berpasangan
        for (int row = 0; row < n; row++) {
            for (int col = 0; col< n; col++) {
                this.normalizedPairwiseComparisonMatrix[row][col] = this.pairwiseComparisonMatrix[row][col] / this.pairwiseComparisonMatrixSum[col];
                this.normalizedPairwiseComparisonMatrixSum[row] += normalizedPairwiseComparisonMatrix[row][col];
                this.priorityVector[row] = normalizedPairwiseComparisonMatrixSum[row] / n; // atau rata-rata
            }
        }
        printArray2D(this.normalizedPairwiseComparisonMatrix, "Matriks Normalized Perbandingan Berpasangan");
        printArray1D(this.normalizedPairwiseComparisonMatrixSum, "Menjumlah Setiap Baris Matriks Normalized Perbandingan Berpasangan");
        printArray1D(this.priorityVector, "Average Setiap Baris Matriks Normalized Perbandingan Berpasangan");
        
        
        // Menghitung nilai eigen vector
        for (int row = 0; row < n; row++) {
            this.eigenVector[row] = this.priorityVector[row] * this.pairwiseComparisonMatrixSum[row];
        }
        printArray1D(this.eigenVector, "Nilai eigen vector");
        
        
        double lambdaMax = 0;
        
        for (double num : this.eigenVector) {
            lambdaMax += num;
        }
        System.out.println("Nilai lambda max: " + lambdaMax);
        
        double CI= (lambdaMax-this.n)/(this.n-1);
        System.out.println("Nilai CI: " + CI);
        
        double ir = this.getConsistencyIndex(n);
        
        this.irValue = ir;
        this.ciValue = CI;
        
        double CR= CI/ir;
        if(CR <= 0.1){
            System.out.println("Konsisten");
        }else{
            System.out.println("Tidak Konsisten");
        }
        this.crValue = CR;
        
//      cara lain
//        for (int row = 0; row < n; row++) {
//            for (int col = 0; col < n; col++) {
//                matriksPenjumlahan[row][col] = pairwiseComparisonMatrix[row][col] * priorityVector[col];
//                jumlahMatriksPenjumlahan[row] += matriksPenjumlahan[row][col];
//            }
//        }
//        printArray2D(this.matriksPenjumlahan, "Matriks Perbandingan Berpasangan Penjumlahan Setiap Baris");
//        printArray1D(this.jumlahMatriksPenjumlahan, "Matriks Perbandingan Berpasangan Penjumlahan Setiap Baris");
//        
//        double totalJumlah=0;
//        for (int row = 0; row < n; row++) {
//            jumlahCekKonsistensi[row] = jumlahMatriksPenjumlahan[row] + priorityVector[row];
//            totalJumlah += jumlahCekKonsistensi[row];
//        }
//        printArray1D(this.jumlahCekKonsistensi, "Perhitungan Konsistensi Rasio untuk Kriteria");
// 
//        double ir = this.getConsistencyIndex(n);
//        double lamdaMaks = totalJumlah/n;
//        double CI=(lamdaMaks-n)/(n-1);
//        double CR=CI/ir;
//        if(CR <= 0.1){
//            System.out.println("Konsisten");
//        }else{
//            System.out.println("Tidak Konsisten");
//        }  
    }
    
    private double getConsistencyIndex(int n) {
        double[] consistencyIndices = {0.0, 0.0, 0.58, 0.90, 1.12, 1.24, 1.32, 1.41, 1.45, 1.49};
        return consistencyIndices[n - 1];
    }
    
    public double getIRValue(){
        return this.irValue;
    }
    
    public double getCIValue(){
        return this.ciValue;
    }
    
    public double getCRValue(){
        return this.crValue;
    }
    
    public double getLambdaMaxValue(){
        return this.lambdaMax;
    }
    
    public int getNCriteriaValue(){
        return this.n;
    }
    
    public static void main(String args[]) {
        AHPCalculation ahpCalculation = new AHPCalculation();
    }
}
