package com.example.exceldemo.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class Contact {

    @NotBlank
    private String mobileNumber;

    @NotBlank
    private String emailAddress;

}
