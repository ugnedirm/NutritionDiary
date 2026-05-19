package model;



public class Day {

    private double water;
    private double activeCalories;
    private double weight;
    private double totalCalorieIntake;

    private MealRepository morning = new MealRepository();
    private MealRepository midDay  = new MealRepository();
    private MealRepository evening = new MealRepository();
}
