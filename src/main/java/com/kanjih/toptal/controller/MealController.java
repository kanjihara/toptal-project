package com.kanjih.toptal.controller;

import com.kanjih.toptal.model.Meal;
import com.kanjih.toptal.service.DataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meals")
public class MealController {

    private final DataService dataService;

    public MealController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/first")
    public Meal getFirstMeal() {
        return dataService.getFirstMeal();
    }
}
