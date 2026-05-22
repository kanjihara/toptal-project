package com.kanjih.toptal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanjih.toptal.model.Meal;
import com.kanjih.toptal.model.Order;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class DataService {

    private List<Meal> meals;
    private List<Order> orders;

    @PostConstruct
    void load() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        meals = Arrays.asList(readJsonResource(mapper, "meals.json", Meal[].class));
        orders = Arrays.asList(readJsonResource(mapper, "orders.json", Order[].class));
    }

    public Meal getFirstMeal() {
        return meals.get(0);
    }

    public Order getFirstOrder() {
        return orders.get(0);
    }

    private <T> T readJsonResource(ObjectMapper mapper, String resourceName, Class<T> type) throws IOException {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            if (in == null) throw new IOException("Resource not found: " + resourceName);
            return mapper.readValue(in, type);
        }
    }
}
