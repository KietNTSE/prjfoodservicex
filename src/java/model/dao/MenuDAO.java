/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import model.dto.MenuDTO;
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
public class MenuDAO {

    private static final String GET_ALL_MENUS = "SELECT menu_id, food, image, price, food_description, food_status, category_id FROM [Menu]";
    private static final String GET_MENU_BY_ID = "SELECT menu_id, food, image, price, food_description, food_status, category_id FROM [Menu] WHERE menu_id = ?";
    private static final String CREATE_MENU = "INSERT INTO [Menu](menu_id, food, image, price, food_description, food_status, category_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_MENU = "UPDATE [Menu] SET food = ?, image= ?, price = ?, food_description = ?,  food_status = ?, category_id = ? WHERE menu_id = ?";
    private static final String DELETE_MENU = "DELETE FROM [Menu] WHERE menu_id = ?";

    public List<MenuDTO> getAllMenus() {
        List<MenuDTO> menus = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_MENUS);
            rs = ps.executeQuery();

            while (rs.next()) {
                MenuDTO menu = new MenuDTO();
                menu.setMenu_id(rs.getInt("menu_id"));
                menu.setFood(rs.getString("food"));
                menu.setImage(rs.getString("image"));
                menu.setPrice(rs.getBigDecimal("price"));
                menu.setFood_description(rs.getString("food_description"));
                menu.setFood_status(rs.getString("food_status"));
                menu.setCategory_id(rs.getInt("category_id"));

                menus.add(menu);
            }
        } catch (Exception e) {
            System.err.println("Error in getAllMenus(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return menus;
    }

    public MenuDTO getMenuByID(int id) {
        MenuDTO menu = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_MENU_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                menu = new MenuDTO();
                menu.setMenu_id(rs.getInt("menu_id"));
                menu.setFood(rs.getString("food"));
                menu.setImage(rs.getString("image"));
                menu.setPrice(rs.getBigDecimal("price"));
                menu.setFood_description(rs.getString("food_description"));
                menu.setFood_status(rs.getString("food_status"));
                menu.setCategory_id(rs.getInt("category_id"));
            }
        } catch (Exception e) {
            System.err.println("Error in getMenuByID(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return menu;
    }

    public boolean create(MenuDTO menu) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(CREATE_MENU);

            ps.setInt(1, menu.getMenu_id());
            ps.setString(2, menu.getFood());
            ps.setString(3, menu.getImage());
            ps.setBigDecimal(4, menu.getPrice());
            ps.setString(5, menu.getFood_description());
            ps.setString(6, menu.getFood_status());
            ps.setInt(7, menu.getCategory_id());

            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);

        } catch (Exception e) {
            System.err.println("Error in create(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }

        return success;
    }

    public boolean update(MenuDTO menu) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(UPDATE_MENU);

            ps.setString(1, menu.getFood());
            ps.setString(2, menu.getImage());
            ps.setBigDecimal(3, menu.getPrice());
            ps.setString(4, menu.getFood_description());
            ps.setString(5, menu.getFood_status());
            ps.setInt(6, menu.getCategory_id());
            ps.setInt(7, menu.getMenu_id());

            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);

        } catch (Exception e) {
            System.err.println("Error in update(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }

        return success;
    }

    public boolean delete(String id) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(DELETE_MENU);
            ps.setString(1, id);

            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);

        } catch (Exception e) {
            System.err.println("Error in delete(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }

        return success;
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

    public boolean isMenuExists(int id) {
        return getMenuByID(id) != null;
    }

    public List<MenuDTO> getMenuByName(String name) {
        List<MenuDTO> menus = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = GET_ALL_MENUS + " WHERE food like ?";

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + name + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                MenuDTO menu = new MenuDTO();
                menu.setMenu_id(rs.getInt("menu_id"));
                menu.setFood(rs.getString("food"));
                menu.setImage(rs.getString("image"));
                menu.setPrice(rs.getBigDecimal("price"));
                menu.setFood_description(rs.getString("food_description"));
                menu.setFood_status(rs.getString("food_status"));
                menu.setCategory_id(rs.getInt("category_id"));

                menus.add(menu);
            }
        } catch (Exception e) {
            System.err.println("Error in getMenuByName(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return menus;
    }

// Method để kiểm tra xem menu ID đã tồn tại chưa (cho việc thêm menu mới)
    public boolean isMenuIdExists(int menuId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement("SELECT COUNT(*) FROM [Menu] WHERE menu_id = ?");
            ps.setInt(1, menuId);
            rs = ps.executeQuery();

            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            System.err.println("Error in isMenuIdExists(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return exists;
    }

// Method để lấy menu theo category
    public List<MenuDTO> getMenuByCategory(int categoryId) {
        List<MenuDTO> menus = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = GET_ALL_MENUS + " WHERE category_id = ?";

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, categoryId);
            rs = ps.executeQuery();

            while (rs.next()) {
                MenuDTO menu = new MenuDTO();
                menu.setMenu_id(rs.getInt("menu_id"));
                menu.setFood(rs.getString("food"));
                menu.setImage(rs.getString("image"));
                menu.setPrice(rs.getBigDecimal("price"));
                menu.setFood_description(rs.getString("food_description"));
                menu.setFood_status(rs.getString("food_status"));
                menu.setCategory_id(rs.getInt("category_id"));

                menus.add(menu);
            }
        } catch (Exception e) {
            System.err.println("Error in getMenuByCategory(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return menus;
    }
 
    public List<MenuDTO> getMenusByCategoryAndKeyword(int categoryId, String keyword) {
        List<MenuDTO> menus = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = GET_ALL_MENUS + " WHERE category_id = ? AND food LIKE ?";
        
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, categoryId);
            ps.setString(2, "%" + keyword + "%");
            rs = ps.executeQuery();
            
            while (rs.next()) {
                MenuDTO menu = new MenuDTO();
                menu.setMenu_id(rs.getInt("menu_id"));
                menu.setFood(rs.getString("food"));
                menu.setImage(rs.getString("image"));
                menu.setPrice(rs.getBigDecimal("price"));
                menu.setFood_description(rs.getString("food_description"));
                menu.setFood_status(rs.getString("food_status"));
                menu.setCategory_id(rs.getInt("category_id"));
                menus.add(menu);
            }
        } catch (Exception e) {
            System.err.println("Error in getMenusByCategoryAndKeyword(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        
        return menus;
    }
}
