package com.kanjih.toptal.controller;

import com.kanjih.toptal.model.Meal;
import com.kanjih.toptal.service.DataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meals")
public class MealController {

    private final DataService dataService;

    public MealController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping
    public List<Meal> getAll() {
        return dataService.getAllMeals();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meal> getById(@PathVariable int id) {
        return dataService.getMealById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Meal> create(@RequestBody Meal meal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dataService.createMeal(meal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Meal> update(@PathVariable int id, @RequestBody Meal meal) {
        return dataService.updateMeal(id, meal)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return dataService.deleteMeal(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
