package service;

import entities.DailyProduct;
import entities.LongProduct;
import entities.Product;
import entities.WareHouse;
import java.util.ArrayList;
import java.util.List;
import controller.ProductController;
import controller.WareHouseController;
import entities.TradeType;
import java.util.Date;
import report.Report;
import utils.FileManager;
import entities.Status;
import utils.Validation;

/**
 *
 * @author Bùi Đức Triệu
 */
public class Service implements IService {

    private final FileManager fm;
    private final ProductController productController;
    private final WareHouseController wareHouseController;
    private final Validation valid;
    private final Report report;

    public Service() {
        fm = new FileManager();
        productController = new ProductController();
        wareHouseController = new WareHouseController();
        valid = new Validation();
        report = new Report();
    }

    @Override
    public void addProduct() {
        while(true){
            Product newProduct = inputProduct(Status.NORMAL);
            // Add the new product to collection.
            if (productController.addProduct(newProduct)) {
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
        Product oldProduct = productController.getProductByCode(code);
        
        if (oldProduct == null) {
            System.out.println("Product does not exist in system");
            return;
        } 
        // Otherwise, user can input update information of product to update that product.
        Product newProduct = inputProduct(Status.UPDATE);
        newProduct = productController.updateProduct(oldProduct, newProduct);
        System.out.println("Information of old product is change be: ");
        System.out.println(newProduct);
        productController.deleteProduct(oldProduct);
        productController.addProduct(oldProduct);
    }

    @Override
    public void deleteProduct() {
       //  Before the delete action is executed, the system must show confirm message.
        String code = valid.inputAndCheckString("Enter code to update: ",Status.NORMAL);
        // he result of the delete action must be shown with success or fail message.
        Product productToDelete = productController.getProductByCode(code);
        
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
        boolean removalSuccess = productController.deleteProduct(productToDelete);

        if (removalSuccess) {
            System.out.println("Delete Success!");
        } else {
            System.out.println("Delete Fail");
        }
    }

    @Override
    public void showAllProduct() {
        boolean option = valid.checkFileOrCollection("Do you want show by file or collection (F/C)");
        productController.showAllProduct(option);
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
        int warehouseCode = wareHouseController.getCode();
        Date currentDate = new Date();
        WareHouse warehouse = new WareHouse(warehouseCode, tradeType, currentDate, items);

        if (wareHouseController.addReceipt(warehouse)) {
            System.out.println("Successfully added " + tradeType + " receipt with information:");
            System.out.println(warehouse);
        }
    }
    
    @Override
    public void showProductExpired() {
        List<Product> list = report.showProductExpired(productController.getListProduct());
        productController.show(list);
    }

    @Override
    public void showProductSelling() {
        List<Product> list = report.showProductSelling(productController.getListProduct());
        productController.show(list);
    }

    @Override
    public void showProductRunningOut() {
         List<Product> list = report.showProductRunningOut(productController.getListProduct());
        productController.show(list);
    }

    @Override
    public void showReceiptProduct() {
        String code = valid.inputAndCheckString("Enter code product:", Status.NORMAL);
        Product p = report.showReceiptProduct(code, productController, wareHouseController);
        System.out.println(p);
    }

    @Override
    public void loadData() {
        List<Product> productList = fm.importDataFromFile(productController.getProductFPath(), Product.class);
        List<WareHouse> wareHouseList = fm.importDataFromFile(wareHouseController.getWareHouseFPath(), WareHouse.class);
        
        productController.setListProduct(productList);
        wareHouseController.setWareHouseList(wareHouseList);
    }

    @Override
    public void saveData() {
        fm.saveDataToFile(productController.getProductFPath(), productController.getListProduct());        
        fm.saveDataToFile(wareHouseController.getWareHouseFPath(), wareHouseController.getWareHouseList());
    }
    
    private Product inputProduct(Status status) {
        // nhap code -> check data 
        String code = valid.checkProductCodeExist("Enter code product: ", productController.getListProduct(), status);
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
        String productCode = valid.inputAndCheckString("Input product code:", Status.NORMAL);

        if (productController.getProductByCode(productCode) == null) {
            if (tradeType == TradeType.EXPORT) {
                System.err.println("Product does not exist! Please enter again.");
                return null;
            } else {
                Product newProduct = inputProduct(Status.NORMAL);
                if (productController.addProduct(newProduct)){
                    System.out.println("Successfully added product!");
                    return newProduct;
                } else {
                    return null;
                }
            }
        } else {
            Product existingProduct = productController.getProductByCode(productCode);
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
