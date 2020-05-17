package com.zenmode.test_ru_bank.service;

import com.zenmode.test_ru_bank.TestConstants;
import com.zenmode.test_ru_bank.TestRun;
import com.zenmode.test_ru_bank.dto.DataRequest;
import com.zenmode.test_ru_bank.dto.DataResponse;
import com.zenmode.test_ru_bank.util.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class SoapServiceImplTest extends TestRun {

    @Autowired
    private SoapService soapService;

    @Test
    void invokeValidationErrorTest() {
        DataRequest dataRequest = DataRequest.builder()
                .clientName(TestConstants.PERSON_NAME)
                .clientPatronymic(TestConstants.PERSON_MIDDLE_NAME)
                .build();
        DataResponse response = soapService.invoke(dataRequest);

        assertNotNull(response);
        Assertions.assertEquals(Constants.ERROR_RESULT, response.getFile());
    }

    @Test
    void invokeSuccessWithoutPartNameTest() {
        DataRequest dataRequest = DataRequest.builder()
                .clientName(TestConstants.PERSON_NAME)
                .passportDateOfIssue(TestConstants.DOC_DATE_OF_ISSUE)
                .passportNumber(TestConstants.DOC_NUMBER)
                .passportSeries(TestConstants.DOC_SERIES)
                .clientSurname(TestConstants.PERSON_SURNAME)
                .build();
        DataResponse response = soapService.invoke(dataRequest);

        assertNotNull(response);
        Assertions.assertNotEquals(Constants.ERROR_RESULT, response.getFile());
    }

    @Test
    void invokeSuccessTest() {
        DataRequest dataRequest = DataRequest.builder()
                .clientName(TestConstants.PERSON_NAME)
                .clientSurname(TestConstants.PERSON_MIDDLE_NAME)
                .passportDateOfIssue(TestConstants.DOC_DATE_OF_ISSUE)
                .passportNumber(TestConstants.DOC_NUMBER)
                .passportSeries(TestConstants.DOC_SERIES)
                .clientPatronymic(TestConstants.PERSON_MIDDLE_NAME)
                .build();
        DataResponse response = soapService.invoke(dataRequest);

        assertNotNull(response);
        Assertions.assertNotEquals(Constants.ERROR_RESULT, response.getFile());
    }

}