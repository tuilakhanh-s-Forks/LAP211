package controller;

import entities.DailyProduct;
import entities.LongProduct;
import entities.Product;
import java.io.FileNotFoundException;
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
public class ProductController implements IProductController{
    
    private List<Product> listProduct;
    private FileManage fileManage;
    
    public ProductController(){
        listProduct = new ArrayList<>();
        fileManage = new FileManage();

    }
    
    @Override
    public List<Product> getListProduct() {
        return listProduct;
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
        int quantity = newProduct.getQuantity();
        
        if (code.isBlank()) {
            newProduct.setCode(oldProduct.getCode());
        }
        if (name.isBlank()) {
            newProduct.setName(oldProduct.getName());
        }
        if (quantity < 0) {
            newProduct.setQuantity(oldProduct.getQuantity());
        }
        
        // 1 trong 2 dang là 'Long' or 'Daily'
        if (oldProduct instanceof LongProduct && newProduct instanceof LongProduct) {
            LongProduct newLongProduct = (LongProduct) newProduct;
            LongProduct oldLongProduct = (LongProduct) oldProduct;
            if(newLongProduct.getManufacturingDate().isBlank()) {
                newLongProduct.setManufacturingDate(oldLongProduct.getManufacturingDate());
            }
            if(newLongProduct.getExpirationDate().isBlank()) {
                newLongProduct.setExpirationDate(oldLongProduct.getExpirationDate());
            }
        }
        if(oldProduct instanceof DailyProduct && newProduct instanceof DailyProduct){
            DailyProduct newDailyProduct = (DailyProduct) newProduct;
            DailyProduct oldDailyProduct = (DailyProduct) oldProduct;
            if(newDailyProduct.getSize().isBlank()) {
                newDailyProduct.setSize(oldDailyProduct.getSize());
            }
            if(newDailyProduct.getUnit() == -1d) {
                newDailyProduct.setUnit(oldDailyProduct.getUnit()); 
            }
        }
        return newProduct;
    }

    @Override
    public boolean deleteProduct(Product productToDelete) {
        return listProduct.remove(productToDelete);
    }
    
    public void show(List<Product> list){
        for(Product p: list){
            System.out.println(p);
        }
    }

    @Override
    public void showAllProduct(boolean option) {
        // true: file , false: collection 
        if (option) {
            try {
                showByFile("product.dat");
            } catch (IOException ex) {
                Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            showByCollection();
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
            String[] data = line.split(",");
            String code = data[0];
            String name = data[1];
            int quanti = Integer.parseInt(data[2].substring(1));
            String type = data[3].substring(1);
            
            Product newProduct;
            
            if (type.equals("Long")) {
                String pDate = data[4];
                String eDate = data[5];
                String sup = data[6];
                newProduct = new LongProduct(pDate,eDate,sup,code,name,quanti,type) {};
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
        return listProduct.stream()
            .filter(p -> p.getCode().equals(code))
            .findFirst()
            .orElse(null);
    }
    
    
    @Override
    public void loadData(List<String> dataFile) {
        for (String line : dataFile) {
            String[] data = line.split(",");       
            String code = data[0];
            String name = data[1];
            int quanti = Integer.parseInt(data[2].substring(1));
            String type = data[3].substring(1);
            
            Product newProduct;
            
            if(type.equals("Long")){
                String pDate = data[4];
                String eDate = data[5];
                String sup = data[6];
                newProduct = new LongProduct(pDate,eDate,sup,code,name,quanti,type) {};
            }else{
                String size = data[5];
                double unit = Double.parseDouble(data[4]);
                newProduct = new DailyProduct(size,unit,code,name,quanti,type);
            }
            
            listProduct.add(newProduct);
        }
    }
}
