package com.kmalik.sample

import org.apache.spark.SparkContext
import com.kmalik.sample.utils.Utils
import com.kmalik.sample.utils.MLUtils
import breeze.macros.expand.args
import com.typesafe.config.Config
import breeze.macros.expand.args

/**
 *
 */
object FeatureSelection extends Serializable {
  
  def run(sc:SparkContext, args:Array[String]): Unit = {
    val inPath = Utils.readArg(args, "input")
    val sep = Utils.readArg(args, "sep", ",")
    val featureIndices = Utils.readArg(args, "features").split(",", -1).map(_.toInt)
    val outPath = Utils.readArg(args, "output", inPath + ".out." + System.currentTimeMillis)
    
    extracted(sc, inPath, sep, featureIndices, outPath)
  }
  
  def run(sc:SparkContext, config:Config): String = {
    val inPath = Utils.readConfig(config, "input")
    val sep = Utils.readConfig(config, "sep", ",")
    val featureIndices = Utils.readConfig(config, "features").split(",", -1).map(_.toInt)
    val outPath = Utils.readConfig(config, "output", inPath + ".out." + System.currentTimeMillis)
    
    extracted(sc, inPath, sep, featureIndices, outPath)
  }

  private def extracted(sc: org.apache.spark.SparkContext, inPath: String, sep: String, featureIndices: Array[Int], outPath: String) = {
    sc.textFile(inPath, 16)
      .map(line => select(line, sep, featureIndices))
      .saveAsTextFile(outPath)
    
    println("Feature selection output saved at " + outPath)
    outPath
  }
  
  private def select(line: String, sep: String, featureIndices : Array[Int]):String = {
    line.split(sep, -1)
        .zipWithIndex
        .filter(p => featureIndices.contains(p._2))
        .map(_._1)
        .mkString(sep)
  }
}