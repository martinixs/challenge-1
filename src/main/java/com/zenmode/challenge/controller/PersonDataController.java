package com.zenmode.challenge.controller;

import com.zenmode.challenge.dto.DataRequest;
import com.zenmode.challenge.dto.DataResponse;
import com.zenmode.challenge.service.SoapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PersonDataController {

    @Autowired
    private SoapService soapService;

    @PostMapping(path = "/app/nkbirequest")
    public ResponseEntity<DataResponse> nkbiRequest(@RequestBody DataRequest personData) {
        log.info("REST Request data: " + personData);
        return new ResponseEntity<>(soapService.invoke(personData), HttpStatus.OK);
    }
}
