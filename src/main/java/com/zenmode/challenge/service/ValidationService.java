package com.zenmode.challenge.service;

import com.zenmode.challenge.soap.ws.Request;

public interface ValidationService {

    String validate(Request dataRequest);
}
