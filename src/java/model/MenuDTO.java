/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;

/**
 *
 * @author Admin
 */
public class MenuDTO {
    private int menu_id;
    private String food;
    private String image;
    private BigDecimal price;
    private String food_description;
    private String food_status;
    private int category_id;

    public MenuDTO() {
    }

    public MenuDTO(int menu_id, String food, String image, BigDecimal price, String food_description, String food_status, int category_id) {
        this.menu_id = menu_id;
        this.food = food;
        this.image = image;
        this.price = price;
        this.food_description = food_description;
        this.food_status = food_status;
        this.category_id = category_id;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFood_description() {
        return food_description;
    }

    public void setFood_description(String food_description) {
        this.food_description = food_description;
    }

    public String getFood_status() {
        return food_status;
    }

    public void setFood_status(String food_status) {
        this.food_status = food_status;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
    
    
    
}
