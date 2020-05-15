package com.zenmode.test_ru_bank.soap.endpoint;


import com.zenmode.test_ru_bank.service.ValidationService;
import com.zenmode.test_ru_bank.soap.ws.OutDocument;
import com.zenmode.test_ru_bank.soap.ws.Reply;
import com.zenmode.test_ru_bank.soap.ws.Request;
import com.zenmode.test_ru_bank.util.Constants;
import com.zenmode.test_ru_bank.util.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import static com.zenmode.test_ru_bank.util.Constants.NAMESPACE_URI;

@Endpoint
@Slf4j
public class PersonEndpoint {

    @Autowired
    private ValidationService validationService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "request")
    @ResponsePayload
    public Reply getPerson(@RequestPayload Request request) {


         log.info(request.toString());

        String status = validationService.validate(request);

        Reply reply = new Reply();
        reply.setCorrectionId(request.getCorrectionId());

        OutDocument generatedFile = new OutDocument();
        generatedFile.setDocument(Constants.ERROR_RESULT);

        if(status.equals(Status.SUCCESS)) {

        }

        reply.setStatus(status);
        //generatedFile.setDocument();
        reply.setDocument(generatedFile);
        return reply;
    }
}
