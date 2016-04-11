package com.kmalik.sample

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD
import org.apache.spark.mllib.classification.LogisticRegressionModel
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.storage.StorageLevel
import com.kmalik.sample.utils.Utils
import com.kmalik.sample.utils.MLUtils
import breeze.macros.expand.args
import com.typesafe.config.Config

/**
 *
 */
object LogisticRegressionTest extends Serializable {
  
  def run(sc:SparkContext, args:Array[String]): Unit = {
    val test = Utils.readArg(args, "test")
    val modelPath = Utils.readArg(args, "modelPath")
    val sep = Utils.readArg(args, "sep", ",")
    val featureIndices = Utils.readArg(args, "features").split(",", -1).map(_.toInt)
    val labelIndex = Utils.readArg(args, "label", 0)
    val outPath = Utils.readArg(args, "output", test + ".out." + System.currentTimeMillis)
    extracted(sc, test, sep, featureIndices, labelIndex, modelPath, outPath)
  }
  
  def run(sc:SparkContext, config:Config): String = {
    val test = Utils.readConfig(config, "test")
    val modelPath = Utils.readConfig(config, "modelPath")
    val sep = Utils.readConfig(config, "sep", ",")
    val featureIndices = Utils.readConfig(config, "features").split(",", -1).map(_.toInt)
    val labelIndex = Utils.readConfig(config, "label", 0)
    val outPath = Utils.readConfig(config, "output", test + ".out." + System.currentTimeMillis)
    extracted(sc, test, sep, featureIndices, labelIndex, modelPath, outPath)
  }

  private def extracted(sc: org.apache.spark.SparkContext, test: String, sep: String, featureIndices: Array[Int], labelIndex: Int, modelPath: String, outPath: String) = {
    val testData = MLUtils.readLabeledPoints(sc, test, sep, featureIndices, labelIndex)
    val model = LogisticRegressionModel.load(sc, modelPath)
    
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