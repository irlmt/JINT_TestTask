package v.batueva.statistics;

public class StatisticsString {
    static private int counter = 0;
    static private int maxLength = -1;
    static private int minLength = Integer.MAX_VALUE;
    public static void addValue(String value){
        counter++;
        if (maxLength < value.length()){
            maxLength = value.length();
        }
        if (minLength > value.length()){
            minLength = value.length();
        }
    }
    public static void printShortStatistics(){
        System.out.println("Number of string values: " + counter);
    }
    public static void printFullStatistics(){
        System.out.println("Number of string values: " + counter);
        System.out.println("Max. length of string value: " + maxLength);
        System.out.println("Min. length of string value: " + minLength);
    }
}
