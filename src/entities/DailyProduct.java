/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author bravee06
 */
public class DailyProduct extends Product {
    private String size;
    private double unit;

    public DailyProduct() {
    }

    public DailyProduct(String size, double unit, String code, String name, int quantity, String type) {
        super(code, name, quantity, type);
        this.size = size;
        this.unit = unit;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getUnit() {
        return unit;
    }

    public void setUnit(double unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return super.toString() + ", " +unit+", "+size; 
    }
    
    
}
