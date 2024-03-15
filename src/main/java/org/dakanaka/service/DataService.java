package org.dakanaka.service;

import java.util.List;

public interface DataService {
    //USERS
    List<String> getAllData(String filePath);

    String getData(String filePath, int index);

    //ADMINS
    boolean saveData(String filePath, String singleData);
    boolean saveAllData(String filePath, List<String> data);

    boolean deleteData(String filePath, String singleData);

    boolean deleteAllData(String filePath);
    boolean containsData(String filePath, String singleData);
}
