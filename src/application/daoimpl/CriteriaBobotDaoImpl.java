/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.daoimpl;

import application.dao.CriteriaBobotDao;
import application.dao.CriteriaDao;
import application.models.CriteriaBobotModel;
import application.models.CriteriaModel;
import application.utils.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mahasiswa unindra
 */
public class CriteriaBobotDaoImpl implements CriteriaBobotDao {

    private final Connection dbConnection;
    private PreparedStatement pstmt;
    private ResultSet resultSet;
    private String query;

    public CriteriaBobotDaoImpl() {
        dbConnection = DatabaseUtil.getInstance().getConnection();
    }

    @Override
    public int insertOne(CriteriaBobotModel criteria) {
        try {
            query = "INSERT INTO bobot_kriteria "
                    + "VALUES (null,?,?,?)";

            pstmt = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, criteria.getK1());
            pstmt.setString(2, criteria.getK2());
            pstmt.setDouble(3, criteria.getBobot());

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }
    }

    private void closeStatement() {
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
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CriteriaBobotModel> findAll() {
        try {
            query = "SELECT * FROM bobot_kriteria";

            pstmt = dbConnection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            List<CriteriaBobotModel> criteriaList = new ArrayList<>();

            while (resultSet.next()) {
                CriteriaBobotModel criteria = new CriteriaBobotModel();
                criteria.setId(resultSet.getInt("id"));
                criteria.setK1(resultSet.getString("k1"));
                criteria.setK2(resultSet.getString("k2"));
                criteria.setBobot(resultSet.getDouble("bobot"));
                criteriaList.add(criteria);
            }

            return criteriaList;
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }
    }

    @Override
    public int update(CriteriaBobotModel criteria) {
        try {
            if (criteria.getK1().equals(criteria.getK2())) {
                query = "UPDATE bobot_kriteria "
                        + "SET bobot = ?"
                        + "WHERE k1 = ? and k2 = ?";

                pstmt = dbConnection.prepareStatement(query);
                pstmt.setDouble(1, criteria.getBobot());
                pstmt.setString(2, criteria.getK1());
                pstmt.setString(3, criteria.getK2());

                return pstmt.executeUpdate();
            } else {

                query = "UPDATE bobot_kriteria "
                        + "SET bobot = ?"
                        + "WHERE k1 = ? and k2 = ?";

                pstmt = dbConnection.prepareStatement(query);
                pstmt.setDouble(1, criteria.getBobot());
                pstmt.setString(2, criteria.getK1());
                pstmt.setString(3, criteria.getK2());

                pstmt.executeUpdate();

                query = "UPDATE bobot_kriteria "
                        + "SET bobot = ?"
                        + "WHERE k1 = ? and k2 = ?";

                pstmt = dbConnection.prepareStatement(query);
                pstmt.setDouble(1, 1 / criteria.getBobot());
                pstmt.setString(2, criteria.getK2());
                pstmt.setString(3, criteria.getK1());

                return pstmt.executeUpdate();

            }
        } catch (SQLException e) {
            // e.printStackTrace();
            System.out.println(e);
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }
    }

}
