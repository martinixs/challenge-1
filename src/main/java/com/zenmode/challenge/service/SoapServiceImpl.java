package com.zenmode.challenge.service;

import com.zenmode.challenge.dto.DataRequest;
import com.zenmode.challenge.dto.DataResponse;
import com.zenmode.challenge.soap.ws.Person;
import com.zenmode.challenge.soap.ws.Reply;
import com.zenmode.challenge.soap.ws.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.UUID;

@Service
public class SoapServiceImpl implements SoapService {

    @Autowired
    private Jaxb2Marshaller jaxb2Marshaller;

    private WebServiceTemplate webServiceTemplate;

    public SoapServiceImpl(Jaxb2Marshaller jaxb2Marshaller) {
        this.jaxb2Marshaller = jaxb2Marshaller;
        this.webServiceTemplate = new WebServiceTemplate(jaxb2Marshaller);
    }

    @Override
    public DataResponse invoke(DataRequest dataRequest) {
        Reply reply = invokeSOAPService(requestFromJson(dataRequest));
        return replyToJson(reply);
    }

    private Reply invokeSOAPService(Request request) {
        return (Reply) webServiceTemplate.marshalSendAndReceive("http://localhost:8080/ws", request);
    }

    private Request requestFromJson(DataRequest dataRequest) {
        Request request = new Request();
        request.setCorrectionId(UUID.randomUUID().toString());
        request.setPerson(personFromJson(dataRequest));

        return request;
    }

    private Person personFromJson(DataRequest dataRequest) {
        Person person = new Person();

        person.setDocIssueDate(dataRequest.getPassportDateOfIssue());
        person.setDocNumber(dataRequest.getPassportNumber());
        person.setDocSeries(dataRequest.getPassportSeries());
        person.setFirstName(dataRequest.getClientName());
        person.setLastName(dataRequest.getClientSurname());
        person.setPatrName(dataRequest.getClientPatronymic());

        return person;
    }

    private DataResponse replyToJson(Reply reply) {
        return DataResponse.builder()
                .file(reply.getDocument().getDocument())
                .build();
    }
}
