package report;

import entities.LongProduct;
import entities.Product;
import entities.WareHouse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import controller.ProductController;
import controller.WareHouseController;
import utils.FileManager;

/**
 *
 * @author Bùi Đức Triệu
 */
public class Report implements IReport{

    @Override
    public List<Product> showProductExpired(List<Product> listProduct) {
        List<Product> listExpired = new ArrayList<>();
        Date currentTime = new Date();
            
        for(Product product : listProduct){
            if (product instanceof LongProduct) {
                 LongProduct longProduct = (LongProduct) product;

                if(longProduct.getExpirationDate().before(currentTime)){
                    listExpired.add(product);
                }
            }
        }
        return listExpired;
    }

    @Override
    public List<Product> showProductSelling(List<Product> listProduct) {
        List<Product> listSelling = new ArrayList<>();
        Date currentTime = new Date();
            
        for(Product product : listProduct){
            if (product instanceof LongProduct) {
                LongProduct longProduct = (LongProduct) product;
                Date manufacturingDate = longProduct.getManufacturingDate();
                int quantity = longProduct.getQuantity();
            
                if(manufacturingDate.compareTo(currentTime) <= 0 && quantity > 0){
                    listSelling.add(product);
                }
            }
        }
        return listSelling;
    }

    @Override
    public List<Product> showProductRunningOut(List<Product> listProduct) {
        List<Product> listRunningOut = new ArrayList<>();
        for(Product product : listProduct) {
            if (product instanceof LongProduct) {
                LongProduct longProduct = (LongProduct) product;
                int quantity = longProduct.getQuantity();
                Date manufacturingDate = longProduct.getManufacturingDate();
                Date expirationDate = longProduct.getExpirationDate();

                if (quantity < 3 && manufacturingDate.compareTo(expirationDate) <= 0) {
                    listRunningOut.add(longProduct);
                }
            }
        }
       Collections.sort(listRunningOut, Comparator.comparingInt(Product::getQuantity));
       return listRunningOut;
    }

    @Override
    public List<Product> showReceiptProduct(String code, ProductController productController, WareHouseController wareHouseController, FileManager fm) {
        List<Product> productReceipt = new ArrayList<>();
        List<Product> productList = fm.importDataFromFile(productController.getProductFPath(), Product.class);
        List<WareHouse> wareHouseList = fm.importDataFromFile(wareHouseController.getWareHouseFPath(), WareHouse.class);
        
        Product productCheck = productList.stream()
            .filter(p -> p.getCode().equals(code))
            .findFirst()
            .orElse(null);
        
        if (productCheck == null) {
            return null;
        }
        
        for (WareHouse wareHouse : wareHouseList) {
            for (Product product : wareHouse.getListProduct()) {
                if (product.getCode().equals(code)) {
                    productReceipt.add(product);
                }
            }
        }
        return productReceipt;
    }
}
