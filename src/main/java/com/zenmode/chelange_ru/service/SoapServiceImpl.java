package com.zenmode.chelange_ru.service;

import com.zenmode.chelange_ru.dto.DataRequest;
import com.zenmode.chelange_ru.dto.DataResponse;
import com.zenmode.chelange_ru.soap.ws.Person;
import com.zenmode.chelange_ru.soap.ws.Reply;
import com.zenmode.chelange_ru.soap.ws.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.UUID;


@Service
@Slf4j
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

        Reply reply = invokeSOAPService(fromJson(dataRequest));
        DataResponse response = fromSOAPXml(reply);
        log.debug("Response" + response);
        return response;
    }

    private Reply invokeSOAPService(Request request) {
        log.debug("Invoke SOAP service");
        return (Reply) webServiceTemplate.marshalSendAndReceive("http://localhost:8080/ws", request);
    }

    private Request fromJson(DataRequest dataRequest) {
        Person person = new Person();

        person.setDocIssueDate(dataRequest.getPassportDateOfIssue());
        person.setDocNumber(dataRequest.getPassportNumber());
        person.setDocSeries(dataRequest.getPassportSeries());
        person.setFirstName(dataRequest.getClientName());
        person.setLastName(dataRequest.getClientSurname());
        person.setPatrName(dataRequest.getClientPatronymic());


        Request request = new Request();
        request.setCorrectionId(UUID.randomUUID().toString());
        request.setPerson(person);

        log.debug("Request: {}", request);

        return request;
    }

    private DataResponse fromSOAPXml(Reply reply) {
        return DataResponse.builder()
                .file(reply.getDocument().getDocument())
                .build();
    }


}
