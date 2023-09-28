package controller;

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
public class ProductController implements IProductController {

    private List<Product> listProduct; // quan ly danh sach sp;
    private FileManager fileManage; // nhap xuat
    private final String PRODUCT_FPATH = "products.dat";

    public ProductController() {
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
    public void updateProduct(Product oldProduct, Product newProduct) {
        if (oldProduct == null || !listProduct.contains(oldProduct)) {
            System.out.println("Product does not exist in the system");
            return;
        }
        listProduct.remove(oldProduct);
        listProduct.add(newProduct);
    }

    @Override
    public boolean deleteProduct(Product productToDelete) {
        return listProduct.remove(productToDelete);
    }

    public void show(List<Product> list) {
        for (Product p : list) {
            System.out.println(p);
        }
    }

    @Override
    public void showAllProduct(boolean option) {
        // true: file , false: collection 
        if (option) {
            try {
                System.out.println("-----List of all products in product.dat-----");
                showByFile(PRODUCT_FPATH);
            } catch (IOException ex) {
                Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("-----List of all products in collection-----");
            showByCollection();
        }
    }

    public void showByCollection() {
        show(listProduct);
    }

    public void showByFile(String pathFile) throws FileNotFoundException, IOException {
        List<Product> listProductInFile = fileManage.importDataFromFile(pathFile, Product.class);
        show(listProductInFile);
    }

    public Product getProductByCode(String code) {
        if (listProduct.isEmpty()) {
            return null;
        }
        return listProduct.stream()
                .filter(p -> p.getCode().equals(code))
                .findFirst()
                .orElse(null);
        // for (Product p : list) { if(p.getCode == code) return code } return null;
    }
}
