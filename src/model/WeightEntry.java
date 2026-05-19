package model;

import java.time.LocalDate;

public class WeightEntry {
    private LocalDate date;
    private double weight;

    public WeightEntry(LocalDate date, double weight) {
        this.date = date;
        this.weight = weight;
    }

    public LocalDate getDate() { return date; }
    public double getWeight() { return weight; }
}
