package com.grocery.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ErrorResponse {
    String message;
    int errorCode;
}
