package v.batueva.statistics.statisticsFloat;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StatisticsFloat {
    static private int counter = 0;
    static private BigDecimal maxValue = BigDecimal.valueOf(Integer.MIN_VALUE);
    static private BigDecimal minValue = BigDecimal.valueOf(Integer.MAX_VALUE);
    static private BigDecimal sumOfValues = BigDecimal.valueOf(0);
    static private BigDecimal averageValue = BigDecimal.valueOf(0);
    public static void addValue(BigDecimal value){
        counter++;
        sumOfValues = sumOfValues.add(value);
        if(value.compareTo(maxValue) > 0){
            maxValue = value;
        }
        if(value.compareTo(minValue) < 0){
            minValue = value;
        }
    }
    public static void printFullStatistics(){
        if(counter!=0){
            averageValue = sumOfValues.divide(BigDecimal.valueOf(counter), 2, RoundingMode.HALF_EVEN);
        }
        System.out.println("Number of float values: " + counter);
        System.out.println("Max. float value: " + maxValue);
        System.out.println("Min. float value: " + minValue);
        System.out.println("Sum of float values: " + sumOfValues);
        System.out.println("Average value of float values: " + averageValue);
    }
    public static void printShortStatistics(){
        System.out.println("Number of Float values: " + counter);
    }
}
