package com.faceforward.utils.service;

import java.util.List;

public interface ExcelService {

    List<String> getValuesFromColumn(String columnName,String pathXls) throws Exception;
    
    void printAllExcel(String path);
    
}
