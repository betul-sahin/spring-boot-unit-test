package com.betulsahin.springbootunittest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppErrorResponse {
    private int status;
    private String message;
    private long timestamp;
}
