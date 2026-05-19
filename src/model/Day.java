package model;

public class Day {

    private double water;
    private double activeCalories;
    private double weight;
    private double totalCalorieIntake;

    private MealRepository morning = new MealRepository();
    private MealRepository midDay  = new MealRepository();
    private MealRepository evening = new MealRepository();

    public double getWater() {
        return water;
    }

    public void setWater(double water) {
        this.water = water;
    }

    public double getActiveCalories() {
        return activeCalories;
    }

    public void setActiveCalories(double activeCalories) {
        this.activeCalories = activeCalories;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getTotalCalorieIntake() {
        return totalCalorieIntake;
    }

    public void setTotalCalorieIntake(double totalCalorieIntake) {
        this.totalCalorieIntake = totalCalorieIntake;
    }

    public MealRepository getMorning() {
        return morning;
    }

    public void setMorning(MealRepository morning) {
        this.morning = morning;
    }

    public MealRepository getMidDay() {
        return midDay;
    }

    public void setMidDay(MealRepository midDay) {
        this.midDay = midDay;
    }

    public MealRepository getEvening() {
        return evening;
    }

    public void setEvening(MealRepository evening) {
        this.evening = evening;
    }
}
