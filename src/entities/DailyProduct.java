package entities;

/**
 *
 * @author Bùi Đức Triệu
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
        return super.toString() + String.format("Size: %s, Unit: %,.2f]", size, unit);
    }
}
