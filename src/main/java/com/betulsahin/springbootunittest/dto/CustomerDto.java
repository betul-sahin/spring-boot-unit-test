package com.betulsahin.springbootunittest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private String firstName;
    private String LastName;
    private String phoneNumber;
    private String identificationNumber;
}
