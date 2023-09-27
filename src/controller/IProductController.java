package controller;

import entities.Product;
import java.util.List;

/**
 *
 * @author Bùi Đức Triệu
 */
public interface IProductController {
    String getProductFPath();
    List<Product> getListProduct();
    public void setListProduct(List<Product> listProduct);
    boolean addProduct(Product p);
    Product updateProduct(Product oldProduct,Product newProduct);
    boolean deleteProduct(Product p);
    void showAllProduct(boolean option);
}
