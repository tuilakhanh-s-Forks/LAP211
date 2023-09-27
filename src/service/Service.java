package service;

import entities.DailyProduct;
import entities.LongProduct;
import entities.Product;
import entities.WareHouse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import controller.ProductController;
import controller.WareHouseManage;
import entities.TradeType;
import java.util.Date;
import report.Report;
import utils.FileManage;
import utils.Status;
import utils.Validation;

/**
 *
 * @author Bùi Đức Triệu
 */
public class Service implements IService {

    private final FileManage fm;
    private final ProductController productManage;
    private final WareHouseManage wareHouseManage;
    private final Validation valid;
    private final Report report;

    public Service() {
        fm = new FileManage();
        productManage = new ProductController();
        wareHouseManage = new WareHouseManage();
        valid = new Validation();
        report = new Report();
    }

    @Override
    public void addProduct() {
        while(true){
            Product newProduct = inputProduct(Status.NORMAL);
            // Add the new product to collection.
            if (productManage.addProduct(newProduct)) {
                System.out.println("Successfully add product: " + newProduct);
            }
            //The application asks to continuous create new product or go back to the main
            if(!valid.checkYesOrNo("Do you want to continue to add product in the collection ( Y/N ) ")){
                return;
            }
        }
    }

    @Override
    public void updateProduct() {
        //✓ User requires enter the productCode
        String code = valid.inputAndCheckString("Enter code to update: ", Status.UPDATE);
        // get Product by code
        Product oldProduct = productManage.getProductByCode(code);
        
        if (oldProduct == null) {
            System.out.println("Product does not exist in system");
            return;
        } 
        // Otherwise, user can input update information of product to update that product.
        Product newProduct = inputProduct(Status.UPDATE);
        newProduct = productManage.updateProduct(oldProduct, newProduct);
        System.out.println("Information of old product is change be: ");
        System.out.println(newProduct);
        productManage.deleteProduct(oldProduct);
        productManage.addProduct(oldProduct);
    }

    @Override
    public void deleteProduct() {
       //  Before the delete action is executed, the system must show confirm message.
        String code = valid.inputAndCheckString("Enter code to update: ",Status.NORMAL);
        // he result of the delete action must be shown with success or fail message.
        Product productToDelete = productManage.getProductByCode(code);
        
        if (productToDelete == null){
            System.out.println("Product does not exist in system");
            return;
        }
        //  only remove the product from the store's list when the import / export information for this product has not been generated.
//        boolean productExistsInReceipt = warehouseManage.getProductInWareHouse(productToDelete) != null;
//        if (productExistsInReceipt) {
//            System.out.println("Product exists in a warehouse receipt and cannot be deleted.");
//            return;
//        }
//        if(!valid.checkYesOrNo("Are you sure you want to delete this product? (Y/N): ")){
//            return;
//        }
        // Remove the product from the list
        boolean removalSuccess = productManage.deleteProduct(productToDelete);

        if (removalSuccess) {
            System.out.println("Delete Success!");
        } else {
            System.out.println("Delete Fail");
        }
    }

    @Override
    public void showAllProduct() {
        boolean option = valid.checkFileOrCollection("Do you want show by file or collection ( F / C)");
        productManage.showAllProduct(option);
    }
    
    @Override
    public void inputReceipt(TradeType tradeType){
        List<Product> items = new ArrayList<>();
        do {
            Product importProduct = createProduct(tradeType);
            if (importProduct != null) {
                items.add(importProduct);
            }
        } while (valid.checkYesOrNo("Continue to add product (Y/N)? "));
        int warehouseCode = wareHouseManage.getCode();
        Date currentDate = new Date();
        WareHouse warehouse = new WareHouse(warehouseCode, tradeType, currentDate, items);

        if (wareHouseManage.addReceipt(warehouse)) {
            System.out.println("Successfully added " + tradeType + " receipt with information:");
            System.out.println(warehouse);
        }
    }
    
    @Override
    public void showProductExpired() {
        List<Product> list = report.showProductExpired(productManage.getListProduct());
        productManage.show(list);
    }

    @Override
    public void showProductSelling() {
        List<Product> list = report.showProductSelling(productManage.getListProduct());
        productManage.show(list);
    }

    @Override
    public void showProductRunningOut() {
         List<Product> list = report.showProductRunningOut(productManage.getListProduct());
        productManage.show(list);
    }

    @Override
    public void showReceiptProduct() {
        String code = valid.inputAndCheckString("Enter code product:", Status.NORMAL);
        Product p = report.showReceiptProduct(code, productManage, wareHouseManage);
        System.out.println(p);
    }

    @Override
    public void loadData() {
        productManage.loadData(fm.loadFromFile("product.dat"));
        wareHouseManage.loadData(fm.loadFromFile("warehouse.dat"), productManage);
    }

    @Override
    public void saveData() {
        fm.saveToFile(productManage.getListProduct(), "product.dat");        
        fm.saveToFile(wareHouseManage.getAllReceipt(), "warehouse.dat");
    }
    
    private Product inputProduct(Status status) {
        // nhap code -> check data 
        String code = valid.checkProductCodeExist("Enter code product: ", productManage.getListProduct(), status);
        String name = valid.inputAndCheckString("Enter name product: ", status);
        int quanti = valid.checkInt("Enter quantity product", 0, Integer.MAX_VALUE, status);
        String type = valid.checkType("Enter type product: ", status);
        Product newProduct;
        if (type.equals("Daily")) {
            double unit = valid.checkDouble("Enter unit product: ", 0, Double.MAX_VALUE, status);
            String size = valid.checkSize("Enter size product: ", status);
            return new DailyProduct(size, unit, code, name, quanti, type);

        } else {
            String pDate = valid.checkBeforeDate("Enter production date: ", status);
            String eDate = valid.checkAfterDate("Enter end date: ", pDate, status);
            String sup = valid.inputAndCheckString("Enter the supplier: ", status);
            return new LongProduct(pDate,eDate,sup,code,name,quanti,type);    
        }
    }
    
    private Product createProduct(TradeType tradeType) {
        String productCode = valid.inputAndCheckString("Input product code:", Status.ADD);

        if (productManage.getProductByCode(productCode) == null) {
            if (tradeType == TradeType.EXPORT) {
                System.err.println("Product does not exist! Please enter again.");
                return null;
            } else {
                Product newProduct = inputProduct(Status.ADD);
                if (productManage.addProduct(newProduct)){
                    System.out.println("Successfully added product!");
                    return newProduct;
                } else {
                    return null;
                }
            }
        } else {
            Product existingProduct = productManage.getProductByCode(productCode);
            return updateExistingProduct(existingProduct, tradeType);
        }
    }
    
    private Product updateExistingProduct(Product existingProduct, TradeType tradeType) {
        int newQuantity;
        if (tradeType == TradeType.EXPORT) {
        while (true) {
            newQuantity = valid.checkInt("Input quantity:", 0, Integer.MAX_VALUE, Status.NORMAL);
            if (existingProduct.getQuantity() - newQuantity >= 0) {
                break;
            } else {
                System.err.println("Not enough quantity to export! Please enter again.");
            }
        }
        existingProduct.setQuantity(existingProduct.getQuantity() - newQuantity);
        } else {
            newQuantity = valid.checkInt("Input quantity:", 0, Integer.MAX_VALUE, Status.NORMAL);
            existingProduct.setQuantity(existingProduct.getQuantity() + newQuantity);
        }
        return existingProduct;
    }
   
}
