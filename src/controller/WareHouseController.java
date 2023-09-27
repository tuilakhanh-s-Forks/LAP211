package controller;

import entities.Product;
import entities.WareHouse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bùi Đức Triệu
 */
public class WareHouseController implements IWareHouseController {

    private List<WareHouse> wareHouseList;
    private final String WAREHOUSE_FPATH = "warehouses.dat";

    public WareHouseController() {
        wareHouseList = new ArrayList<>();
    }
    
    public WareHouseController(List<WareHouse> wareHouseList) {
        this.wareHouseList = wareHouseList;
    }
    
    @Override
    public String getWareHouseFPath() {
        return WAREHOUSE_FPATH;
    }
    
    @Override
    public int getCode() {
        return 1000001 + wareHouseList.size();
    }
    
    @Override
    public List<WareHouse> getWareHouseList() {
        return wareHouseList;
    }
    
    @Override
    public void setWareHouseList(List<WareHouse> wareHouseList) {
        if (wareHouseList != null) {
            this.wareHouseList = new ArrayList<>(wareHouseList); // Create a new list to avoid modifying the original list
        } else {
            throw new IllegalArgumentException("Product list cannot be null.");
        }
    }
    
    @Override
    public boolean addReceipt(WareHouse receipt) {
        return wareHouseList.add(receipt);
    }
    
    @Override
    public boolean isProductExist(String productCode) {
        if (wareHouseList.isEmpty()) {
            return false;
        }
        for(WareHouse wareHouse: wareHouseList){
            for(Product product: wareHouse.getListProduct()){
                if(product.getCode().equalsIgnoreCase(productCode)){
                    return true;
                }
            }
        }
        return false;
    }
}
