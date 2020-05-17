package com.zenmode.test_ru_bank.service;

import com.zenmode.test_ru_bank.dto.DataRequest;
import com.zenmode.test_ru_bank.dto.DataResponse;
import com.zenmode.test_ru_bank.soap.ws.Person;
import com.zenmode.test_ru_bank.soap.ws.Reply;
import com.zenmode.test_ru_bank.soap.ws.Request;
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

        Reply reply = getPerson(request);

        DataResponse response = DataResponse.builder()
                .file(reply.getDocument().getDocument())
                .build();

        log.debug("Response" + response);
        return response;
    }

    private Reply getPerson(Request request) {
        log.debug("Invoke SOAP service");
        return (Reply) webServiceTemplate.marshalSendAndReceive("http://localhost:8080/ws", request);
    }


}
