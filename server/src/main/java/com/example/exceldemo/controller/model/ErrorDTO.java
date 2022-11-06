package com.example.exceldemo.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {

    @Schema(example = "err_tech")
    private String reason;

    @Schema(example = "Something went wrong!")
    private String message;

    @Schema(example = "2022-11-05T01:32:37.999356")
    private LocalDateTime timestamp;

}
