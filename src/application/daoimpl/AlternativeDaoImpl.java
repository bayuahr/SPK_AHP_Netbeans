package application.daoimpl;

import application.dao.AlternativeDao;
import application.models.AlternativeModel;
import application.models.KaryawanModel;
import application.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlternativeDaoImpl implements AlternativeDao {

    private final Connection dbConnection;
    private PreparedStatement pstmt;
    private ResultSet resultSet;
    private String query;

    public AlternativeDaoImpl() {
        dbConnection = DatabaseUtil.getInstance().getConnection();
    }

    @Override
    public int insertOne(AlternativeModel alt) {
        try {
            query = "INSERT INTO alternatives (karyawan_id,k1_score, k2_score, k3_score, k4_score) "
                    + "VALUES (?, ?, ?, ?, ?)";
            pstmt = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, alt.getKaryawanId());
            pstmt.setInt(2, alt.getK1Score());
            pstmt.setInt(3, alt.getK2Score());
            pstmt.setInt(4, alt.getK3Score());
            pstmt.setInt(5, alt.getK4Score());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }
    }

    @Override
    public List<AlternativeModel> findAll() {
        try {
            query = "SELECT a.*, "
                    + "p.id AS karyawan_id, p.nama AS nama_karyawan,p.jenis_kelamin,p.posisi "
                    + "FROM alternatives a JOIN employees p ON a.karyawan_id = p.id";

            pstmt = dbConnection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            List<AlternativeModel> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(extractFromResultSet(resultSet));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }
    }

    @Override
    public List<AlternativeModel> findByProductId(int id) {
        try {
            query = "SELECT a.*, "
                    + "p.id AS karyawan_id, p.nama AS nama_karyawan,p.jenis_kelamin,p.posisi "
                    + "FROM alternatives a JOIN employees p ON a.karyawan_id = p.id "
                    + "WHERE a.karyawan_id = ?";

            pstmt = dbConnection.prepareStatement(query);
            pstmt.setInt(1, id);
            resultSet = pstmt.executeQuery();

            List<AlternativeModel> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(extractFromResultSet(resultSet));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }
    }

    @Override
    public int update(AlternativeModel alt) {
        try {
            query = "UPDATE alternatives SET karyawan_id = ?,"
                    + "k1_score = ?, k2_score = ?, k3_score = ?, k4_score = ? WHERE id = ?";
            pstmt = dbConnection.prepareStatement(query);
            pstmt.setInt(1, alt.getKaryawanId());
            pstmt.setInt(2, alt.getK1Score());
            pstmt.setInt(3, alt.getK2Score());
            pstmt.setInt(4, alt.getK3Score());
            pstmt.setInt(5, alt.getK4Score());
            pstmt.setInt(6, alt.getId());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }
    }

    @Override
    public int delete(int id) {
        try {
            query = "DELETE FROM alternatives WHERE id = ?";
            pstmt = dbConnection.prepareStatement(query);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }
    }

    private AlternativeModel extractFromResultSet(ResultSet rs) throws SQLException {
        AlternativeModel alt = new AlternativeModel();
        alt.setId(rs.getInt("id"));
        alt.setKaryawanId(rs.getInt("karyawan_id"));
        alt.setK1Score(rs.getInt("k1_score"));
        alt.setK2Score(rs.getInt("k2_score"));
        alt.setK3Score(rs.getInt("k3_score"));
        alt.setK4Score(rs.getInt("k4_score"));

        KaryawanModel kar = new KaryawanModel();
        kar.setId(rs.getInt("karyawan_id"));
        kar.setNama(rs.getString("nama_karyawan"));
        kar.setJenis_kelamin(rs.getString("jenis_kelamin"));
        kar.setPosisi(rs.getString("posisi"));

        alt.setK(kar);

        return alt;
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
