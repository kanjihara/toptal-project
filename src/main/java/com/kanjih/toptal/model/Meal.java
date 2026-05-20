package com.kanjih.toptal.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Meal {

    private int id;

    @JsonProperty("user_id")
    private String userId;

    private String name;
    private int calories;
    private double protein;

    @JsonProperty("date_consumed")
    private String dateConsumed;

    private String type;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCalories() { return calories; }
    public void setCalories(int calories) { this.calories = calories; }

    public double getProtein() { return protein; }
    public void setProtein(double protein) { this.protein = protein; }

    public String getDateConsumed() { return dateConsumed; }
    public void setDateConsumed(String dateConsumed) { this.dateConsumed = dateConsumed; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return "Meal{id=" + id + ", name='" + name + "', calories=" + calories
                + ", protein=" + protein + ", type='" + type + "', date='" + dateConsumed + "'}";
    }
}
