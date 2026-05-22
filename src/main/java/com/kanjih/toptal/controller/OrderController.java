package com.kanjih.toptal.controller;

import com.kanjih.toptal.model.Order;
import com.kanjih.toptal.service.DataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final DataService dataService;

    public OrderController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping
    public List<Order> getAll() {
        return dataService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable int id) {
        return dataService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dataService.createOrder(order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable int id, @RequestBody Order order) {
        return dataService.updateOrder(id, order)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return dataService.deleteOrder(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
