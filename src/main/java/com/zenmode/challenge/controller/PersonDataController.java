package com.zenmode.challenge.controller;

import com.zenmode.challenge.dto.DataRequest;
import com.zenmode.challenge.dto.DataResponse;
import com.zenmode.challenge.service.SoapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonDataController {

    @Autowired
    private SoapService soapService;

    @PostMapping(path = "/app/nkbirequest")
    public ResponseEntity<DataResponse> nkbiRequest(@RequestBody DataRequest personData) {
        return new ResponseEntity<>(soapService.invoke(personData), HttpStatus.OK);
    }
}
