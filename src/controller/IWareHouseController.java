/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package controller;

import entities.WareHouse;
import java.util.List;

/**
 *
 * @author Bùi Đức Triệu
 */
public interface IWareHouseController {

    String getWareHouseFPath();

    int getCode();

    List<WareHouse> getWareHouseList();

    void setWareHouseList(List<WareHouse> wareHouseList);

    boolean addReceipt(WareHouse receipt);

    boolean isProductExist(String productCode);
}
