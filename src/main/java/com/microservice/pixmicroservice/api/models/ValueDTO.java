package com.microservice.pixmicroservice.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValueDTO {
    private String original;
}
