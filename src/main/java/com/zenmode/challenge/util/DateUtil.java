package com.zenmode.challenge.util;

import com.zenmode.challenge.exception.PersonValidationException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.zenmode.challenge.util.Constants.DATE_FORMAT;

public enum DateUtil {
    ;

    public static String toString(Date date) {
        if (date != null) {
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            return dateFormat.format(date);
        }

        return null;
    }

    public static Date fromString(String date) {
        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(date);
        } catch (ParseException e) {
            throw new PersonValidationException("Invalid date format: " + date);
        }
    }
}
