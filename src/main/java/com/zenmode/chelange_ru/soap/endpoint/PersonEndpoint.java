package com.zenmode.chelange_ru.soap.endpoint;


import com.zenmode.chelange_ru.service.ValidationService;
import com.zenmode.chelange_ru.soap.ws.OutDocument;
import com.zenmode.chelange_ru.soap.ws.Reply;
import com.zenmode.chelange_ru.soap.ws.Request;
import com.zenmode.chelange_ru.util.Constants;
import com.zenmode.chelange_ru.util.Status;
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

import static com.zenmode.chelange_ru.util.Constants.NAMESPACE_URI;

@Endpoint
@Slf4j
public class PersonEndpoint {

    @Autowired
    private ValidationService validationService;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "request")
    @ResponsePayload()
    public Reply getPerson(@RequestPayload Request request, MessageContext messageContext) {


        log.info(request.toString());
        String httpMessage = Constants.ERROR_RESULT;

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
