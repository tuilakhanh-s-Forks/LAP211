/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author bravee06
 */
public interface IService {
    /*
    
1.1. Add a product
1.2. Update product information.
1.3. Delete product.
1.4. Show all product.
2. Manage Warehouse
2.1. Create an import receipt.
2.2. Create an export receipt.
3. Report
3.1. Products that have expired
3.2. The products that the store is selling.
3.3. Products that are running out of stock (sorted in ascending order).
3.4. Import/export receipt of a product.

    */
    void addProduct();
    void updateProduct();
    void deleteProduct();
    void showAllProduct();
    void createImportReceipt();
    void createExportReceipt();
    void showProductExpired();
    void showProductSelling();
    void showProductRunningOut();
    void showReceiptProduct();
    void loadData();
    void saveData();
}
