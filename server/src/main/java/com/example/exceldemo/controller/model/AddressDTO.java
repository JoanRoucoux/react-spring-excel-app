package com.example.exceldemo.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddressDTO {

    @Schema(example = "2717 Lady Bug Drive")
    private String streetAddress;

    @Schema(example = "Whitestone")
    private String city;

    @Schema(example = "NY")
    private String state;

    @Schema(example = "11357")
    private String zipCode;

}
