package com.faceforward.utils.job;

import com.faceforward.utils.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class ReaderJob {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    ExcelService excelService;


    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        excelService.printAllExcel("D:\\git\\faceforwarddata\\Employee_attendance_record.xls");
        try {
            List<String> values = excelService.getValuesFromColumn("Employee aaID","D:\\git\\faceforwarddata\\Employee_attendance_record.xls");
            values.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("The time is now {}", dateFormat.format(new Date()));
    }

}
