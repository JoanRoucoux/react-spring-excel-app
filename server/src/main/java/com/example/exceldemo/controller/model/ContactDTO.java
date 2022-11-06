package com.example.exceldemo.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ContactDTO {

    @Schema(example = "347-585-0947")
    private String mobileNumber;

    @Schema(example = "john.smith@gmail.com")
    private String emailAddress;

}
