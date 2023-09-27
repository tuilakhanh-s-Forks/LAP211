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
    private final WareHouseManage warehouseManage;
    private final Validation valid;
    private final Report report;

    public Service() {
        fm = new FileManage();
        productManage = new ProductController();
        warehouseManage = new WareHouseManage();
        valid = new Validation();
        report = new Report();
    }

    @Override
    public void addProduct() {
        while(true){
            Product newProduct = inputProduct(Status.ADD);
            // Add the new product to collection.
            productManage.addProduct(newProduct);
            //The application asks to continuous create new product or go back to the main
            if(!valid.checkYesOrNo("Do you want to continue to add product in the collection ( Y/N ) ")){
                break;
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
        } else {
            // Otherwise, user can input update information of product to update that product.
            Product newProduct = inputProduct(Status.UPDATE);
            newProduct = productManage.updateProduct(oldProduct, newProduct);
            System.out.println("Information of old product is change be: ");
            System.out.println(newProduct);
            productManage.deleteProduct(oldProduct);
            productManage.addProduct(oldProduct);
        }  
    }

    @Override
    public void deleteProduct() {
       //  Before the delete action is executed, the system must show confirm message.
        String code = valid.inputAndCheckString("Enter code to update: ",Status.DELETE);
        // he result of the delete action must be shown with success or fail message.
        Product productToDelete = productManage.getProductByCode(code);
        
        if (productToDelete == null){
            System.out.println("Product does not exist in system");
            return;
        }//  only remove the product from the store's list when the import / export information for this product has not been generated.
        boolean productExistsInReceipt = warehouseManage.getProductInWareHouse(productToDelete) != null;
        if (productExistsInReceipt) {
            System.out.println("Product exists in a warehouse receipt and cannot be deleted.");
            return;
        }
        if(!valid.checkYesOrNo("Are you sure you want to delete this product? (Y/N): ")){
            return;
        }
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
    
    public WareHouse inputReceipt(boolean option){
        // tạo code gồm 1 ký tự ban đầu là I đi kèm với 6 số tiếp theo và số này tự động tăng khi add hóa đơn 
        String code = "";
        if(option){
            code+="I";// I 
       
        }else{
            code+="E";// I 
        }
        int end_code = warehouseManage.listImport.size() + 1; // 12001
            if (end_code > 999999){
                System.out.println("Warehouse Information Full !!!");
            }
            int number_zero = 7 - (end_code+"").length(); // 2 
            String med = "";
            for(int i = 1;i<=number_zero;i++){
                med += "0";
            }
            code += (med + end_code);
            // list hóa đơn 0 hóa đơn -> I000001
            // list của hóa đơn có 12000 hóa đơn -> I0012001
            // size của hóa đơn thì không thể lơn hơn 9999999
        // lấy time hệ thống 
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);
        // tạo một list Product 
        List<Product> listProduct = new ArrayList<>();
        
        while(true){
            String productCode = valid.inputAndCheckString("Enter code product: ", Status.ADD);
            Product p = productManage.getProductByCode(productCode);
            if(p == null){
                System.out.println("Product does not exist in system");
            }else if(listProduct.contains(p)){
                System.out.println("Product does exist in receipt");
            }else{
                listProduct.add(p);
                if(valid.checkYesOrNo("Do you want to continue add product in receipt ( Y / N)")){
                    continue;
                }
                break;
            }
        }
        
        WareHouse importReceipt = new WareHouse(code,time,listProduct);
        return importReceipt;
    }
    
    @Override
    public void createImportReceipt() {
        WareHouse importReceipt = inputReceipt(valid.checkImportOrExport("Do you want create receipt import or export: ( I / E ) "));
        warehouseManage.createImportReceipt(importReceipt);
    }

    @Override
    public void createExportReceipt() {
        WareHouse importReceipt = inputReceipt(valid.checkImportOrExport("Do you want create receipt import or export: ( I / E )"));
        warehouseManage.createImportReceipt(importReceipt);
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
        String code = valid.inputAndCheckString("Enter code product:", Status.NONE);
        Product p = report.showReceiptProduct(code, productManage, warehouseManage);
        System.out.println(p);
    }

    @Override
    public void loadData() {
        productManage.loadData(fm.loadFromFile("product.dat"));
        warehouseManage.loadData(fm.loadFromFile("warehouse.dat"), productManage);
    }

    @Override
    public void saveData() {
        fm.saveToFile(productManage.getListProduct(), "product.dat");        
        fm.saveToFile(warehouseManage.getAllReceipt(), "warehouse.dat");
    }
    
    private Product inputProduct(Status status) {
        // nhap code -> check data 
        String code = valid.checkProductCodeExist("Enter code product: ", productManage.getListProduct(), status);
        String name = valid.inputAndCheckString("Enter name product: ", status);
        int quanti = valid.checkInt("Enter quanti product", 0, Integer.MAX_VALUE, status);
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
}
