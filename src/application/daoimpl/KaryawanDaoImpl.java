package application.daoimpl;

import application.models.KaryawanModel;
import application.utils.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import application.dao.KaryawanDao;

public class KaryawanDaoImpl implements KaryawanDao {
    private final Connection dbConnection;
    private PreparedStatement pstmt;
    private ResultSet resultSet;
    private String query;

    public KaryawanDaoImpl() {
        dbConnection = DatabaseUtil.getInstance().getConnection();
    }

    @Override
    public int insertOne(KaryawanModel k) {
        try {
            query = "INSERT INTO employees(nama, jenis_kelamin, posisi) VALUES (?, ?, ?)";
            pstmt = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, k.getNama());
            pstmt.setString(2, k.getJenis_kelamin());
            pstmt.setString(3, k.getPosisi());

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }
    }

    @Override
    public List<KaryawanModel> findAll() {
        try {
            query = "SELECT * FROM employees";
            pstmt = dbConnection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            List<KaryawanModel> kList = new ArrayList<>();

            while (resultSet.next()) {
                KaryawanModel k = new KaryawanModel();
                k.setId(resultSet.getInt("id"));
                k.setNama(resultSet.getString("nama"));
                k.setJenis_kelamin(resultSet.getString("jenis_kelamin"));
                k.setPosisi(resultSet.getString("posisi"));

                kList.add(k);
            }

            return kList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }
    }

    @Override
    public KaryawanModel findByCode(int code) {
        try {
            query = "SELECT * FROM employees WHERE id = ?";
            pstmt = dbConnection.prepareStatement(query);
            pstmt.setInt(1, code);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                KaryawanModel k = new KaryawanModel();
                k.setId(resultSet.getInt("id"));
                k.setNama(resultSet.getString("nama"));
                k.setJenis_kelamin(resultSet.getString("jenis_kelamin"));
                k.setPosisi(resultSet.getString("posisi"));

                return k;
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }
    }

    @Override
    public int update(KaryawanModel k) {
        try {
            query = "UPDATE employees SET nama = ?, jenis_kelamin= ?, posisi= ? WHERE id= ?";
            pstmt = dbConnection.prepareStatement(query);
            pstmt.setString(1, k.getNama());
            pstmt.setString(2, k.getJenis_kelamin());
            pstmt.setString(3, k.getPosisi());
            pstmt.setInt(4, k.getId());

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }
    }

    @Override
    public int delete(int code) {
        try {
            query = "DELETE FROM employees WHERE id = ?";
            pstmt = dbConnection.prepareStatement(query);
            pstmt.setInt(1, code);
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
                resultSet = null;
            }
            if (pstmt != null) {
                pstmt.close();
                pstmt = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
