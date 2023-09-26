/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manage;

import entities.Product;
import entities.WareHouse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bravee06
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
        for (WareHouse receipt : allReceipts) {
            List<Product> list = receipt.getListProduct();
            for (Product p1 : list) {
                if (p1.equals(p)) {
                    return p;
                }
            }
        }
        return null;
    }

    @Override
    public void loadData(List<String> dataFile, ProductManage pm) {
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
