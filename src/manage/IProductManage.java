/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package manage;

import entities.Product;
import java.util.List;

/**
 *
 * @author bravee06
 */
public interface IProductManage {
    //1.2Add a product. (30 LOCs)
    void addProduct(Product p);
    //1.2. Update product information. (50 LOCs)
    Product updateProduct(Product oldProduct,Product newProduct);
    //1.3. Delete product. (20 LOCs)
    void deleteProduct(Product p);
    //1.4. Show all product. (20 LOCs).
    void showAllProduct(boolean option);
    void loadData(List<String> data);
}
