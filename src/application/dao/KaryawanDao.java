/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package application.dao;

import application.models.KaryawanModel;
import java.util.List;

public interface KaryawanDao {
    int insertOne(KaryawanModel product);
    List<KaryawanModel> findAll();
    KaryawanModel findByCode(int code);
    int update(KaryawanModel product);
    int delete(int code);
}
