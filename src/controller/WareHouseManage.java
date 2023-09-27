package controller;

import entities.Product;
import entities.WareHouse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bùi Đức Triệu
 */
public class WareHouseManage implements IWareHouseManage {

    public List<WareHouse> listImport;
    public List<WareHouse> listExport;

    public WareHouseManage() {
        listImport = new ArrayList<>();
        listExport = new ArrayList<>();
    }

    @Override
    public void createImportReceipt(WareHouse warehouse) {
        listImport.add(warehouse);
    }

    @Override
    public void createExportReceipt(WareHouse warehouse) {
        listImport.add(warehouse);
    }

    public Product getProductInWareHouse(Product p) {
        List<WareHouse> allReceipts = new ArrayList<WareHouse>(listImport);
        allReceipts.addAll(listExport);
        
        return allReceipts.stream()
            .flatMap(receipt -> receipt.getListProduct().stream())
            .filter(product -> product.equals(p))
            .findFirst()
            .orElse(null);
    }

    @Override
    public void loadData(List<String> dataFile, ProductController pm) {
        for (String line : dataFile) {
            String[] info = line.split("[, ]");

            String code = info[0].trim();

            String date = info[1].trim();

           
            List<Product> items = new ArrayList<>();
            for (int i = 3; i < info.length; i++) {
                items.add(pm.getProductByCode(info[i].trim()));
            }

            WareHouse w = new WareHouse(code, date, items);
            if (code.charAt(0) == 'I') {
                listImport.add(w);
            } else {
                listExport.add(w);
            }
        }
    }

    public List<WareHouse> getAllReceipt() {
        List<WareHouse> allReceipts = new ArrayList<WareHouse>(listImport);
        allReceipts.addAll(listExport);
        return allReceipts;
    }
}
