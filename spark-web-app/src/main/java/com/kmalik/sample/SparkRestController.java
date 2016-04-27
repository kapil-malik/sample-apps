package com.kmalik.sample;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PreDestroy;

import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping(produces = MediaType.TEXT_PLAIN_VALUE)
public class SparkRestController {

  private Boolean killCtxOnDestroy = false;
  private JavaSparkContext sc;
//  
//  @PostConstruct
//  public void init() {
//    this.sc = new JavaSparkContext();
//  }
//  
  public void setContext(JavaSparkContext sc, Boolean killCtxOnDestroy) {
	this.sc = sc;
	this.killCtxOnDestroy = killCtxOnDestroy;
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
    if (this.killCtxOnDestroy) {
      this.sc.stop();
    }
  }
  
}
