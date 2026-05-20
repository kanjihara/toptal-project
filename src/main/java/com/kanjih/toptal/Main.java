package com.kanjih.toptal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanjih.toptal.model.Meal;
import com.kanjih.toptal.model.Order;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Meal[] mealArray = readJsonResource(mapper, "meals.json", Meal[].class);
        List<Meal> meals = Arrays.asList(mealArray);

        System.out.println("First meal: " + meals.get(0).getName());

        Order[] orderArray = readJsonResource(mapper, "orders.json", Order[].class);
        List<Order> orders = Arrays.asList(orderArray);

        System.out.println("First order by: " + orders.get(0).getCustomerName());
    }

    private static <T> T readJsonResource(ObjectMapper mapper, String resourceName, Class<T> type) throws IOException {
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (inputStream == null) {
                throw new IOException("Resource not found on classpath: " + resourceName);
            }

            return mapper.readValue(inputStream, type);
        }
    }
}