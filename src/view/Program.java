package view;

import entities.TradeType;
import service.Service;

/**
 *
 * @author Bùi Đức Triệu
 */
public class Program {
    public void execute() {
        Service service = new Service();
        service.loadData();
        int choice;
        
        do {
            System.out.println("====== STORE MANAGEMENT ======");
            choice = Menu.getChoice(Menu.STORE_MENU);
            System.out.println("==============================");
            
            switch (choice) {
                case 1:
                    int c2 = Menu.getChoice(Menu.PRODUCT_MENU);
                    switch (c2) {
                        case 1:
                            service.addProduct();
                            break;
                        case 2:
                            service.updateProduct();
                            break;
                        case 3:
                            service.deleteProduct();
                            break;
                        case 4:
                            service.showAllProduct();
                            break;
                    }
                    break;
                case 2:
                    int c3 = Menu.getChoice(Menu.WAREHOUSE_MENU);
                    switch (c3) {
                        case 1:
                            service.inputReceipt(TradeType.IMPORT);
                            break;
                        case 2:
                            service.inputReceipt(TradeType.EXPORT);
                            break;
                    }
                    break;
                case 3:
                    int c4 = Menu.getChoice(Menu.REPORT_MENU);
                    switch (c4) {
                        case 1:
                            service.showProductExpired();
                            break;
                        case 2:
                            service.showProductSelling();
                            break;
                        case 3:
                            service.showProductRunningOut();
                            break;
                        case 4:
                            service.showReceiptProduct();
                            break;
                    }
                    break;
                case 4:
                    service.saveData();
                    break;
            }
            
            System.out.println();
        } while (choice != Menu.STORE_MENU.length);
    }
}
