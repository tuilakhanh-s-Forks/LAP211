package utils;

import entities.Status;
import entities.Product;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Bùi Đức Triệu
 */
public interface IValidation {
    String inputAndCheckString(String msg, Status status);
  
    String checkProductCodeExist(String msg, List<Product> listProduct, Status status);

    Date checkBeforeDate(String msg, Status status);

    Date checkAfterDate(String msg, Date pd, Status status);

    String checkType(String msg, Status status);
    
    String checkSize(String msg, Status status);
    
    int checkInt(String msg, int min, int max, Status status);

    double checkDouble(String msg, double min, double max, Status status);

    boolean checkYesOrNo(String msg);

    boolean checkUpdateOrDelete(String msg);

    boolean checkFileOrCollection(String msg);
    
    Product checkUpdateProduct(Product oldProduct, Product newProduct);
}
