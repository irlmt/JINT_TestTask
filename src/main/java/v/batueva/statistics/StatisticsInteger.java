package v.batueva.statistics;

import java.math.BigDecimal;
import java.math.BigInteger;

public class StatisticsInteger {
    private static int counter = 0;
    private static BigInteger maxValue = BigInteger.valueOf(Integer.MIN_VALUE);
    private static BigInteger minValue = BigInteger.valueOf(Integer.MAX_VALUE);
    private static BigInteger sumOfValues = BigInteger.valueOf(0);
    private static BigDecimal averageValue = BigDecimal.valueOf(0);
    public static void addValue(BigInteger value){
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
            averageValue = new BigDecimal(sumOfValues).divide(BigDecimal.valueOf(counter));
        }
        System.out.println("Number of integer values: " + counter);
        System.out.println("Max. integer value: " + maxValue);
        System.out.println("Min. integer value: " + minValue);
        System.out.println("Sum of integer values: " + sumOfValues);
        System.out.println("Average value of integer values: " + averageValue);
    }
    public static void printShortStatistics(){
        System.out.println("Number of integer values: " + counter);
    }
}
