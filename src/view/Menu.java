package view;

import entities.Status;
import utils.Validation;

/**
 *
 * @author Bùi Đức Triệu
 */
public class Menu {
    
    public static final String[] STORE_MENU = {
        "Manage products",
        "Manage Warehouse",
        "Report",
        "Store data to files",
        "Close the application"
    };
    
    public static final String[] PRODUCT_MENU = {
        "Add a product",
        "Update product information",
        "Delete product",
        "Show all product"
    };
    
    public static final String[] WAREHOUSE_MENU = {
        "Create an import receipt",
        "Create an export receipt"
    };
    
    public static final String[] REPORT_MENU = {
        "Products that have expired",
        "The products that the store is selling",
        "Products that are running out of stock (sorted in ascending order)",
        "Import/export receipt of a product"
    };
    
    public static int getChoice(String[] menu) {
        Validation valid = new Validation();
        for (int i = 0; i < menu.length; i++)
            System.out.println((i+1) + ". " + menu[i]);
        
        return valid.checkInt("Choose: ", 1, menu.length,Status.NORMAL);
    }
}
