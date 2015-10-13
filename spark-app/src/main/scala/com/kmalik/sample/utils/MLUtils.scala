package com.kmalik.sample.utils

import org.apache.spark.SparkContext
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD
import org.apache.spark.mllib.classification.LogisticRegressionModel
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.storage.StorageLevel

/**
 *
 */
object MLUtils {
  
  def readLabeledPoints(sc:SparkContext, 
    file:String, 
    sep:String, 
    featureIndices:Array[Int], 
    labelIndex:Int):RDD[LabeledPoint] = {
    sc.textFile(file, 16)
      .map(line => toLabeledPoint(line, sep, featureIndices, labelIndex))
  }
  
  private def toLabeledPoint(line:String, sep:String, featureIndices:Array[Int], labelIndex:Int) = {
    val splits = line.split(sep, -1)
    val features = splits.zipWithIndex
               .filter(p => featureIndices.contains(p._2))
               .map(_._1.toDouble)
    val label = splits(labelIndex).toDouble
    new LabeledPoint(label, Vectors.dense(features))
  }
  
  def trainModel(trainData:RDD[LabeledPoint], numIterations:Int):LogisticRegressionModel = {
    trainData.persist(StorageLevel.MEMORY_ONLY_SER)
    LogisticRegressionWithSGD.train(trainData, numIterations)
  }
  
  def validateModel(model:LogisticRegressionModel, testData:RDD[LabeledPoint]) = {
    val total = testData.count    
    model.setThreshold(0.5)
    val correct = model.predict(testData.map(_.features))
                 .zip(testData.map(_.label))
                 .filter(isValidPrediction)
                 .count
    (total, correct)
  }
  
  private def isValidPrediction(pair:(Double,Double)) = {
    val (predicted, actual) = pair
    (predicted <= 0.5 && actual <= 0.5) || (predicted > 0.5 && actual > 0.5) 
  } 

}