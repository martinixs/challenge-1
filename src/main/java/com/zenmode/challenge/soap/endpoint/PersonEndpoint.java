package com.zenmode.challenge.soap.endpoint;

import com.zenmode.challenge.service.ValidationService;
import com.zenmode.challenge.soap.ws.OutDocument;
import com.zenmode.challenge.soap.ws.Reply;
import com.zenmode.challenge.soap.ws.Request;
import com.zenmode.challenge.util.Constants;
import com.zenmode.challenge.util.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import static com.zenmode.challenge.util.Constants.NAMESPACE_URI;

@Endpoint
@Slf4j
public class PersonEndpoint {

    @Autowired
    private ValidationService validationService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "request")
    @ResponsePayload()
    public Reply getPerson(@RequestPayload Request request, MessageContext messageContext) {
        String status = validationService.validate(request);

        String encodedRequest = Constants.ERROR_RESULT;
        if (status.equals(Status.SUCCESS)) {
            encodedRequest = encodeRequest(messageContext.getRequest());
        }

        OutDocument outDocument = new OutDocument();
        outDocument.setDocument(encodedRequest);

        Reply reply = new Reply();
        reply.setCorrectionId(request.getCorrectionId());
        reply.setStatus(status);
        reply.setDocument(outDocument);

        return reply;
    }

    private String encodeRequest(WebServiceMessage request) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            request.writeTo(outputStream);
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (IOException e) {
            log.error("Base64 encoding has failed", e);
            return Constants.ERROR_RESULT;
        }
    }
}
