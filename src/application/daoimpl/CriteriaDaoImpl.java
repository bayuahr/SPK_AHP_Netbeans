/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.daoimpl;

import application.dao.CriteriaDao;
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
public class CriteriaDaoImpl implements CriteriaDao {
    private final Connection dbConnection;
    private PreparedStatement pstmt;
    private ResultSet resultSet;
    private String query;
    
    public CriteriaDaoImpl(){
        dbConnection = DatabaseUtil.getInstance().getConnection();
    }
    
    @Override
    public int insertOne(CriteriaModel criteria) {
        try {
            query = "INSERT INTO criterias(code,name,priority) "
                    + "VALUES (?,?,?)";
            
            pstmt = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, criteria.getCode());
            pstmt.setString(2, criteria.getName());
            pstmt.setString(3, criteria.getPriority());
            
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
            if(pstmt != null){
                pstmt.close();
                pstmt = null;
            }
            if(resultSet != null){
                resultSet.close();
                resultSet = null;
            }   
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CriteriaModel> findAll() {
                try {
            query = "SELECT * FROM criterias";

            pstmt = dbConnection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            List<CriteriaModel> criteriaList = new ArrayList<>();

            while (resultSet.next()) {
                CriteriaModel criteria = new CriteriaModel();
                criteria.setCode(resultSet.getString("code"));
                criteria.setName(resultSet.getString("name"));
                criteria.setPriority(resultSet.getString("priority"));
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
    public int update(CriteriaModel criteria) {
        try {
            query = "UPDATE criterias " 
                    + "SET code = ?, name = ?, priority = ? "
                    + "WHERE code = ?";

            pstmt = dbConnection.prepareStatement(query);
            pstmt.setString(1, criteria.getCode());
            pstmt.setString(2, criteria.getName());
            pstmt.setString(3, criteria.getPriority());
            pstmt.setString(4, criteria.getCode());
            
            return pstmt.executeUpdate();
	} catch (SQLException e) {
            // e.printStackTrace();
            System.out.println(e);
            throw new RuntimeException(e);
        }finally{
            closeStatement();
        }
    }

    @Override
    public int delete(int id) {
        try {
            query = "DELETE FROM criterias";
            pstmt = dbConnection.prepareStatement(query);
            
            return pstmt.executeUpdate();
	} catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        }finally{
            closeStatement();
        }
    }

    
}
