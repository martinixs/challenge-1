package com.zenmode.test_ru_bank.service;

import com.zenmode.test_ru_bank.soap.ws.Person;
import com.zenmode.test_ru_bank.soap.ws.Request;
import com.zenmode.test_ru_bank.util.Status;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static com.zenmode.test_ru_bank.util.Constants.DATE_FORMAT;
import static com.zenmode.test_ru_bank.util.Constants.SCHEMA_NAME;

@Service
@Slf4j
public class ValidationServiceImpl implements ValidationService {

    @Override
    public String validate(Request dataRequest) {

        log.debug("Start validate");
        String validationStatus = jabxValidate(dataRequest).isEmpty()
                && manualValidation(dataRequest.getPerson()).isEmpty()
                ? Status.SUCCESS : Status.ERROR;
        log.debug("Validation status: {}", validationStatus);

        return validationStatus;
    }

    private List<String> manualValidation(Person person) {
        List<String> validationErrors = new ArrayList<>(0);
        log.debug("Start manual validation");

        String validateDate = validateDateOfIssue(person.getDocIssueDate());
        if (validateDate != null) {
            validationErrors.add(validateDate);
        }
        String validatePart = validatePatronic(person.getPatrName());

        if (validatePart != null) {
            validationErrors.add(validateDate);
        }
        log.debug("End Manual validation");

        return validationErrors;
    }

    private List<String> jabxValidate(Request dataRequest) {
        List<String> validationErrors = new ArrayList<>(0);
        try {
            log.debug("Start schema validation");
            Schema s = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(new ClassPathResource(SCHEMA_NAME).getFile());
            Validator validator = s.newValidator();
            validator.validate(new JAXBSource(JAXBContext.newInstance(Request.class), dataRequest));
            log.debug("Schema validation is passed");
        } catch (SAXException | JAXBException | IOException e) {
            log.error("Schema validation is failed");
            validationErrors.add("Schema validation is failed");
        }

        return validationErrors;
    }

    private String validateDateOfIssue(String date) {

        if (StringUtils.isNotEmpty(date)) {
            DateTimeFormatter f = DateTimeFormatter.ofPattern(DATE_FORMAT);
            try {
                LocalDate ld = LocalDate.parse(date, f);
                if (ld.isEqual(LocalDate.now())) {
                    log.debug("Date of issue more or equal than current date");
                    return "Date of issue more or equal than current date";
                }
            } catch (DateTimeParseException e) {
                log.error("Can't parse date: {}", date);
                return "Date of issue has wrong format";
            }
        }
        return null;
    }

    private String validatePatronic(String patronic) {
        if (StringUtils.isNotEmpty(patronic)) {
            int length = patronic.length();
            if (length < 2 || length > 30) {
                log.error("Patronic name has wrong length");
                return "Patronic's name has wrong length";
            }
        }
        return null;
    }
}
