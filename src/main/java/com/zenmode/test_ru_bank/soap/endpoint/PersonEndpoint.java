package com.zenmode.test_ru_bank.soap.endpoint;


import com.zenmode.test_ru_bank.service.ValidationService;
import com.zenmode.test_ru_bank.soap.ws.OutDocument;
import com.zenmode.test_ru_bank.soap.ws.Reply;
import com.zenmode.test_ru_bank.soap.ws.Request;
import com.zenmode.test_ru_bank.util.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import static com.zenmode.test_ru_bank.util.Constants.NAMESPACE_URI;

@Endpoint
@Slf4j
public class PersonEndpoint {

    @Autowired
    private ValidationService validationService;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "request")
    @ResponsePayload()
    public Reply getPerson(@RequestPayload Request request, MessageContext messageContext) {


        log.info(request.toString());
        String httpMessage = "Error";

        String status = validationService.validate(request);

        if (status.equals(Status.SUCCESS)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                messageContext.getRequest().writeTo(outputStream);
                httpMessage = Base64.getEncoder().encodeToString(outputStream.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        OutDocument outDocument = new OutDocument();
        outDocument.setDocument(httpMessage);

        Reply reply = new Reply();
        reply.setCorrectionId(request.getCorrectionId());
        reply.setStatus(status);
        reply.setDocument(outDocument);

        log.debug(reply.toString());
        return reply;
    }
}
