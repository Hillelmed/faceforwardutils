package com.faceforward.utils.service;

import com.faceforward.utils.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class ExcelServiceImpl implements ExcelService {

    @Override
    public List<String> getValuesFromColumn(String columnName, String pathXls) throws ApplicationException {
        try {
            Integer indexList = null;
            List<String> listValues = new ArrayList<>();
            HSSFSheet sheet = getSheetFromFile(new FileInputStream(pathXls));
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    indexList = getIndexNumberByColumnName(row, columnName);
                } else {
                    for (Cell cell : row) {
                        if (cell.getColumnIndex() == indexList) {
                            listValues.add((String) getValueFromCell(cell));
                        }
                    }
                }
            }
            return listValues.isEmpty() ? null : listValues;
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
            throw new ApplicationException(e.getCause());
        }
    }

    @Override
    public void printAllExcel(String pathXls) {
        try {
            HSSFSheet sheet = getSheetFromFile(new FileInputStream(pathXls));
            for (Row row : sheet) {
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING:
                            System.out.print(cell.getStringCellValue() + "\t\t");
                            break;
                        case NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t\t");
                            break;
                        case BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + "\t\t");
                            break;
                        case ERROR:
                            System.err.println(cell.getErrorCellValue() + "\t\t");
                            break;
                        case _NONE:
                            System.out.println("NONE");
                            break;
                        case FORMULA:
                            System.out.println(cell.getCellFormula() + "\t\t");
                            break;
                        case BLANK:
                            System.out.println("BLANK");
                            break;
                        default:
                            break;
                    }
                }
                System.out.println();
            }
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }

    private HSSFSheet getSheetFromFile(FileInputStream fileInputStream) throws ApplicationException {
        try {
            HSSFWorkbook wb = new HSSFWorkbook(fileInputStream);
            return wb.getSheetAt(0);
        } catch (IOException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
        throw new ApplicationException("Error with load xls file");
    }

    private Object getValueFromCell(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return (cell.getStringCellValue());
            case NUMERIC:
                return (cell.getNumericCellValue());
            case BOOLEAN:
                return (cell.getBooleanCellValue());
            case ERROR:
                return (cell.getErrorCellValue());
            case _NONE:
            case BLANK:
                return null;
            case FORMULA:
                return (cell.getCellFormula());
            default:
                break;
        }
        return null;
    }

    private Integer getIndexNumberByColumnName(Row row, String columnName) throws ApplicationException {
        Integer indexList = null;
        for (Cell cell : row) {
            if (cell.toString().equals(columnName)) {
                indexList = cell.getColumnIndex();
                break;
            }
        }
        if (indexList == null) throw new ApplicationException("not found column with name : " + columnName);
        else return indexList;
    }


}
