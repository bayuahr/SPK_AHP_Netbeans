package application.daoimpl;

import application.dao.EvaluationDao;
import application.models.EvaluationModel;
import application.models.KaryawanModel;
import application.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvaluationDaoImpl implements EvaluationDao {

    private final Connection dbConnection;
    private PreparedStatement pstmt;
    private ResultSet resultSet;
    private String query;

    public EvaluationDaoImpl() {
        dbConnection = DatabaseUtil.getInstance().getConnection();
    }

    @Override
    public int insertOne(EvaluationModel selection) {
        try {
            query = "INSERT INTO evaluations (karyawan_id, admin_id, selection_name, created_at) VALUES (?, ?, ?, ?)";
            pstmt = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, selection.getKaryawan_id());
            pstmt.setInt(2, selection.getAdminId());
            pstmt.setString(3, selection.getSelectionName());
            pstmt.setTimestamp(4, selection.getCreatedAt());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the generated ID
                    }
                }
            }

            return 0; // Return 0 if no ID was generated

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }
    }

    @Override
    public List<EvaluationModel> findAll() {
        try {
            query = "SELECT s.*, p.nama AS nama_karyawan,p.jenis_kelamin as jenis_kelamin,"
                    + "p.posisi as posisi, p.id AS karyawan_id, a.name AS admin_name "
                    + "FROM evaluations s "
                    + "JOIN employees p ON s.karyawan_id = p.id "
                    + "JOIN users a ON s.admin_id = a.id "
                    + "ORDER BY s.created_at DESC";

            pstmt = dbConnection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            List<EvaluationModel> list = new ArrayList<>();
            while (resultSet.next()) {
                EvaluationModel s = new EvaluationModel();
                s.setId(resultSet.getInt("id"));
                s.setKaryawan_id(resultSet.getInt("karyawan_id"));
                s.setAdminId(resultSet.getInt("admin_id"));
                s.setSelectionName(resultSet.getString("karyawan_id"));
                s.setCreatedAt(resultSet.getTimestamp("created_at"));
                s.setAdminId(resultSet.getInt("admin_id"));

                KaryawanModel karyawan = new KaryawanModel();
                karyawan.setId(resultSet.getInt("karyawan_id"));
                karyawan.setNama(resultSet.getString("nama_karyawan"));
                karyawan.setJenis_kelamin(resultSet.getString("jenis_kelamin"));
                karyawan.setJenis_kelamin(resultSet.getString("jenis_kelamin"));
                s.setKaryawan(karyawan);

                list.add(s);
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }
    }

    @Override
    public int delete(int id) {
        try {
            query = "DELETE FROM evaluations WHERE id = ?";
            pstmt = dbConnection.prepareStatement(query);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }
    }

    private void closeStatement() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
