package com.zenmode.test_ru_bank;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ComponentScan(basePackages = {"com.zenmode.test_ru_bank.service", "com.zenmode.test_ru_bank.config"})
public class TestRun {
}
