package com.zenmode.chelange_ru.controller;

import com.zenmode.chelange_ru.TestConstants;
import com.zenmode.chelange_ru.TestRun;
import com.zenmode.chelange_ru.dto.DataRequest;
import com.zenmode.chelange_ru.dto.DataResponse;
import com.zenmode.chelange_ru.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class PersonDataControllerTest extends TestRun {

    private static final String uri = "/app/nkbirequest";
    private static final String auth = "YXBpdXNlcjphcGlwd2Q=";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void nkbiRequestBadRequestTest() throws Exception {
        DataRequest dataRequest = DataRequest.builder()
                .clientName(TestConstants.PERSON_NAME)
                .clientPatronymic(TestConstants.PERSON_MIDDLE_NAME)
                .build();

        ResponseEntity<DataResponse> response = restTemplate
                .postForEntity(uri, getHttpEntity(dataRequest, auth), DataResponse.class);
        assertNotNull(response.getBody());
        assertEquals(Constants.ERROR_RESULT, response.getBody().getFile());
    }

    @Test
    void nkbiRequestNotAuthTest() throws Exception {
        DataRequest dataRequest = DataRequest.builder()
                .clientName(TestConstants.PERSON_NAME)
                .clientPatronymic(TestConstants.PERSON_MIDDLE_NAME)
                .build();

        ResponseEntity<DataResponse> response = restTemplate
                .postForEntity(uri, getHttpEntity(dataRequest, null), DataResponse.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void nkbiRequestSuccessTest() throws Exception {
        DataRequest dataRequest = DataRequest.builder()
                .clientName(TestConstants.PERSON_NAME)
                .clientPatronymic(TestConstants.PERSON_MIDDLE_NAME)
                .clientSurname(TestConstants.PERSON_SURNAME)
                .passportDateOfIssue(TestConstants.DOC_DATE_OF_ISSUE)
                .passportNumber(TestConstants.DOC_NUMBER)
                .passportSeries(TestConstants.DOC_SERIES)
                .build();

        ResponseEntity<DataResponse> response = restTemplate
                .postForEntity(uri, getHttpEntity(dataRequest, auth), DataResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotEquals(Constants.ERROR_RESULT, response.getBody().getFile());
    }

    private HttpEntity<DataRequest> getHttpEntity(DataRequest dataRequest, String auth) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        if (StringUtils.isNotEmpty(auth)) {
            headers.setBasicAuth(auth);
        }

        return new HttpEntity<>(dataRequest, headers);
    }
}