package utils;

import java.util.List;

/**
 *
 * @author Bùi Đức Triệu
 */
public interface IFileManage {
    void saveToFile(List<?> objList, String fileName);
    List<String> loadFromFile(String fileName);
}
