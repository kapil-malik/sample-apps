package com.kmalik.sample

import spark.jobserver.SparkJob
import org.apache.spark.SparkContext
import com.typesafe.config.Config
import scala.collection.JavaConverters._
import com.kmalik.sample.utils.Utils
import spark.jobserver.SparkJobValidation
import spark.jobserver.SparkJobValid

class SampleSparkJob extends SparkJob {
  
  override def runJob(sc:SparkContext, jobConfig: Config): Any = {
    
    jobConfig.entrySet().asScala.foreach(x => println(x.getKey+"="+x.getValue.toString()))
    
    val app = Utils.readConfig(jobConfig, "app", "pi")
    
    val result = app match {
      case "pi" => Pi.run(sc, jobConfig)
      case "sample" => Sampling.run(sc, jobConfig)
      case "feature-selection" => FeatureSelection.run(sc, jobConfig)
      case "train" => LogisticRegressionTrain.run(sc, jobConfig)
      case "test" => LogisticRegressionTest.run(sc, jobConfig)
      case "lr" => LogisticRegression.run(sc, jobConfig)
      case _ => Pi.run(sc, jobConfig)
    }
    
    result
  }

  override def validate(sc:SparkContext, config: Config): SparkJobValidation = SparkJobValid
}