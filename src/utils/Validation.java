
package utils;

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
    
    @Override
    public String checkString(String msg, Status status) {
        // vong lap su dung de nguoi dung nhap den khi dung 
        while (true) {
            System.out.println(msg);
            String input = sc.next().trim();
            
            if(status.equals(Status.UPDATE) && input.isBlank()){
                return input;
            }
            // input == null or do dai = 0 => rong 
            if (input == null || input.length() == 0) {
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
            int flag = 0;
            // NHAP ID DE CHECK 
            String id = checkString(msg, status);
            
            boolean isDuplicate = listProduct.stream().anyMatch(item -> item.getCode().equals(id));
            
            if (isDuplicate) {
                System.err.println("ID already exists! Please enter again.");
            } else {
                return id;
            }
        }
    }

    @Override
    public String checkReceiptCodeExist(String msg, List<WareHouse> listWareHouse) {
        while (true) {
            String id = checkString(msg,Status.NONE);
            
            boolean isDuplicate = listWareHouse.stream().anyMatch(item -> item.getCode().equals(id));
            
            if (isDuplicate) {
                System.err.println("Receipt code already exists! Please enter again.");
            } else {
                return id;
            }
        }
    }

    @Override
    public String checkBeforeDate(String msg, Status status) {
        String dateFormat = "MM/dd/yyyy";
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        while (true) {
            String dateStr = checkString(msg,status);
            try {
                sdf.parse(dateStr);
                return dateStr;
            } catch (ParseException e) {
                System.err.println("Incorrect date must input by format MM/dd/yyyy ! Please enter again !");
            }
        }

    }

    @Override
    public String checkAfterDate(String msg, String productionDate, Status status) {
        String dateFormat = "MM/dd/yyyy";
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        while (true) {
            String initDate = checkBeforeDate(msg,status);
            try {
                Date d1 = sdf.parse(initDate);
                Date d2 = sdf.parse(productionDate);
                    
                if (d1.compareTo(d2) < 0) {
                    System.out.println("Expiration date must be after production date. Please enter again.");
                } else {
                    return initDate;
                }
            } catch (ParseException ex) {
                continue;
            }

        }
    }

    @Override
    public String checkType(String msg, Status status) {
        while(true){
            String type = checkString(msg,status);
            
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
            String type = checkString(msg,status);
            
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
            String input_raw = checkString(msg,status);
            
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
            String input_raw = checkString(msg,status);
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
            String input = checkString(msg,Status.NONE);
            
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
            String input = checkString(msg,Status.NONE);
            
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
            String input = checkString(msg,Status.NONE);
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
            String input = checkString(msg,Status.NONE);
            if (input.equalsIgnoreCase("I")) {
                return true;
            } else if (input.equalsIgnoreCase("E")) {
                return false;
            } else {
                System.err.println("Must input I or E to select option");
            }
        }
    }
}


