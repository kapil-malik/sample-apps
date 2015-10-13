package com.kmalik.sample

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import com.kmalik.sample.utils.Utils

object SparkApp extends Serializable {

  def main(args:Array[String]):Unit = {
    args.foreach(println)
    
    val sc = new SparkContext(new SparkConf())        
    
    val app = Utils.readArg(args, "app", "pi")
    
    app match {
      case "pi" => Pi.run(sc, args)
      case "sample" => Sampling.run(sc, args)
      case "feature-selection" => FeatureSelection.run(sc, args)
      case "train" => LogisticRegressionTrain.run(sc, args)
      case "test" => LogisticRegressionTest.run(sc, args)
      case "lr" => LogisticRegression.run(sc, args)
      case _ => Pi.run(sc, args)
    }
    
    sc.stop    
  }
  
}