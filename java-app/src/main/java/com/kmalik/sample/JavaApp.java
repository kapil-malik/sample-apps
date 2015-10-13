package com.kmalik.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class JavaApp {

  public static void main(String[] args) throws Exception {

    System.out.println("Enter JavaApp : " + JavaApp.class.getName());
    
    Thread.sleep(1000);
    
    System.out.println("Passed " + args.length + " arguments");
    
    final List<String> list = new ArrayList<String>();
    int loopCount = 0;
    long sleep = 0L;
    for (String arg : args) {
      System.out.println(arg);
      final String lArg = arg.toLowerCase(); 
      list.add(lArg);
      
      if (lArg.startsWith("--loop=")) {
        loopCount = Integer.parseInt(lArg.split("=")[1]);
      } else if (lArg.startsWith("--sleep=")) {
        sleep = Long.parseLong(lArg.split("=")[1]);
      }      
    }
    
    if (list.contains("-printenv")) {
      System.out.println("env");
      for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
        System.out.println(entry.getKey()+"="+entry.getValue());
      }
    }
    
    for (int i = 1; i <= loopCount; i++) {
      System.out.println("Loop " + i + " of " + loopCount);
      Thread.sleep(1000);
    }
    
    if (sleep > 0L) {
      System.out.println("Sleeping for " + sleep);
      Thread.sleep(sleep);
    }
    
    if (list.contains("-x") || list.contains("-exit")) {
      System.exit(1);
    }
    
    if (list.contains("-e") || list.contains("-exception") || list.contains("-error")) {
      throw new RuntimeException("Exception inside JavaApp");
    }
    
    System.out.println("Exit JavaApp : " + JavaApp.class.getName());
    
  }

}
