package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private int age;
    private double height;
    private double currentWeight;
    private double goalWeight;
    private int activityLevel;
    private String gender;
    private List<WeightEntry> weightHistory;

    public User(String username, int age, double height, double currentWeight, double goalWeight, int activityLevel, String gender) {
        this.username = username;
        this.age = age;
        this.height = height;
        this.currentWeight = currentWeight;
        this.goalWeight = goalWeight;
        this.activityLevel = activityLevel;
        this.gender = gender;
        this.weightHistory = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public double getHeight() {
        return height;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public double getGoalWeight() {
        return goalWeight;
    }

    public int getActivityLevel() {
        return activityLevel;
    }

    public String getGender() {return gender;}

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public void setGoalWeight(double goalWeight) {
        this.goalWeight = goalWeight;
    }

    public void setActivityLevel(int activityLevel) {
        this.activityLevel = activityLevel;
    }

    public void setGender(String gender) {this.gender = gender;}

    public void addWeightEntry(LocalDate date, double weight) {weightHistory.add(new WeightEntry(date, weight));}

    public List<WeightEntry> getWeightHistory() {return weightHistory;}
}
