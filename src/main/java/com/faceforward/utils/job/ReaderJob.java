package com.faceforward.utils.job;

import com.faceforward.utils.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ReaderJob {

    @Autowired
    ExcelService excelService;

    @Value("${columnName}")
    String columnName;
    @Value("${pathXsl}")
    String pathXsl;


    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        excelService.printAllExcel(pathXsl);
        try {
            List<String> values = excelService.getValuesFromColumn(columnName, pathXsl);
            values.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
