/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package utils;

import java.util.List;

/**
 *
 * @author bravee06
 */
public interface IFileManage {
    void saveToFile(List objList, String fileName);
   
    List<String> loadFromFile(String fileName);
    
}
