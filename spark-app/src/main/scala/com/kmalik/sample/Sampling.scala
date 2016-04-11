package com.kmalik.sample

import org.apache.spark.SparkContext
import com.kmalik.sample.utils.Utils
import com.kmalik.sample.utils.MLUtils
import com.typesafe.config.Config

/**
 *
 */
object Sampling extends Serializable {
  
  def run(sc:SparkContext, args:Array[String]): Unit = {
    val inPath = Utils.readArg(args, "input")
    val ratio = Utils.readArg(args, "ratio", 0.1)
    val outPath = Utils.readArg(args, "output", inPath + ".out." + System.currentTimeMillis)
    
    extracted(sc, inPath, ratio, outPath)
  }
  
  def run(sc:SparkContext, config:Config): String = {
    val inPath = Utils.readConfig(config, "input")
    val ratio = Utils.readConfig(config, "ratio", 0.1)
    val outPath = Utils.readConfig(config, "output", inPath + ".out." + System.currentTimeMillis)
    
    extracted(sc, inPath, ratio, outPath)
  }

  private def extracted(sc: org.apache.spark.SparkContext, inPath: String, ratio: Double, outPath: String) = {
    sc.textFile(inPath)
      .sample(false, ratio)
      .saveAsTextFile(outPath)
    
    println("Sampling output saved at " + outPath)
    outPath
  }
}