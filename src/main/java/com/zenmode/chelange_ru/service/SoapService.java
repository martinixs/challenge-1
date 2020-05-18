package com.zenmode.chelange_ru.service;

import com.zenmode.chelange_ru.dto.DataRequest;
import com.zenmode.chelange_ru.dto.DataResponse;


public interface SoapService {
    DataResponse invoke(DataRequest dataRequest);
}
