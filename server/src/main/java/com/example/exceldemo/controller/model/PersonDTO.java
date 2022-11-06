package com.example.exceldemo.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

    @Schema(example = "01020304")
    private int id;

    private FullNameDTO fullName;

    @Schema(example = "11/22/1995")
    private String birthdate;

    private AddressDTO address;
    private ContactDTO contact;

}
