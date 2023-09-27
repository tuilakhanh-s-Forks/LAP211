package controller;

import entities.Product;
import java.util.List;

/**
 *
 * @author Bùi Đức Triệu
 */
public interface IProductController {
    List<Product> getListProduct();
    boolean addProduct(Product p);
    Product updateProduct(Product oldProduct,Product newProduct);
    boolean deleteProduct(Product p);
    void showAllProduct(boolean option);
    void loadData(List<String> data);
}
