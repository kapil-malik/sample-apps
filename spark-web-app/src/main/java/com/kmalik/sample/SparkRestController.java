package com.kmalik.sample;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 *
 */
@RestController
@RequestMapping(produces = MediaType.TEXT_PLAIN_VALUE)
public class SparkRestController {
  
  private JavaSparkContext sc;
  
  @PostConstruct
  public void init() {
    this.sc = new JavaSparkContext();
  }
  
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/pi", method = GET)
  public String pi(
    @RequestParam(value = "points", defaultValue = "100000", required = false) Long points,
    @RequestParam(value = "slices", defaultValue = "4", required = false) Integer slices) {
    System.out.println("Pi, points = " + points + ", slices = " + slices);
    
    // avoid overflow
    final Integer n =  (int)Math.min(points, Integer.MAX_VALUE);
    
    final Integer[] data = new Integer[n];
    Arrays.fill(data, 0);
    final List<Integer> list = Arrays.asList(data);
    
    final long count = sc.parallelize(list, slices)
                         .filter(RandomDart.INSTANCE)
                         .count();
    
    double pi = 4.0 * count / n;
    
    System.out.println("Pi is roughly " + pi);
    return String.valueOf(pi);
  }
  
  @PreDestroy
  public void exit() {
    this.sc.stop();
  }
  
  /**
   * Returns true if a random hit in a square dart board is inside the circle
   */
  private static class RandomDart implements Function<Integer, Boolean> {    
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
}
