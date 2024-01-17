package com.ricardo.chillsrestaurant.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyUserDto
{
    private Integer id;
    private String email;
    private String role;
    private Boolean isEnabled;
    private Boolean isLocked;
    private String name;
    private String primaryPhone;
    private String secondaryPhone;
    private String address;
    private String picture;
}
