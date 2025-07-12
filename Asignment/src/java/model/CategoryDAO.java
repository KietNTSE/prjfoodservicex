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
 * @author ASUS
 */
public class CategoryDAO {
    private static final String GET_ALL_CATEGORIES = "SELECT category_ID, category_name FROM [Category]";
    private static final String GET_CATEGORIES_BY_ID = "SELECT category_ID, category_name FROM [Category] WHERE category_ID = ?";
    
    public List<CategoryDTO> getAllCategories() {
        List<CategoryDTO> categories = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_CATEGORIES);
            rs = ps.executeQuery();

            while (rs.next()) {
                CategoryDTO category = new CategoryDTO();
                category.setCategory_ID(rs.getInt("category_ID"));
                category.setCategory_name(rs.getString("category_name"));

                categories.add(category);
            }
        } catch (Exception e) {
            System.err.println("Error in getAllCategories(): " + e.getMessage());
            e.printStackTrace();
        } finally{
            closeResources(conn, ps, rs);
        }
        
        return categories;
    }

    
    public CategoryDTO getCategoryByID(String id) {
        CategoryDTO category = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_CATEGORIES_BY_ID);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                category = new CategoryDTO();
                category.setCategory_ID(rs.getInt("category_ID"));
                category.setCategory_name(rs.getString("category_name"));
            }
        } catch (Exception e) {
            System.err.println("Error in getCategoryByID(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return category;
    }

    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.err.println("Error closing resources: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean isCategoryExists(String id) {
        return getCategoryByID(id) != null;
    }
    
    public List<CategoryDTO> getCategoryByName(String name) {
        List<CategoryDTO> categories = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = GET_ALL_CATEGORIES + " WHERE category_name like ?";

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + name + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                CategoryDTO category = new CategoryDTO();
                category.setCategory_ID(rs.getInt("category_ID"));
                category.setCategory_name(rs.getString("category_name"));

                categories.add(category);
            }
        } catch (Exception e) {
            System.err.println("Error in getCategoryByName(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return categories;
    }

    
}
