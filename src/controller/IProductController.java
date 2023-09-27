/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package controller;

import entities.Product;
import java.util.List;

/**
 *
 * @author bravee06
 */
public interface IProductController {
    List<Product> getListProduct();
    void addProduct(Product p);
    Product updateProduct(Product oldProduct,Product newProduct);
    boolean deleteProduct(Product p);
    void showAllProduct(boolean option);
    void loadData(List<String> data);
}
