package utils;

import entities.DailyProduct;
import entities.LongProduct;
import entities.Status;
import utils.IValidation;
import java.util.List;
import entities.Product;
import entities.WareHouse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class Validation implements IValidation{

    private Scanner sc = new Scanner(System.in);
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);
    
    @Override
    public String inputAndCheckString(String msg, Status status) {
        // vong lap su dung de nguoi dung nhap den khi dung 
        while (true) {
            System.out.print(msg);
            String input = sc.nextLine().trim();
            
            if(status.equals(Status.UPDATE) && input.isBlank()){
                return input;
            }
            
            if (input.isBlank()) {
                System.err.println("Must input a string not empty !!!");
                System.out.println("Please enter again!");
            } else {
                return input;
            }
        }
    }

    @Override
    public String checkProductCodeExist(String msg, List<Product> listProduct, Status status) {
        while (true) {
            String id = inputAndCheckString(msg, status);
            
            boolean isDuplicate = listProduct.stream().anyMatch(item -> item.getCode().equals(id));
            
            if (isDuplicate) {
                System.err.println("ID already exists! Please enter again.");
            } else {
                return id;
            }
        }
    }

    @Override
    public Date checkBeforeDate(String msg, Status status) {
        DATE_FORMATTER.setLenient(false);
        while (true) {
            String dateStr = inputAndCheckString(msg,status);
            if (dateStr.isBlank()) {
                return null; // Return null if the input is blank
            }
            try {
                Date date = DATE_FORMATTER.parse(dateStr);
                return date;
            } catch (ParseException e) {
                System.err.println("Incorrect date. Please enter a valid date in the format dd/MM/yyyy.");
            }
        }

    }

    @Override
    public Date checkAfterDate(String msg, Date productionDate, Status status) {
        DateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);
        while (true) {
            Date initDate = checkBeforeDate(msg, status);
            if (initDate == null) {
                return null; // Return null if the initial date is blank
            }
            if (initDate.compareTo(productionDate) >= 0) {
                return initDate;
            } else {
                System.out.println("Expiration date must be after production date. Please enter again.");
            }
        }
    }

    @Override
    public String checkType(String msg, Status status) {
        while(true){
            String type = inputAndCheckString(msg,status);
            
            if (!type.equalsIgnoreCase("Daily") && !type.equalsIgnoreCase("Long")) {
                System.err.println("Please enter either 'Daily' or 'Long'. Please input again.");
            } else {
                return type;
            }
        }
    }

    @Override
    public String checkSize(String msg, Status status) {
        while(true){
            String type = inputAndCheckString(msg,status);
            
            if(!((type.equals("Small")) || (type.equals("Medium")) || (type.equals("Large")))){
                System.err.println("Must input 1 in 3 size product is 'Small' or 'Medium' or 'Large' ! Please input again !");
            } else {
                return type;
            }
        }
    }

    @Override
    public int checkInt(String msg, int min, int max, Status status) {
    // vong lap su dung de nguoi dung nhap den khi dung 
        while (true) {
            // allow user input a string 
            String input_raw = inputAndCheckString(msg,status);
            
            if(input_raw.isBlank() && status.equals(Status.UPDATE)){
                return -1;
            }
            
            try {
                // loi nhap sai dinh dang so 
                int input = Integer.parseInt(input_raw);
                // loi nhap ngoai range cho phep
                if (isWithinRange(input, min, max)) {
                    return input;
                } else {
                    System.err.println("Input must be a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.err.println("Must enter a valid number");
                continue;
            }

        }
    }
 
    @Override
    public double checkDouble(String msg, double min, double max, Status status) {
          // vong lap su dung de nguoi dung nhap den khi dung 
        while (true) {

            // allow user input a string 
            String input_raw = inputAndCheckString(msg,status);
            if(input_raw.isBlank() && status.equals(Status.UPDATE)){
                return -1;
            }
            try {
                // loi nhap sai dinh dang so 
                double input = Double.parseDouble(input_raw);
                // loi nhap ngoai range cho phep
                if (isWithinRange(input, min, max)) {
                    return input;
                } else {
                    System.err.println("Input must be a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {

                System.err.println("Must enter a valid number");
                continue;
            }

        }
    }
    
     private <T extends Number> boolean isWithinRange(T value, T min, T max) {
        double doubleValue = value.doubleValue();
        double doubleMin = min.doubleValue();
        double doubleMax = max.doubleValue();

        return doubleValue >= doubleMin && doubleValue <= doubleMax;
    }

    @Override
    public boolean checkYesOrNo(String msg) {
        while (true) {
            String input = inputAndCheckString(msg,Status.NORMAL);
            
            if (input.equalsIgnoreCase("Y")) {
                return true;
            } else if (input.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.err.println("Must input Y or N to select option");
            }
        }    
    }

    @Override
    public boolean checkUpdateOrDelete(String msg) {
        while (true) {
            String input = inputAndCheckString(msg,Status.NORMAL);
            
            if (input.equalsIgnoreCase("U")) {
                return true;
            } else if (input.equalsIgnoreCase("D")) {
                return false;
            } else {
                System.err.println("Must input U or D to select option");
            }
        }
    }

    @Override
    public boolean checkFileOrCollection(String msg) {
        while (true) {
            String input = inputAndCheckString(msg,Status.NORMAL);
            if (input.equalsIgnoreCase("F")) {
                return true;
            } else if (input.equalsIgnoreCase("C")) {
                return false;
            } else {
                System.err.println("Must input F or C to select option");
            }
        }
    }
    
    public boolean checkImportOrExport(String msg) {
        while (true) {
            String input = inputAndCheckString(msg,Status.NORMAL);
            if (input.equalsIgnoreCase("I")) {
                return true;
            } else if (input.equalsIgnoreCase("E")) {
                return false;
            } else {
                System.err.println("Must input I or E to select option");
            }
        }
    }
    
    public Product checkUpdateProduct(Product oldProduct, Product newProduct) {
        String code = newProduct.getCode();
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
            if(newLongProduct.getManufacturingDate() == null) {
                newLongProduct.setManufacturingDate(oldLongProduct.getManufacturingDate());
            }
            if(newLongProduct.getExpirationDate() == null) {
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
}

