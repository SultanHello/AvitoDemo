package com.example.AvitoDemo.model;

import lombok.Data;

@Data
public class RegisterUser {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String city;

}
