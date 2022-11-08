package com.example.exceldemo.common.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@Document("persons")
public class PersonDocument {

    @Id
    private String id;

    @Valid
    private FullName fullName;

    @NotBlank
    private String birthdate;

    @Valid
    private Address address;

    @Valid
    private Contact contact;

    public static PersonDocument fromExcelRow(ExcelRow excelRow) {
        FullName fullName = new FullName(
                excelRow.getCivility().getValue(),
                excelRow.getFirstname().getValue(),
                excelRow.getLastname().getValue()

        );
        Address address = new Address(
                excelRow.getStreetAddress().getValue(),
                excelRow.getCity().getValue(),
                excelRow.getState().getValue(),
                excelRow.getZipCode().getValue()
        );
        Contact contact = new Contact(
                excelRow.getMobileNumber().getValue(),
                excelRow.getEmailAddress().getValue()
        );
        return builder()
                .fullName(fullName)
                .birthdate(excelRow.getBirthdate().getValue())
                .address(address)
                .contact(contact)
                .build();
    }

}
