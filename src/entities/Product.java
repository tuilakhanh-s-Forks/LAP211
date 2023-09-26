/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author bravee06
 */
public abstract class Product {
    // product code, product name, manufacturing date,expiration date, and other attributes.
    private String code;
    private String name;
    private int quantity;
    private String type;

    public Product() {
    }

    public Product(String code, String name, int quantity, String type) {
        this.code = code;
        this.name = name;
        this.quantity = quantity;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return code+", "+name+", "+quantity+", "+type;
    }
   
    
    
}
