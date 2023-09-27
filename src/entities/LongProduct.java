package entities;

/**
 *
 * @author Bùi Đức Triệu
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
        return super.toString() + String.format("Manufactoring Date: %s, Expiration Date: %s]", manufacturingDate, expirationDate);
    }
}
