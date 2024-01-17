package com.ricardo.chillsrestaurant.utility;

import com.ricardo.chillsrestaurant.dtos.RegistrationRequest;

public class InputValidator
{
    public static final String EMAIL_REGEX =
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public static final String PHONE_NUMBER_REGEX = "^[0-9]{8,14}$";

    public static boolean validateRegistrationRequest(RegistrationRequest request)
    {
        return !request.getName().isBlank() &&
                request.getEmail().matches(EMAIL_REGEX) &&
               !request.getPassword().isBlank() &&
                request.getPrimaryPhone().matches(PHONE_NUMBER_REGEX) &&
                request.getSecondaryPhone().matches(PHONE_NUMBER_REGEX) &&
               !request.getAddress().isBlank();
    }

    public static boolean containsCharacter(String string, char c)
    {
        if (string == null)
            throw new IllegalArgumentException("Input string cannot be null");

        for (char currentChar : string.toCharArray())
        {
            if (currentChar == c) return true;
        }

        return false;
    }
}
