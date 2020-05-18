package com.zenmode.challenge.service;

import com.zenmode.challenge.dto.DataRequest;
import com.zenmode.challenge.dto.DataResponse;

public interface SoapService {
    DataResponse invoke(DataRequest dataRequest);
}
