package com.kanjih.toptal.controller;

import com.kanjih.toptal.model.Order;
import com.kanjih.toptal.service.DataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final DataService dataService;

    public OrderController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/first")
    public Order getFirstOrder() {
        return dataService.getFirstOrder();
    }
}
