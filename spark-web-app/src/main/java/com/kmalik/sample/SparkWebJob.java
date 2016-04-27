package com.kmalik.sample;

import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.typesafe.config.Config;

import spark.jobserver.JavaSparkJob;

public class SparkWebJob extends JavaSparkJob {

	@Override
	public Object runJob(JavaSparkContext sc, Config config) {
		final String[] args = config.hasPath("args") 
			? config.getString("args").split(" ") : new String[]{};

		final ApplicationContext appContext = SpringApplication.run(SparkWebApp.class, args);
	    appContext.getBean(SparkRestController.class)
	    		  .setContext(sc, Boolean.FALSE);
		return "Started";
	}
	
}
