package controller;

import entities.Product;
import entities.WareHouse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bùi Đức Triệu
 */
public class WareHouseManage implements IWareHouseManage {

    private List<WareHouse> wareHouseList;

    public WareHouseManage() {
        wareHouseList = new ArrayList<>();
    }
    
    public WareHouseManage(List<WareHouse> wareHouseList) {
        this.wareHouseList = wareHouseList;
    }
    
    @Override
    public int getCode() {
        return 1000001 + wareHouseList.size();
    }
    
    @Override
    public boolean addReceipt(WareHouse receipt) {
        return wareHouseList.add(receipt);
    }
    
    @Override
    public boolean isProductExist(String productCode) {
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
