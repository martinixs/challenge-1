package com.zenmode.test_ru_bank.service;

import com.zenmode.test_ru_bank.dto.DataRequest;
import com.zenmode.test_ru_bank.dto.DataResponse;


public interface SoapService {
    DataResponse invoke(DataRequest dataRequest);
}
