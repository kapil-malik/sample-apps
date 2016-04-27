package com.kmalik.sample;

import org.apache.spark.api.java.function.Function;

/**
 * Returns true if a random hit in a square dart board is inside the circle
 */
public final class RandomDart implements Function<Integer, Boolean> {    
    private static final long serialVersionUID = -529486737062653456L;

    private RandomDart(){}
    
    public static RandomDart INSTANCE = new RandomDart();
    
    @Override
     public Boolean call(Integer i) throws Exception {
       final double x = 2 * Math.random() -1;
       final double y = 2 * Math.random() -1;
       return (x*x + y*y) < 1;
     }
   }
