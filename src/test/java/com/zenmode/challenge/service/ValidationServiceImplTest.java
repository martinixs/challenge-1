package com.zenmode.challenge.service;

import com.zenmode.challenge.TestConstants;
import com.zenmode.challenge.TestRun;
import com.zenmode.challenge.soap.ws.Person;
import com.zenmode.challenge.soap.ws.Request;
import com.zenmode.challenge.util.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

import static com.zenmode.challenge.TestConstants.TEST_LONG_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidationServiceImplTest extends TestRun {

    @Autowired
    private ValidationService validationService;

    @Test
    public void validationSuccessTest() {
        Person person = getPerson();
        String status = validationService.validate(getRequest(person));
        assertEquals(Status.SUCCESS, status);
    }

    @Test
    public void validatePersonDocNumberTest() {
        Person person = getPerson();
        person.setDocNumber(new BigInteger("1234"));

        assertEquals(Status.ERROR, validationService.validate(getRequest(person)));
    }

    @Test
    public void validatePersonDocSeriesTest() {
        Person person = getPerson();
        person.setDocSeries(new BigInteger("124"));

        assertEquals(Status.ERROR, validationService.validate(getRequest(person)));
    }

    @Test
    public void validationDateTest() {
        Person person = getPerson();
        person.setDocIssueDate("23.12.30");

        assertEquals(Status.ERROR, validationService.validate(getRequest(person)));
    }

    @Test
    public void validateLastNameTest() {
        Person person = getPerson();

        person.setLastName("");
        assertEquals(Status.ERROR, validationService.validate(getRequest(person)));

        person.setLastName("T");
        assertEquals(Status.ERROR, validationService.validate(getRequest(person)));

        person.setLastName(TEST_LONG_NAME);
        assertEquals(Status.ERROR, validationService.validate(getRequest(person)));
    }

    @Test
    public void validateFirstNameTest() {
        Person person = getPerson();

        person.setFirstName("");
        assertEquals(Status.ERROR, validationService.validate(getRequest(person)));

        person.setFirstName("T");
        assertEquals(Status.ERROR, validationService.validate(getRequest(person)));

        person.setFirstName(TEST_LONG_NAME);
        assertEquals(Status.ERROR, validationService.validate(getRequest(person)));
    }

    @Test
    public void validatePartNameTest() {
        Person person = getPerson();

        person.setPatrName("");
        assertEquals(Status.SUCCESS, validationService.validate(getRequest(person)));

        person.setPatrName("T");
        assertEquals(Status.ERROR, validationService.validate(getRequest(person)));

        person.setPatrName(TEST_LONG_NAME);
        assertEquals(Status.ERROR, validationService.validate(getRequest(person)));
    }


    private Person getPerson() {
        Person person = new Person();

        person.setDocNumber(TestConstants.DOC_NUMBER);
        person.setDocSeries(TestConstants.DOC_SERIES);
        person.setDocIssueDate(TestConstants.DOC_DATE_OF_ISSUE);
        person.setFirstName(TestConstants.PERSON_NAME);
        person.setPatrName(TestConstants.PERSON_MIDDLE_NAME);
        person.setLastName(TestConstants.PERSON_SURNAME);

        return person;
    }

    private Request getRequest(Person person) {
        Request request = new Request();

        request.setCorrectionId(TestConstants.CORRECTION_ID);
        request.setPerson(person);

        return request;
    }
}