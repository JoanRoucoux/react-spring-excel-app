package com.example.exceldemo.common.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Person {

    private String civility;
    private String name;
    private String firstName;
    private LocalDate birthDate;
    private String birthCity;
    private String birthDepartment;
    private String birthCountry;
    private String address;
    private String postCode;
    private String city;
    private String country;
    private String phoneCode;
    private String phoneNumber;
    private String emailAddress;

}
