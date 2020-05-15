package com.zenmode.test_ru_bank.controller;


import com.zenmode.test_ru_bank.dto.DataRequest;
import com.zenmode.test_ru_bank.dto.DataResponse;
import com.zenmode.test_ru_bank.service.SoapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
@Slf4j
public class PersonDataController {

    @Autowired
    private SoapService soapService;

    @PostMapping(path = "/nkbirequest")
    public ResponseEntity<DataResponse> nkbiRequest(@RequestBody DataRequest personData) {
        log.info("Request: {}", personData);
        DataResponse p = soapService.invoke(personData);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }
}
