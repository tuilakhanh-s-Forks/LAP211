/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manage;

import entities.DailyProduct;
import entities.LongProduct;
import entities.Product;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.FileManage;

/**
 *
 * @author bravee06
 */
public class ProductManage implements IProductManage{
    
    public List<Product> listProduct;
    public FileManage fileManage;
    public ProductManage(){
        listProduct = new ArrayList<>();
        fileManage = new FileManage();

    }
    @Override
    public void addProduct(Product p) {
        listProduct.add(p);
    }

    @Override
    public Product updateProduct(Product oldProduct, Product newProduct) {
        String code = newProduct.getCode();
        String type = newProduct.getType();
        String name = newProduct.getName();
        int quanti = newProduct.getQuantity();
        String codeOld = oldProduct.getCode();
        String typeOld = oldProduct.getType();
        String nameOld = oldProduct.getName();
        int quantiOld = oldProduct.getQuantity();
        
        if(code.isBlank()){
            newProduct.setCode(codeOld);
        }
        if(name.isBlank()){
            newProduct.setName(nameOld);
        }
        if(quanti == -1){
            newProduct.setQuantity(quantiOld);
        }
        if(type.isBlank()){
            newProduct.setType(typeOld);
        }
        // 1 trong 2 dang là 'Long' or 'Daily'
        if(newProduct.getType().equals("Long") && oldProduct.getType().equals("Long") ){
            LongProduct lp = (LongProduct)newProduct;
            LongProduct lpOld = (LongProduct)oldProduct;
            if(lp.getManufacturingDate().isBlank())
            lp.setManufacturingDate(lpOld.getManufacturingDate());
            if(lp.getExpirationDate().isBlank())
            lp.setExpirationDate(lpOld.getExpirationDate());
        }
        if(newProduct.getType().equals("Daily") && oldProduct.getType().equals("Daily")){
            
                DailyProduct lp = (DailyProduct)newProduct;
                DailyProduct lpOld = (DailyProduct)oldProduct;
                if(lp.getSize().isBlank())
                lp.setSize(lpOld.getSize());
            if(lp.getUnit() == -1d)
            lp.setUnit(lpOld.getUnit());
            
            
        }
        
        return newProduct;
    }

    @Override
    public void deleteProduct(Product p) {
        listProduct.remove(p);
    }

    @Override
    public void showAllProduct(boolean option) {
        
        // true: file , false: collection 
        if(option){
            try {
                showByFile("product.dat");
            } catch (IOException ex) {
                Logger.getLogger(ProductManage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            showByCollection();
        }
    }
    public void show(List<Product> list){
        for(Product p: list){
                System.out.println(p);
            }
    }
    public void showByCollection(){
        show(listProduct);
    }
    public void showByFile(String pathFile) throws FileNotFoundException, IOException{
        // P0001,ABC,2,Long,20/12/2003,10/10/2005 
        // P0002,ABC,2,Daily,3.5,Small 
        List<Product> listProductInFile = new ArrayList<>();
        List<String> listData = fileManage.loadFromFile(pathFile);
         for (String line : listData) {
            Product newProduct = null;
            String[] data = line.split(",");
           
           
            String code = data[0];
            String name = data[1];
            int quanti = Integer.parseInt(data[2].substring(1));
            String type = data[3].substring(1);
            if(type.equals("Long")){
                String pDate = data[4];
                String eDate = data[5];
                String sup = data[6];
                newProduct = new LongProduct(pDate,eDate,sup,code,name,quanti,type);
            }else{
                String size = data[5];
                double unit = Double.parseDouble(data[4]);
                newProduct = new DailyProduct(size,unit,code,name,quanti,type);
            }
            listProductInFile.add(newProduct);
        }
         show(listProductInFile);
    }
    
    public Product getProductByCode(String code){
        for(Product p : listProduct ){
            if(p.getCode().equals(code)){
                return p;
            }
        }
        return null;
    }
    
    
    @Override
    public void loadData(List<String> dataFile) {
        for (String line : dataFile) {
            Product newProduct = null;
            String[] data = line.split(",");
           
           
            String code = data[0];
            String name = data[1];
            int quanti = Integer.parseInt(data[2].substring(1));
            String type = data[3].substring(1);
            if(type.equals("Long")){
                String pDate = data[4];
                String eDate = data[5];
                String sup = data[6];
                newProduct = new LongProduct(pDate,eDate,sup,code,name,quanti,type);
            }else{
                String size = data[5];
                double unit = Double.parseDouble(data[4]);
                newProduct = new DailyProduct(size,unit,code,name,quanti,type);
            }
            listProduct.add(newProduct);
        }
    }
       
       
        
    
}
