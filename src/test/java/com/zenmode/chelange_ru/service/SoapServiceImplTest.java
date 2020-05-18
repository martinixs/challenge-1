package com.zenmode.chelange_ru.service;

import com.zenmode.chelange_ru.TestConstants;
import com.zenmode.chelange_ru.TestRun;
import com.zenmode.chelange_ru.dto.DataRequest;
import com.zenmode.chelange_ru.dto.DataResponse;
import com.zenmode.chelange_ru.util.Constants;
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