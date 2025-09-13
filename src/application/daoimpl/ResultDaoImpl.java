package application.daoimpl;

import application.dao.ResultDao;
import application.models.AlternativeModel;
import application.models.KaryawanModel;
import application.models.ResultModel;
import application.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultDaoImpl implements ResultDao {

    private final Connection dbConnection;
    private PreparedStatement pstmt;
    private ResultSet resultSet;
    private String query;

    public ResultDaoImpl() {
        this.dbConnection = DatabaseUtil.getInstance().getConnection();
    }

    @Override
    public int insertOne(ResultModel result) {
        try {
            query = "INSERT INTO results(evaluation_id, alternative_id, score, rank) VALUES (?, ?, ?, ?)";
            pstmt = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, result.getEvaluationId());
            pstmt.setInt(2, result.getAlternativeId());
            pstmt.setDouble(3, result.getScore());
            pstmt.setObject(4, result.getRank(), java.sql.Types.INTEGER);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting result", e);
        } finally {
            closeResources();
        }
    }

    @Override
    public List<ResultModel> findByEvaluationId() {
        try {
            query = "SELECT r.*, "
                    + "a.id as alt_id, a.karyawan_id, "
                    + "a.k1_score, a.k2_score, a.k3_score, a.k4_score, "
                    + "p.id as karyawan_id,  p.nama as karyawan_nama, p.jenis_kelamin , p.posisi "
                    + "FROM results r "
                    + "LEFT JOIN alternatives a ON r.alternative_id = a.id "
                    + "LEFT JOIN employees p ON a.karyawan_id = p.id "
                    + "ORDER BY r.rank ASC";

            pstmt = dbConnection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            List<ResultModel> results = new ArrayList<>();
            while (resultSet.next()) {
                ResultModel result = new ResultModel();
                result.setId(resultSet.getInt("id"));
                result.setEvaluationId(1);
                result.setAlternativeId(resultSet.getInt("alternative_id"));
                result.setScore(resultSet.getDouble("score"));
                result.setRank(resultSet.getInt("rank"));

                // Create and populate AlternativeModel
                AlternativeModel alternative = new AlternativeModel();
                alternative.setId(resultSet.getInt("alt_id"));
                alternative.setK1Score(resultSet.getInt("k1_score"));
                alternative.setK2Score(resultSet.getInt("k2_score"));
                alternative.setK3Score(resultSet.getInt("k3_score"));
                alternative.setK4Score(resultSet.getInt("k4_score"));

                // Create and populate ProductModel
                KaryawanModel product = new KaryawanModel();
                product.setId(resultSet.getInt("karyawan_id"));
                product.setNama(resultSet.getString("karyawan_nama"));
                product.setJenis_kelamin(resultSet.getString("jenis_kelamin"));
                product.setPosisi(resultSet.getString("posisi"));

                // Set product to alternative
                alternative.setK(product);

                // Set alternative to result
                result.setAlternative(alternative);

                results.add(result);
            }

            return results;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching results by evaluation_id", e);
        } finally {
            closeResources();
        }
    }

    @Override
    public int deleteByEvaluationId() {
        try {
            query = "DELETE FROM results";
            pstmt = dbConnection.prepareStatement(query);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting results by evaluation_id", e);
        } finally {
            closeResources();
        }
    }

    private void closeResources() {
        try {
            if (pstmt != null) {
                pstmt.close();
                pstmt = null;
            }
            if (resultSet != null) {
                resultSet.close();
                resultSet = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error closing resources", e);
        }
    }
}
