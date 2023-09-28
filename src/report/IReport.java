package report;

import entities.Product;
import java.util.List;
import controller.ProductController;
import controller.WareHouseController;
import utils.FileManager;

/**
 *
 * @author Bùi Đức Triệu
 */
public interface IReport {

    List<Product> showProductExpired(List<Product> listProduct);

    List<Product> showProductSelling(List<Product> listProduct);

    List<Product> showProductRunningOut(List<Product> listProduct);

    List<Product> showReceiptProduct(String code, ProductController productController, WareHouseController wareHouseController, FileManager fm);

}
