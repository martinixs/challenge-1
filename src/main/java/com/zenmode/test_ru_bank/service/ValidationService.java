package com.zenmode.test_ru_bank.service;

import com.zenmode.test_ru_bank.soap.ws.Request;

public interface ValidationService {

    String validate(Request dataRequest);
}
