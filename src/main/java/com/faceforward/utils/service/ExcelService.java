package com.faceforward.utils.service;

import com.faceforward.utils.exception.ApplicationException;

import java.util.List;

public interface ExcelService {

    List<String> getValuesFromColumn(String columnName,String pathXls) throws ApplicationException;
    
    void printAllExcel(String path);
    
}
