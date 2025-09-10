/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.models;

/**
 *
 * @author mahasiswa unindra
 */
public class CriteriaBobotModel {

    public CriteriaBobotModel(int id, String k1, String k2, double bobot) {
        this.id = id;
        this.k1 = k1;
        this.k2 = k2;
        this.bobot = bobot;
    }

    public CriteriaBobotModel() {
    }

    public String getK1() {
        return k1;
    }

    public void setK1(String k1) {
        this.k1 = k1;
    }

    public String getK2() {
        return k2;
    }

    public void setK2(String k2) {
        this.k2 = k2;
    }

    public double getBobot() {
        return bobot;
    }

    public void setBobot(double bobot) {
        this.bobot = bobot;
    }
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private String k1;
    private String k2;
    private double bobot;

}
