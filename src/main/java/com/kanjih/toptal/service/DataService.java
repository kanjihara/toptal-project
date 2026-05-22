package com.kanjih.toptal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanjih.toptal.model.Meal;
import com.kanjih.toptal.model.Order;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DataService {

    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${data.meals-file}")
    private String mealsFilePath;

    @Value("${data.orders-file}")
    private String ordersFilePath;

    private final Map<Integer, Meal> meals = new LinkedHashMap<>();
    private final Map<Integer, Order> orders = new LinkedHashMap<>();
    private final AtomicInteger mealIdSeq = new AtomicInteger();
    private final AtomicInteger orderIdSeq = new AtomicInteger();

    @PostConstruct
    void load() throws IOException {
        for (Meal meal : mapper.readValue(new File(mealsFilePath), Meal[].class)) {
            meals.put(meal.getId(), meal);
            mealIdSeq.set(Math.max(mealIdSeq.get(), meal.getId()));
        }
        for (Order order : mapper.readValue(new File(ordersFilePath), Order[].class)) {
            orders.put(order.getOrderId(), order);
            orderIdSeq.set(Math.max(orderIdSeq.get(), order.getOrderId()));
        }
    }

    // Meal CRUD

    public List<Meal> getAllMeals() {
        return new ArrayList<>(meals.values());
    }

    public Optional<Meal> getMealById(int id) {
        return Optional.ofNullable(meals.get(id));
    }

    public Meal createMeal(Meal meal) {
        meal.setId(mealIdSeq.incrementAndGet());
        meals.put(meal.getId(), meal);
        saveMeals();
        return meal;
    }

    public Optional<Meal> updateMeal(int id, Meal meal) {
        if (!meals.containsKey(id)) return Optional.empty();
        meal.setId(id);
        meals.put(id, meal);
        saveMeals();
        return Optional.of(meal);
    }

    public boolean deleteMeal(int id) {
        boolean removed = meals.remove(id) != null;
        if (removed) saveMeals();
        return removed;
    }

    // Order CRUD

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    public Optional<Order> getOrderById(int id) {
        return Optional.ofNullable(orders.get(id));
    }

    public Order createOrder(Order order) {
        order.setOrderId(orderIdSeq.incrementAndGet());
        orders.put(order.getOrderId(), order);
        saveOrders();
        return order;
    }

    public Optional<Order> updateOrder(int id, Order order) {
        if (!orders.containsKey(id)) return Optional.empty();
        order.setOrderId(id);
        orders.put(id, order);
        saveOrders();
        return Optional.of(order);
    }

    public boolean deleteOrder(int id) {
        boolean removed = orders.remove(id) != null;
        if (removed) saveOrders();
        return removed;
    }

    private void saveMeals() {
        try {
            mapper.writerWithDefaultPrettyPrinter()
                  .writeValue(new File(mealsFilePath), new ArrayList<>(meals.values()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void saveOrders() {
        try {
            mapper.writerWithDefaultPrettyPrinter()
                  .writeValue(new File(ordersFilePath), new ArrayList<>(orders.values()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
