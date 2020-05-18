package com.zenmode.chelange_ru.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigInteger;

@ToString
@JsonTypeName("personaldata")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT ,use = JsonTypeInfo.Id.NAME)
@Getter
@Builder
public class DataRequest {

    @JsonProperty("client_name")
    private String clientName;
    @JsonProperty("client_surname")
    private String clientSurname;
    @JsonProperty("client_patronymic")
    private String clientPatronymic;
    @JsonProperty("passport_series")
    private BigInteger passportSeries;
    @JsonProperty("passport_number")
    private BigInteger passportNumber;
    @JsonProperty("passport_date_of_issue")
    private String passportDateOfIssue;


}
