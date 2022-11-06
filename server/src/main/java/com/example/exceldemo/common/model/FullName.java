package com.example.exceldemo.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class FullName {

    @NotBlank
    private String civility;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

}
