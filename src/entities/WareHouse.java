package entities;

import java.util.List;

/**
 *
 * @author Bùi Đức Triệu
 */


public class WareHouse {
    private String code;
    private String time;
    
    private List<Product> listProduct;

    public WareHouse(String code, String time, List<Product> listProduct) {
        this.code = code;
        this.time = time;
        
        this.listProduct = listProduct;
    }

    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Product> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Product> listProduct) {
        this.listProduct = listProduct;
    }

    @Override
    public String toString() {
        String result = code+", "+time+", ";
        
        for(Product p : listProduct){
            String pCode = p.getCode();
            result += (","+pCode);
        }
        return result;
    }
}
