/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package report;

import entities.Product;
import entities.WareHouse;
import java.util.List;
import manage.ProductManage;
import manage.WareHouseManage;

/**
 *
 * @author bravee06
 */
public interface IReport {

    List<Product> showProductExpired(List<Product> listProduct);

    List<Product> showProductSelling(List<Product> listProduct);

    List<Product> showProductRunningOut(List<Product> listProduct);

    Product showReceiptProduct(String code,ProductManage pm,WareHouseManage whm);

}
