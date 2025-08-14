package application.models;

public class AlternativeModel {

    private int id;
    private int karyawanId;
    private int k1Score;
    private int k2Score;
    private int k3Score;
    private int k4Score;
    private KaryawanModel k;

    public AlternativeModel(int id, int karyawanId, int k1Score, int k2Score, int k3Score, int k4Score, KaryawanModel k) {
        this.id = id;
        this.karyawanId = karyawanId;
        this.k1Score = k1Score;
        this.k2Score = k2Score;
        this.k3Score = k3Score;
        this.k4Score = k4Score;
        this.k = k;
    }

    public AlternativeModel() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKaryawanId(int karyawanId) {
        this.karyawanId = karyawanId;
    }

    public void setK1Score(int k1Score) {
        this.k1Score = k1Score;
    }

    public void setK2Score(int k2Score) {
        this.k2Score = k2Score;
    }

    public void setK3Score(int k3Score) {
        this.k3Score = k3Score;
    }

    public void setK4Score(int k4Score) {
        this.k4Score = k4Score;
    }

    public void setK(KaryawanModel k) {
        this.k = k;
    }

    public int getId() {
        return id;
    }

    public int getKaryawanId() {
        return karyawanId;
    }

    public int getK1Score() {
        return k1Score;
    }

    public int getK2Score() {
        return k2Score;
    }

    public int getK3Score() {
        return k3Score;
    }

    public int getK4Score() {
        return k4Score;
    }

    public KaryawanModel getK() {
        return k;
    }

    
}
