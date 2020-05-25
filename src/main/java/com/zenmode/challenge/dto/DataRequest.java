package com.zenmode.challenge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@JsonTypeName("personaldata")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DataRequest implements Serializable {

    @JsonProperty
    private String clientName;

    @JsonProperty
    private String clientSurname;

    @JsonProperty
    private String clientPatronymic;

    @JsonProperty
    private BigInteger passportSeries;

    @JsonProperty
    private BigInteger passportNumber;

    @JsonProperty
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date passportDateOfIssue;
}
