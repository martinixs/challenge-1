package com.zenmode.chelange_ru;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ComponentScan(basePackages = {"com.zenmode.test_ru_bank.service", "com.zenmode.test_ru_bank.config"})
public class TestRun {
}
