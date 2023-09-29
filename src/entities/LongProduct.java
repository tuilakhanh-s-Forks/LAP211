package entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Bùi Đức Triệu
 */
public class LongProduct extends Product {

    private Date manufacturingDate;
    private Date expirationDate;
    private String supplier;

    public LongProduct() {
    }

    public LongProduct(Date manufacturingDate, Date expirationDate, String supplier, String code, String name, int quantity, String type) {
        super(code, name, quantity, type);
        this.manufacturingDate = manufacturingDate;
        this.expirationDate = expirationDate;
        this.supplier = supplier;
    }

    public Date getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(Date manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
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
        String pattern = "dd/MM/yyyy";
        DateFormat sdf = new SimpleDateFormat(pattern);
        return super.toString() + String.format("Manufactoring Date: %s, Expiration Date: %s, Supplier: %s]", sdf.format(manufacturingDate), sdf.format(expirationDate), supplier);
    }

    public String toReportString() {
        String pattern = "dd/MM/yyyy";
        DateFormat sdf = new SimpleDateFormat(pattern);
        return String.format("Product [Code: %s, Name: %s, Quantity: %s, Manufactoring Date: %s, Expiration Date: %s]", getCode(), getName(), getQuantity(), sdf.format(manufacturingDate), sdf.format(expirationDate));
    }
}
