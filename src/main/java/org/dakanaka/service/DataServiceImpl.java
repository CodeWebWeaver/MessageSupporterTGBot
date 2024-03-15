package org.dakanaka.service;

import org.dakanaka.repo_imitation.FileUtil;

import java.util.List;

public class DataServiceImpl implements DataService {
    private final FileUtil fileUtil;
    public DataServiceImpl () {
        fileUtil = new FileUtil();
    }
    @Override
    public List<String> getAllData(String filePath) {
        return fileUtil.readLines(filePath);
    }

    @Override
    public String getData(String filePath, int index) {
        return fileUtil.readLine(filePath, index);
    }

    @Override
    public boolean saveData(String filePath, String singleData) {
        return fileUtil.writeLine(filePath, singleData);
    }

    @Override
    public boolean saveAllData(String filePath, List<String> data) {
        return fileUtil.writeLines(filePath, data);
    }

    @Override
    public boolean deleteData(String filePath, String singleData) {
        return fileUtil.removeLine(filePath, singleData);
    }

    @Override
    public boolean deleteAllData(String filePath) {
        return fileUtil.removeAll(filePath);
    }

    @Override
    public boolean containsData(String filePath, String singleData) {
        return fileUtil.containsLine(filePath, singleData);
    }
}
