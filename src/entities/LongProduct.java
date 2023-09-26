/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author bravee06
 */
public class LongProduct extends Product {
    private String manufacturingDate;
    private String expirationDate;
    private String supplier;

    public LongProduct() {
    }

    public LongProduct(String manufacturingDate, String expirationDate,String sup, String code, String name, int quantity, String type) {
        super(code, name, quantity, type);
        this.manufacturingDate = manufacturingDate;
        this.expirationDate = expirationDate;
        this.supplier = sup;
    }

    public String getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(String manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return super.toString() + ", " +manufacturingDate+", "+expirationDate+", "+this.supplier; 
    }
    
    //P0001,ABC,2,Long,20/12/2003,10/10/2005
    
    
}
