/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

/**
 *
 * @author Admin
 */
public class UserDAO {

    public UserDAO() {
    }
    
    
    public boolean login(String user_name, String password){
        UserDTO user = getUserByName(user_name);
        if(user != null){
            if(user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
    
    public List<UserDTO> getAllUsers(){
        List<UserDTO> userList = new ArrayList<>();
        String sql = "SELECT user_ID, user_name, password, role, user_fullName, phone, address, gender, email from [User]";

        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                UserDTO user = new UserDTO();
                user.setUser_ID(rs.getInt("user_ID"));
                user.setUser_name(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setUser_fullName(rs.getString("user_fullName"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setGender(rs.getString("gender"));
                user.setEmail(rs.getString("email"));
                
                userList.add(user);
            }
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return userList;
         
    }
    
    public UserDTO getUserByID(int id){
        UserDTO user = null;
        String sql = "SELECT user_ID, user_name, password, role, user_fullName, phone, address, gender, email from [User] WHERE user_ID = ?";

        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                int user_ID = rs.getInt("user_ID");
                String user_name = rs.getString("user_name");
                String password = rs.getString("password");
                String role = rs.getString("role");
                String user_fullName = rs.getString("user_fullName");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String gender = rs.getString("gender");
                String email = rs.getString("email");
                
                user = new UserDTO(user_ID, user_name, password, role, user_fullName, phone, address, gender, email);
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return user;
    }
    
    public UserDTO getUserByName(String name){
        UserDTO user = null;
        String sql = "SELECT user_ID, user_name, password, role, user_fullName, phone, address, gender, email from [User] WHERE user_name = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int user_ID = rs.getInt("user_ID");
                String user_name = rs.getString("user_name");
                String password = rs.getString("password");
                String role = rs.getString("role");
                String user_fullName = rs.getString("user_fullName");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String gender = rs.getString("gender");
                String email = rs.getString("email");

                user = new UserDTO(user_ID, user_name, password, role, user_fullName, phone, address, gender, email);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return user;

    }

    public boolean insertUser(UserDTO user) {
        String sql = "INSERT INTO [User](user_name, password, role, user_fullName, phone, address, gender, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            //ps.setInt(1, user.getUser_ID());
            ps.setString(1, user.getUser_name());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getUser_fullName());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getAddress());
            ps.setString(7, user.getGender());
            ps.setString(8, user.getEmail());

            int rowAffected = ps.executeUpdate();
            return rowAffected > 0;

            

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    
    public UserDTO checkLogin(String user_name, String password){
        String sql = "SELECT user_ID, user_name, password, role, user_fullName, phone, address, gender, email from [User] WHERE user_name = ? and password = ?";

        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user_name);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                UserDTO user = new UserDTO();
                user.setUser_ID(rs.getInt("user_ID"));
                user.setUser_name(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setUser_fullName(rs.getString("user_fullName"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setGender(rs.getString("gender"));
                user.setEmail(rs.getString("email"));
                
                return user;
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
