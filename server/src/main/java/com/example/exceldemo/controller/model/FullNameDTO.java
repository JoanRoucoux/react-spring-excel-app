package com.example.exceldemo.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class FullNameDTO {

    @Schema(example = "Mr")
    private String civility;

    @Schema(example = "John")
    private String firstname;

    @Schema(example = "Smith")
    private String lastname;

}
