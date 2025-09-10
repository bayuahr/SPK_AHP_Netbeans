/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package application.dao;

import application.models.CriteriaBobotModel;
import application.models.CriteriaModel;
import java.util.List;

/**
 *
 * @author mahasiswa unindra 
 */
public interface CriteriaBobotDao {
    public int insertOne(CriteriaBobotModel criteria);
    public List<CriteriaBobotModel> findAll();
    public int update(CriteriaBobotModel criteria);
}
