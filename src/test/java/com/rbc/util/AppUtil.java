package com.rbc.util;

import org.apache.commons.lang3.RandomStringUtils;

public class AppUtil {
    public String randowString()
    {
        String generatedString= RandomStringUtils.randomAlphabetic(5);
        return generatedString;
    }

    public String randomNumber()
    {
        String generatedString=RandomStringUtils.randomAlphanumeric(10);
        return generatedString;
    }

    public String randomAlphaNumeric()
    {
        String str=RandomStringUtils.randomAlphabetic(5);
        String num=RandomStringUtils.randomAlphanumeric(10);
        return str+num;
    }
}
