/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package controller;

import entities.Product;
import entities.WareHouse;
import java.util.List;

/**
 *
 * @author Bùi Đức Triệu
 */
public interface IWareHouseManage {
    
    // 2.1. Create an import receipt. (50 LOCs)
    // 2.2. Create an export receipt. (100 LOCs)
     void createImportReceipt(WareHouse warehouse) ;
     void createExportReceipt(WareHouse warehouse) ;
     void loadData(List<String> dataFile,ProductController pm);
}
