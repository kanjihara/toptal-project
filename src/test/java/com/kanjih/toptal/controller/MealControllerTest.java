package com.kanjih.toptal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanjih.toptal.model.Meal;
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

@WebMvcTest(MealController.class)
class MealControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    DataService dataService;

    private Meal meal() {
        Meal m = new Meal();
        m.setId(1);
        m.setName("Pasta");
        m.setCalories(400);
        m.setProtein(15.0);
        return m;
    }

    @Test
    void getAll_returns200WithList() throws Exception {
        when(dataService.getAllMeals()).thenReturn(List.of(meal()));

        mockMvc.perform(get("/meals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Pasta"));
    }

    @Test
    void getById_whenExists_returns200() throws Exception {
        when(dataService.getMealById(1)).thenReturn(Optional.of(meal()));

        mockMvc.perform(get("/meals/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pasta"))
                .andExpect(jsonPath("$.calories").value(400));
    }

    @Test
    void getById_whenNotExists_returns404() throws Exception {
        when(dataService.getMealById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/meals/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_returns201WithBody() throws Exception {
        when(dataService.createMeal(any())).thenReturn(meal());

        mockMvc.perform(post("/meals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(meal())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Pasta"));
    }

    @Test
    void update_whenExists_returns200() throws Exception {
        when(dataService.updateMeal(eq(1), any())).thenReturn(Optional.of(meal()));

        mockMvc.perform(put("/meals/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(meal())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pasta"));
    }

    @Test
    void update_whenNotExists_returns404() throws Exception {
        when(dataService.updateMeal(eq(99), any())).thenReturn(Optional.empty());

        mockMvc.perform(put("/meals/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(meal())))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_whenExists_returns204() throws Exception {
        when(dataService.deleteMeal(1)).thenReturn(true);

        mockMvc.perform(delete("/meals/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_whenNotExists_returns404() throws Exception {
        when(dataService.deleteMeal(99)).thenReturn(false);

        mockMvc.perform(delete("/meals/99"))
                .andExpect(status().isNotFound());
    }
}
