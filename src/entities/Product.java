package entities;

import java.io.Serializable;

/**
 *
 * @author Bùi Đức Triệu
 */
public abstract class Product implements Serializable {

    private String productCode;
    private String productName;
    private int quantity;
    private String type;

    public Product() {
    }

    public Product(String productCode, String productName, int quantity, String type) {
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
        this.type = type;
    }

    public String getCode() {
        return productCode;
    }

    public void setCode(String code) {
        this.productCode = code;
    }

    public String getName() {
        return productName;
    }

    public void setName(String name) {
        this.productName = name;
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
        return String.format("Product [Code: %s, Name: %s, Quantity: %s, Type: %s, ", productCode, productName, quantity, type);
    }
}
