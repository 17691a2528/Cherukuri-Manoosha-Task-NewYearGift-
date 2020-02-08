package com.epam.lab.model.sweets;

public abstract class Sweets {

    private double sugarLevel;

    private double weight;

    public double getSugarLevel() {
        return sugarLevel;
    }

    public double getWeight() {
        return weight;
    }

    public void setSugarLevel(double sugarLevel) {
        this.sugarLevel = sugarLevel;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName() + " " + sugarLevel + " " + weight);
        return sb.toString();
    }

}
