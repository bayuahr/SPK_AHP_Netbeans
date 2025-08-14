/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package application.dao;

import application.models.UserModel;

/**
 *
 * @author mahasiswa unindra 
 */
public interface UserDao {
    
    public UserModel findOneByUsernameAndPassword(String username, String password);
    public int insertOne(UserModel user);
    
}
