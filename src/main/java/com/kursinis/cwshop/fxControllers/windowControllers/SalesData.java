package com.kursinis.cwshop.fxControllers.windowControllers;

public class SalesData {
    private int timePeriod; // e.g., could be year, month, day, etc.
    private double salesAmount;

    public SalesData(int timePeriod, double salesAmount) {
        this.timePeriod = timePeriod;
        this.salesAmount = salesAmount;
    }

    public int getTimePeriod() {
        return timePeriod;
    }

    public double getSalesAmount() {
        return salesAmount;
    }



}