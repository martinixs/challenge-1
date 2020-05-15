package com.zenmode.test_ru_bank.util;

import java.util.Base64;

public enum  FileUtils {
    ;
    public static String encode(String soapRequest) {
        try {
            return Base64.getEncoder().encodeToString(soapRequest.getBytes());
        } catch (Exception ex) {
            return Constants.ERROR_RESULT;
        }
    }
}
