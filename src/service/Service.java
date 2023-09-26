/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entities.DailyProduct;
import entities.LongProduct;
import entities.Product;
import entities.WareHouse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import manage.ProductManage;
import manage.WareHouseManage;
import report.Report;
import utils.FileManage;
import utils.Status;
import utils.Validation;

/**
 *
 * @author bravee06
 */
public class Service implements IService {

    FileManage fm = new FileManage();
    ProductManage productManage = new ProductManage();
    WareHouseManage warehouseManage = new WareHouseManage();
    Validation valid = new Validation();
    List<Product> listProduct = productManage.listProduct;
    Report report = new Report();

    public Product inputProduct(Status status) {
        // nhap code -> check data 
        String code = valid.checkProductCodeExist("Enter code product: ", listProduct, status);
        String name = valid.checkString("Enter name product: ", status);
        int quanti = valid.checkInt("Enter quanti product", 0, Integer.MAX_VALUE, status);
        String type = valid.checkType("Enter type product: ", status);
        if (type.equals("Daily")) {
            double unit = valid.checkDouble("Enter unit product: ", 0, Double.MAX_VALUE, status);
            String size = valid.checkSize("Enter size product: ", status);
            Product newProduct = new DailyProduct(size, unit, code, name, quanti, type);
            return newProduct;

        } else {
            String pDate = valid.checkBeforeDate("Enter production date: ", status);
            String eDate = valid.checkAfterDate("Enter end date: ", pDate, status);
            String sup = valid.checkString("Enter the supplier: ", status);
            Product newProduct =   new LongProduct(pDate,eDate,sup,code,name,quanti,type);

            return newProduct;
        }
        
    }

    @Override
    public void addProduct() {
        while(true){
             // submenu 
        Product newProduct = inputProduct(Status.ADD);
        // Add the new product to collection.
        productManage.addProduct(newProduct);
        //The application asks to continuous create new product or go back to the main
        if(valid.checkYesOrNo("Do you want to continue to add product in the collection ( Y/N ) ")){
            continue;
        }
        break;
        }
       
        
    }

    @Override
    public void updateProduct() {
        
        //✓ User requires enter the productCode
        String code = valid.checkString("Enter code to update: ", Status.UPDATE);
        // get Product by code 
        Product oldProduct = productManage.getProductByCode(code);
        if(oldProduct.equals(null)){
            System.out.println("Product does not exist in system");
        }else{
            // Otherwise, user can input update information of product to update that product.
            Product newProduct = inputProduct(Status.UPDATE);
            newProduct = productManage.updateProduct(oldProduct, newProduct);
            System.out.println("Information of old product is change be: ");
            System.out.println(newProduct);
            listProduct.remove(oldProduct);
            listProduct.add(oldProduct);
        }
        
                
                
                
    }

    @Override
    public void deleteProduct() {
       //  Before the delete action is executed, the system must show confirm message.
        String code = valid.checkString("Enter code to update: ",Status.DELETE);
        boolean flag = true;
        // he result of the delete action must be shown with success or fail message.
        Product p = productManage.getProductByCode(code);
        if(p.equals(null)){
             System.out.println("Product does not exist in system");
             flag = false;
        }//  only remove the product from the store's list when the import / export information for this product has not been generated.
        else if(warehouseManage.getProductInWareHouse(p) != null){
            System.out.println("Product exist in receipt !");
            flag = false;
        }
        productManage.deleteProduct(p);
        if(flag) System.out.println("Delete Success!");
        else System.out.println("Delete Fail");
       
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
            if(end_code > 999999){
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
            String productCode = valid.checkString("Enter code product: ", Status.ADD);
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
        List<Product> list = report.showProductExpired(listProduct);
        productManage.show(list);
    }

    @Override
    public void showProductSelling() {
         List<Product> list = report.showProductSelling(listProduct);
        productManage.show(list);
    }

    @Override
    public void showProductRunningOut() {
         List<Product> list = report.showProductRunningOut(listProduct);
        productManage.show(list);
    }

    @Override
    public void showReceiptProduct() {
        String code = valid.checkString("Enter code product:", Status.NONE);
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
        fm.saveToFile(listProduct, "product.dat");        
        fm.saveToFile(warehouseManage.getAllReceipt(), "warehouse.dat");
    }
    

}
