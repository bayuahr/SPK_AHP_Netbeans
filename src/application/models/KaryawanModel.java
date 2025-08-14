/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.models;

/**
 * Model class for product entity Representing fields from the 'product' table
 *
 * @author
 */
public class KaryawanModel {

    private int id;
    private String nama;
    private String jenis_kelamin;
    private String posisi;

    public KaryawanModel(int id, String nama, String jenis_kelamin, String posisi) {
        this.id = id;
        this.nama = nama;
        this.jenis_kelamin = jenis_kelamin;
        this.posisi = posisi;
    }

    public KaryawanModel() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public String getPosisi() {
        return posisi;
    }

}
