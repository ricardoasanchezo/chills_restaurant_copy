package com.ricardo.chillsrestaurant.dtos;

import lombok.Data;

@Data
public class RegistrationRequest
{
    private String email;
    private String password;
    private String name;
    private String primaryPhone;
    private String secondaryPhone;
    private String address;
}
