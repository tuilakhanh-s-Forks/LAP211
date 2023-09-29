package service;

import entities.TradeType;

/**
 *
 * @author Bùi Đức Triệu
 */
public interface IService {

    void addProduct();
    void updateProduct();
    void deleteProduct();
    void inputReceipt(TradeType tradeType);
    void showAllProduct();
    void showProductExpired();
    void showProductSelling();
    void showProductRunningOut();
    void showReceiptProduct();
    void loadData();
    void saveData();
}
