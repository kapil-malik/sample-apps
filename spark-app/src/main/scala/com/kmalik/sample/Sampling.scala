package com.kmalik.sample

import org.apache.spark.SparkContext
import com.kmalik.sample.utils.Utils
import com.kmalik.sample.utils.MLUtils

/**
 *
 */
object Sampling extends Serializable {
  
  def run(sc:SparkContext, args:Array[String]):Unit = {
    val inPath = Utils.readArg(args, "input")
    val ratio = Utils.readArg(args, "ratio", 0.1)
    val outPath = Utils.readArg(args, "output", inPath + ".out." + System.currentTimeMillis)
    
    sc.textFile(inPath)
      .sample(false, ratio)
      .saveAsTextFile(outPath)
    
    println("Sampling output saved at " + outPath)
  }

}