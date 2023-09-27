package report;

import entities.LongProduct;
import entities.Product;
import entities.WareHouse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import controller.ProductController;
import controller.WareHouseManage;

/**
 *
 * @author Bùi Đức Triệu
 */
public class Report implements IReport{

    @Override
    public List<Product> showProductExpired(List<Product> listProduct) {
        List<Product> listEx = new ArrayList<>();
        String dateFormat = "MM/dd/yyyy";
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now); // thoi gian hien tai 
        try {
            Date now_time = sdf.parse(time); // time now 
            
            for(Product p : listProduct){
                if(p.getType().equals("Long")){
                     LongProduct pl = (LongProduct)p;
                     
            Date end_time = sdf.parse(pl.getExpirationDate());
            if(now_time.compareTo(end_time) > 0){
                listEx.add(p);
            }
                }
               
            
        }
        } catch (ParseException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listEx;
        
    }

    @Override
    public List<Product> showProductSelling(List<Product> listProduct) {
        List<Product> listEx = new ArrayList<>();
        String dateFormat = "MM/dd/yyyy";
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now); // thoi gian hien tai 
        try {
            Date now_time = sdf.parse(time); // time now 
            
            for(Product p : listProduct){
                LongProduct pl = (LongProduct)p;
            Date end_time = sdf.parse(pl.getExpirationDate());
            if(now_time.compareTo(end_time) < 0 && pl.getQuantity() > 0){
                listEx.add(p);
            }
            
        }
        } catch (ParseException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listEx;
    }

    @Override
    public List<Product> showProductRunningOut(List<Product> listProduct) {
        List<Product> listEx = new ArrayList<>();
        for(Product p : listProduct){
              
            if(p.getQuantity() < 3){
                listEx.add(p);
            }
          
    }
       Comparator<Product> c = new Comparator<Product>(){
            @Override
            public int compare(Product o1, Product o2) {
                if(o1.getQuantity() > o2.getQuantity()){
                    return 1;
                }else if(o1.getQuantity() < o2.getQuantity()){
                    return -1;
                }
                return 0;
            }
           
       };
       Collections.sort(listEx,c);
       return listEx;
    }

    @Override
    public Product showReceiptProduct(String code,ProductController pm,WareHouseManage whm) {
        Product p = pm.getProductByCode(code);
        return whm.getProductInWareHouse(p);
    }

    
    
}
