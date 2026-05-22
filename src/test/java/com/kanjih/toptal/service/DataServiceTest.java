package com.kanjih.toptal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanjih.toptal.model.Meal;
import com.kanjih.toptal.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class DataServiceTest {

    @TempDir
    Path tempDir;

    private DataService dataService;
    private File mealsFile;
    private File ordersFile;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException {
        mealsFile = tempDir.resolve("meals.json").toFile();
        ordersFile = tempDir.resolve("orders.json").toFile();

        Files.writeString(mealsFile.toPath(),
                "[{\"id\":1,\"user_id\":\"1\",\"name\":\"Pasta\",\"calories\":400," +
                "\"protein\":15.0,\"date_consumed\":\"2024-01-01\",\"type\":\"lunch\"}]");
        Files.writeString(ordersFile.toPath(),
                "[{\"order_id\":1,\"customer_id\":1,\"customer_name\":\"Alice\"," +
                "\"loyalty_points\":10,\"order_items\":[]}]");

        dataService = new DataService();
        ReflectionTestUtils.setField(dataService, "mealsFilePath", mealsFile.getAbsolutePath());
        ReflectionTestUtils.setField(dataService, "ordersFilePath", ordersFile.getAbsolutePath());
        dataService.load();
    }

    // --- Meal tests ---

    @Test
    void getAllMeals_returnsSeededData() {
        assertThat(dataService.getAllMeals()).hasSize(1);
    }

    @Test
    void getMealById_whenExists_returnsMeal() {
        Optional<Meal> result = dataService.getMealById(1);
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Pasta");
    }

    @Test
    void getMealById_whenNotExists_returnsEmpty() {
        assertThat(dataService.getMealById(99)).isEmpty();
    }

    @Test
    void createMeal_assignsIdAndPersistsToFile() throws IOException {
        Meal meal = new Meal();
        meal.setName("Salad");
        meal.setCalories(200);

        Meal created = dataService.createMeal(meal);

        assertThat(created.getId()).isEqualTo(2);
        assertThat(dataService.getAllMeals()).hasSize(2);
        assertThat(mapper.readValue(mealsFile, Meal[].class)).hasSize(2);
    }

    @Test
    void updateMeal_whenExists_updatesAndPersistsToFile() throws IOException {
        Meal updated = new Meal();
        updated.setName("Updated Pasta");
        updated.setCalories(500);

        Optional<Meal> result = dataService.updateMeal(1, updated);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Updated Pasta");
        assertThat(mapper.readValue(mealsFile, Meal[].class)[0].getName()).isEqualTo("Updated Pasta");
    }

    @Test
    void updateMeal_whenNotExists_returnsEmpty() {
        assertThat(dataService.updateMeal(99, new Meal())).isEmpty();
    }

    @Test
    void deleteMeal_whenExists_removesAndPersistsToFile() throws IOException {
        assertThat(dataService.deleteMeal(1)).isTrue();
        assertThat(dataService.getAllMeals()).isEmpty();
        assertThat(mapper.readValue(mealsFile, Meal[].class)).isEmpty();
    }

    @Test
    void deleteMeal_whenNotExists_returnsFalse() {
        assertThat(dataService.deleteMeal(99)).isFalse();
    }

    // --- Order tests ---

    @Test
    void getAllOrders_returnsSeededData() {
        assertThat(dataService.getAllOrders()).hasSize(1);
    }

    @Test
    void getOrderById_whenExists_returnsOrder() {
        Optional<Order> result = dataService.getOrderById(1);
        assertThat(result).isPresent();
        assertThat(result.get().getCustomerName()).isEqualTo("Alice");
    }

    @Test
    void getOrderById_whenNotExists_returnsEmpty() {
        assertThat(dataService.getOrderById(99)).isEmpty();
    }

    @Test
    void createOrder_assignsIdAndPersistsToFile() throws IOException {
        Order order = new Order();
        order.setCustomerName("Bob");

        Order created = dataService.createOrder(order);

        assertThat(created.getOrderId()).isEqualTo(2);
        assertThat(dataService.getAllOrders()).hasSize(2);
        assertThat(mapper.readValue(ordersFile, Order[].class)).hasSize(2);
    }

    @Test
    void updateOrder_whenExists_updatesAndPersistsToFile() throws IOException {
        Order updated = new Order();
        updated.setCustomerName("Updated Alice");

        Optional<Order> result = dataService.updateOrder(1, updated);

        assertThat(result).isPresent();
        assertThat(result.get().getCustomerName()).isEqualTo("Updated Alice");
        assertThat(mapper.readValue(ordersFile, Order[].class)[0].getCustomerName()).isEqualTo("Updated Alice");
    }

    @Test
    void updateOrder_whenNotExists_returnsEmpty() {
        assertThat(dataService.updateOrder(99, new Order())).isEmpty();
    }

    @Test
    void deleteOrder_whenExists_removesAndPersistsToFile() throws IOException {
        assertThat(dataService.deleteOrder(1)).isTrue();
        assertThat(dataService.getAllOrders()).isEmpty();
        assertThat(mapper.readValue(ordersFile, Order[].class)).isEmpty();
    }

    @Test
    void deleteOrder_whenNotExists_returnsFalse() {
        assertThat(dataService.deleteOrder(99)).isFalse();
    }
}
