package com.kmalik.sample

import org.apache.spark.SparkContext
import org.apache.spark.mllib.classification.LogisticRegressionModel
import org.apache.spark.rdd.RDD

import com.kmalik.sample.utils.MLUtils
import com.kmalik.sample.utils.Utils
import com.typesafe.config.Config

/**
 *
 */
object LogisticRegression extends Serializable  {
  
  def run(sc:SparkContext, args:Array[String]): Unit = {
    val train = Utils.readArg(args, "train")
    val test = Utils.readArg(args, "test", train)
    val sep = Utils.readArg(args, "sep", ",")
    val featureIndices = Utils.readArg(args, "features").split(",", -1).map(_.toInt)
    val labelIndex = Utils.readArg(args, "label", 0)
    val numIterations = Utils.readArg(args, "iterations", 10)
    val outPath = Utils.readArg(args, "output", test + ".out." + System.currentTimeMillis)
    extracted(sc, train, sep, featureIndices, labelIndex, test, numIterations, outPath)
  }
  
  def run(sc:SparkContext, config:Config): String = {
    val train = Utils.readConfig(config, "train")
    val test = Utils.readConfig(config, "test", train)
    val sep = Utils.readConfig(config, "sep", ",")
    val featureIndices = Utils.readConfig(config, "features").split(",", -1).map(_.toInt)
    val labelIndex = Utils.readConfig(config, "label", 0)
    val numIterations = Utils.readConfig(config, "iterations", 10)
    val outPath = Utils.readConfig(config, "output", test + ".out." + System.currentTimeMillis)
    extracted(sc, train, sep, featureIndices, labelIndex, test, numIterations, outPath)
  }

  private def extracted(sc: org.apache.spark.SparkContext, train: String, sep: String, featureIndices: Array[Int], labelIndex: Int, test: String, numIterations: Int, outPath: String) = {
    val trainData = MLUtils.readLabeledPoints(sc, train, sep, featureIndices, labelIndex)
    val testData = if (test.equals(train)) trainData else MLUtils.readLabeledPoints(sc, test, sep, featureIndices, labelIndex)
    
    val model = MLUtils.trainModel(trainData, numIterations)

    val (total, correct) = MLUtils.validateModel(model, testData)
    
    val lines = sc.parallelize(Array[String](
        "TOTAL TEST LINES : " + total, 
        "CORRECTLY PREDICTED : " + correct), 
        1)
        
    lines.saveAsTextFile(outPath)
    
    lines.foreach(println)
    lines.collect.mkString(",")
  }
}