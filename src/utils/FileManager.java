package utils;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bùi Đức Triệu
 */
public class FileManager implements IFileManager {

    @Override
    public <T> List<T> importDataFromFile(String fName, Class<T> clazz) {
        List<T> dataList = new ArrayList<>();

        try {
            File file = new File(fName);

            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);

                while (true) {
                    try {
                        Object obj = ois.readObject();
                        if (clazz.isInstance(obj)) {
                            dataList.add(clazz.cast(obj));
                        }
                    } catch (ClassNotFoundException | EOFException e) {
                        break; // Exit the loop when the end of file is reached or class is not found
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dataList;
    }

    @Override
    public void saveDataToFile(String fName, List<?> dataList) {
        try {
            File file = new File(fName);

            if (!file.exists()) {
                // Create a new file if it doesn't exist
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for (Object obj : dataList) {
                oos.writeObject(obj);
            }
            System.out.println("Save to file successfully !!!");
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
