package com.example.exceldemo.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorReason {

    ERR_TECH("err_tech"),
    ERR_FUNC_MAX_UPLOAD_SIZE("err_func_max_upload_size"),
    ERR_FUNC_INVALID_FILE_EXTENSION("err_func_invalid_file_extension"),
    ERR_FUNC_INVALID_ROW_SIZE("err_func_invalid_row_size"),
    ERR_FUNC_INVALID_HEADER("err_func_invalid_header"),
    ERR_FUNC_INVALID_FORMAT("err_func_invalid_format"),
    ERR_FUNC_RESOURCE_NOT_FOUND("err_func_resource_not_found");

    private final String value;

}
