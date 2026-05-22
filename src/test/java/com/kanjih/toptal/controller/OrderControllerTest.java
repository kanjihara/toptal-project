package com.kanjih.toptal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanjih.toptal.model.Order;
import com.kanjih.toptal.model.OrderItem;
import com.kanjih.toptal.service.DataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    DataService dataService;

    private Order order() {
        OrderItem item = new OrderItem();
        item.setProductId(10);
        item.setProductName("Laptop");
        item.setProductCategory("Electronics");
        item.setUnitPrice(999.99);

        Order o = new Order();
        o.setOrderId(1);
        o.setCustomerId(1);
        o.setCustomerName("Alice");
        o.setLoyaltyPoints(10);
        o.setOrderItems(List.of(item));
        return o;
    }

    @Test
    void getAll_returns200WithList() throws Exception {
        when(dataService.getAllOrders()).thenReturn(List.of(order()));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customer_name").value("Alice"));
    }

    @Test
    void getById_whenExists_returns200() throws Exception {
        when(dataService.getOrderById(1)).thenReturn(Optional.of(order()));

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer_name").value("Alice"))
                .andExpect(jsonPath("$.order_items[0].product_name").value("Laptop"));
    }

    @Test
    void getById_whenNotExists_returns404() throws Exception {
        when(dataService.getOrderById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/orders/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_returns201WithBody() throws Exception {
        when(dataService.createOrder(any())).thenReturn(order());

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.order_id").value(1))
                .andExpect(jsonPath("$.customer_name").value("Alice"));
    }

    @Test
    void update_whenExists_returns200() throws Exception {
        when(dataService.updateOrder(eq(1), any())).thenReturn(Optional.of(order()));

        mockMvc.perform(put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer_name").value("Alice"));
    }

    @Test
    void update_whenNotExists_returns404() throws Exception {
        when(dataService.updateOrder(eq(99), any())).thenReturn(Optional.empty());

        mockMvc.perform(put("/orders/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order())))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_whenExists_returns204() throws Exception {
        when(dataService.deleteOrder(1)).thenReturn(true);

        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_whenNotExists_returns404() throws Exception {
        when(dataService.deleteOrder(99)).thenReturn(false);

        mockMvc.perform(delete("/orders/99"))
                .andExpect(status().isNotFound());
    }
}
