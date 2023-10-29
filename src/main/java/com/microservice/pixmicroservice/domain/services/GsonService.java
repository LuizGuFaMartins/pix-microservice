package com.microservice.pixmicroservice.domain.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.HashMap;

@Service
public class GsonService {
    private final Gson gson;

    public GsonService() {
        this.gson = new Gson();
    }

    public String toJson(Object object) {
        return gson.toJson(object);
    }

    public <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public HashMap<String, Object> fromJsonToMap(String json) {
        Type type = new TypeToken<HashMap<String, Object>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
