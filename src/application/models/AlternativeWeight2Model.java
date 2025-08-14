/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mahasiswa unindra 
 */
public class AlternativeWeight2Model {

    public double getWeight() {
        return weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getKriteria1Score() {
        return kriteria1Score;
    }

    public void setKriteria1Score(double kriteria1Score) {
        this.kriteria1Score = kriteria1Score;
    }

    public double getKriteria2Score() {
        return kriteria2Score;
    }

    public void setKriteria2Score(double kriteria2Score) {
        this.kriteria2Score = kriteria2Score;
    }

    public double getKriteria3Score() {
        return kriteria3Score;
    }

    public void setKriteria3Score(double kriteria3Score) {
        this.kriteria3Score = kriteria3Score;
    }

    public double getKriteria4Score() {
        return kriteria4Score;
    }

    public void setKriteria4Score(double kriteria4Score) {
        this.kriteria4Score = kriteria4Score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    private int id;
    private double kriteria1Score;
    private double kriteria2Score;
    private double kriteria3Score;
    private double kriteria4Score;
    public double weight;
    private String name; 
    
    public static List<AlternativeWeight2Model> fromArray(Object[][] dataArray) {
        List<AlternativeWeight2Model> models = new ArrayList<>();
        for (Object[] data : dataArray) {
            AlternativeWeight2Model model = new AlternativeWeight2Model();
            model.setId((Integer) data[0]);
            model.setName((String) data[1]);
            model.setKriteria1Score((Double) data[2]);
            model.setKriteria2Score((Double) data[3]);
            model.setKriteria3Score((Double) data[4]);
            model.setKriteria4Score((Double) data[5]);
            model.weight = (Double) data[6];  // Assuming weight is at index 6
            models.add(model);
        }
        return models;
    }
}
