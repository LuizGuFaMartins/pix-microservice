package com.microservice.pixmicroservice.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocDTO {
    private int id;
    private String location;
    private String tipoCob;
}
