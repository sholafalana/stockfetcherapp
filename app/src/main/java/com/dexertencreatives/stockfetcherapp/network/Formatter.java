package com.dexertencreatives.stockfetcherapp.network;

/**
 * Created by shola on 11/9/2018.
 */

public class Formatter {
    public static double formatter(double num, int digits) {
        double factor = Math.pow(10, digits);
        return ((int) (num * factor)) / factor;
    }
}