package application.models;

import java.sql.Timestamp;

public class EvaluationModel {

    private int id;
    private int karyawan_id;
    private int adminId;
    private Timestamp createdAt;
    private KaryawanModel karyawan;

    public void setKaryawan(KaryawanModel karyawan) {
        this.karyawan = karyawan;
    }

    public KaryawanModel getKaryawan() {
        return karyawan;
    }

    public EvaluationModel() {
    }

    public EvaluationModel(int id, int karyawan_id, int adminId,Timestamp createdAt,KaryawanModel karyawan) {
        this.id = id;
        this.karyawan_id = karyawan_id;
        this.adminId = adminId;
        this.createdAt = createdAt;
        this.karyawan = karyawan;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKaryawan_id(int karyawan_id) {
        this.karyawan_id = karyawan_id;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getKaryawan_id() {
        return karyawan_id;
    }

    public int getAdminId() {
        return adminId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    
}
