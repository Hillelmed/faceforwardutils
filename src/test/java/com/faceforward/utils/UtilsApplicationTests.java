package com.faceforward.utils;

import com.faceforward.utils.service.ExcelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UtilsApplicationTests {

	@Autowired
	ExcelService excelService;
	@Test
	void contextLoads() {
	}

}
