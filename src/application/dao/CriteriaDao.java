/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package application.dao;

import application.models.CriteriaModel;
import java.util.List;

/**
 *
 * @author mahasiswa unindra 
 */
public interface CriteriaDao {
    public int insertOne(CriteriaModel criteria);
    public List<CriteriaModel> findAll();
    public int update(CriteriaModel criteria);
    public int delete(int id);
}
