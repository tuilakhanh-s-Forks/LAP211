package report;

import entities.Product;
import entities.WareHouse;
import java.util.List;
import controller.ProductController;
import controller.WareHouseManage;

/**
 *
 * @author Bùi Đức Triệu
 */
public interface IReport {

    List<Product> showProductExpired(List<Product> listProduct);

    List<Product> showProductSelling(List<Product> listProduct);

    List<Product> showProductRunningOut(List<Product> listProduct);

    Product showReceiptProduct(String code,ProductController pm,WareHouseManage whm);

}
