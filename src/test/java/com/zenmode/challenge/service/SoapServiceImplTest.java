package com.zenmode.challenge.service;

import com.zenmode.challenge.TestConstants;
import com.zenmode.challenge.TestRun;
import com.zenmode.challenge.dto.DataRequest;
import com.zenmode.challenge.dto.DataResponse;
import com.zenmode.challenge.util.Constants;
import com.zenmode.challenge.util.DateUtil;
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
                .passportDateOfIssue(DateUtil.fromString(TestConstants.DOC_DATE_OF_ISSUE))
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
                .passportDateOfIssue(DateUtil.fromString(TestConstants.DOC_DATE_OF_ISSUE))
                .passportNumber(TestConstants.DOC_NUMBER)
                .passportSeries(TestConstants.DOC_SERIES)
                .clientPatronymic(TestConstants.PERSON_MIDDLE_NAME)
                .build();
        DataResponse response = soapService.invoke(dataRequest);

        assertNotNull(response);
        Assertions.assertNotEquals(Constants.ERROR_RESULT, response.getFile());
    }

}