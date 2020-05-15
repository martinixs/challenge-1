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

import static com.zenmode.test_ru_bank.util.Constants.SCHEMA_NAME;

@Service
@Slf4j
public class ValidationServiceImpl implements ValidationService {

    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private List<String> validationErrors = new ArrayList<>(0);

    @Override
    public String validate(Request dataRequest) {

        log.debug("Start validate");
        jabxValidate(dataRequest);
        manualValidation(dataRequest.getPerson());
        log.debug("Validation is passed");

        return validationErrors.isEmpty() ? Status.SUCCESS : Status.ERROR;
    }

    private void manualValidation(Person person) {
        log.debug("Start manual validation");
        validateDateOfIssue(person.getDocIssueDate());
        validatePatronic(person.getPatrName());
        log.debug("Manual validation is passed");
    }

    private void jabxValidate(Request dataRequest) {
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
    }

    private void validateDateOfIssue(String date) {
        if (StringUtils.isNotEmpty(date)) {
            DateTimeFormatter f = DateTimeFormatter.ofPattern(DATE_FORMAT);
            try {
                LocalDate ld = LocalDate.parse(date, f);
                if (ld.isEqual(LocalDate.now())) {
                    log.debug("Date of issue more or equal than current date");
                    validationErrors.add("Date of issue more or equal than current date");
                }
            } catch (DateTimeParseException e) {
                log.error("Can't parse date: {}", date);
                validationErrors.add("Date of issue has wrong format");
            }
        }
    }

    private void validatePatronic(String patronic) {
        if (patronic != null) {
            int length = patronic.length();
            if (length < 2 || length > 30) {
                log.error("Patronic name has wrong length");
                validationErrors.add("Patronic's name has wrong length");
            }
        }
    }
}
