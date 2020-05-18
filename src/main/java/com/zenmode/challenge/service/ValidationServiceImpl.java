package com.zenmode.challenge.service;

import com.zenmode.challenge.soap.ws.Person;
import com.zenmode.challenge.soap.ws.Request;
import com.zenmode.challenge.util.Status;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.zenmode.challenge.util.Constants.DATE_FORMAT;
import static com.zenmode.challenge.util.Constants.SCHEMA_NAME;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    public String validate(Request dataRequest) {
        if (jaxbValidate(dataRequest).isEmpty() && customValidate(dataRequest.getPerson()).isEmpty()) {
            return Status.SUCCESS;
        }

        return Status.ERROR;
    }

    private List<String> jaxbValidate(Request dataRequest) {
        List<String> validationErrors = new ArrayList<>();

        try {
            Schema schema = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI).newSchema(new ClassPathResource(SCHEMA_NAME).getFile());
            schema.newValidator().validate(new JAXBSource(JAXBContext.newInstance(Request.class), dataRequest));
        } catch (SAXException | JAXBException | IOException e) {
            validationErrors.add("Schema validation has failed");
        }

        return validationErrors;
    }

    private List<String> customValidate(Person person) {
        List<String> validationErrors = new ArrayList<>();

        validationErrors.addAll(validateDateOfIssue(person.getDocIssueDate()));
        validationErrors.addAll(validatePatronymic(person.getPatrName()));

        return validationErrors;
    }

    private List<String> validateDateOfIssue(String date) {
        if (StringUtils.isNotEmpty(date)) {
            try {
                LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
                if (localDate.isAfter(LocalDate.now())) {
                    return Collections.singletonList("Date of issue is after the current date");
                }
            } catch (DateTimeParseException e) {
                return Collections.singletonList("Date of issue has invalid format");
            }
        }
        return Collections.emptyList();
    }

    private List<String> validatePatronymic(String patronymic) {
        if (StringUtils.isNotEmpty(patronymic) && (patronymic.length() < 2 || patronymic.length() > 30)) {
            return Collections.singletonList("Patronymic name has invalid length");
        }

        return Collections.emptyList();
    }
}
