package controller;

import entities.DailyProduct;
import entities.LongProduct;
import entities.Product;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.FileManager;

/**
 *
 * @author Bùi Đức Triệu
 */
public class ProductController implements IProductController{
    
    private List<Product> listProduct;
    private FileManager fileManage;
    private final String PRODUCT_FPATH = "products.dat";
    
    public ProductController(){
        listProduct = new ArrayList<>();
        fileManage = new FileManager();

    }
    
    @Override
    public String getProductFPath() {
        return PRODUCT_FPATH;
    }
    
    @Override
    public List<Product> getListProduct() {
        return listProduct;
    }
    
    @Override
    public void setListProduct(List<Product> listProduct) {
        if (listProduct != null) {
            this.listProduct = new ArrayList<>(listProduct); // Create a new list to avoid modifying the original list
        } else {
            throw new IllegalArgumentException("Product list cannot be null.");
        }
    }
    
    @Override
    public boolean addProduct(Product p) {
        return listProduct.add(p);
    }

    @Override
    public Product updateProduct(Product oldProduct, Product newProduct) {
        String code = newProduct.getCode();
        String type = newProduct.getType();
        String name = newProduct.getName();
        int quantity = newProduct.getQuantity();
        
        if (code.isBlank()) {
            newProduct.setCode(oldProduct.getCode());
        }
        if (name.isBlank()) {
            newProduct.setName(oldProduct.getName());
        }
        if (quantity < 0) {
            newProduct.setQuantity(oldProduct.getQuantity());
        }
        
        // 1 trong 2 dang là 'Long' or 'Daily'
        if (oldProduct instanceof LongProduct && newProduct instanceof LongProduct) {
            LongProduct newLongProduct = (LongProduct) newProduct;
            LongProduct oldLongProduct = (LongProduct) oldProduct;
            if(newLongProduct.getManufacturingDate().isBlank()) {
                newLongProduct.setManufacturingDate(oldLongProduct.getManufacturingDate());
            }
            if(newLongProduct.getExpirationDate().isBlank()) {
                newLongProduct.setExpirationDate(oldLongProduct.getExpirationDate());
            }
        }
        if(oldProduct instanceof DailyProduct && newProduct instanceof DailyProduct){
            DailyProduct newDailyProduct = (DailyProduct) newProduct;
            DailyProduct oldDailyProduct = (DailyProduct) oldProduct;
            if(newDailyProduct.getSize().isBlank()) {
                newDailyProduct.setSize(oldDailyProduct.getSize());
            }
            if(newDailyProduct.getUnit() == -1d) {
                newDailyProduct.setUnit(oldDailyProduct.getUnit()); 
            }
        }
        return newProduct;
    }

    @Override
    public boolean deleteProduct(Product productToDelete) {
        return listProduct.remove(productToDelete);
    }
    
    public void show(List<Product> list){
        for(Product p: list){
            System.out.println(p);
        }
    }

    @Override
    public void showAllProduct(boolean option) {
        // true: file , false: collection 
        if (option) {
            try {
                System.out.println("-----List of all products in collection-----");
                showByFile("product.dat");
            } catch (IOException ex) {
                Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("-----List of all products in product.dat-----");
            showByCollection();
        }
    }
    
    public void showByCollection(){
        show(listProduct);
    }
    
    public void showByFile(String pathFile) throws FileNotFoundException, IOException{
        List<Product> listProductInFile = fileManage.importDataFromFile(pathFile, Product.class);
        show(listProductInFile);
    }
    
    public Product getProductByCode(String code){
        return listProduct.stream()
            .filter(p -> p.getCode().equals(code))
            .findFirst()
            .orElse(null);
    }
}
