package utils;

import java.util.List;

/**
 *
 * @author Bùi Đức Triệu
 */
public interface IFileManager {
    <T> List<T> importDataFromFile(String fName, Class<T> clazz);
    void saveDataToFile(String fName, List<?> dataList);
}
