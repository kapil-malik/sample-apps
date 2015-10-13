package com.kmalik.sample

import org.apache.spark.SparkContext
import com.kmalik.sample.utils.Utils
import com.kmalik.sample.utils.MLUtils

/**
 *
 */
object FeatureSelection extends Serializable {
  
  def run(sc:SparkContext, args:Array[String]):Unit = {
    val inPath = Utils.readArg(args, "input")
    val sep = Utils.readArg(args, "sep", ",")
    val featureIndices = Utils.readArg(args, "features").split(",", -1).map(_.toInt)
    val outPath = Utils.readArg(args, "output", inPath + ".out." + System.currentTimeMillis)
    
    sc.textFile(inPath, 16)
      .map(line => select(line, sep, featureIndices))
      .saveAsTextFile(outPath)
    
    println("Feature selection output saved at " + outPath)
  }
  
  private def select(line: String, sep: String, featureIndices : Array[Int]):String = {
    line.split(sep, -1)
        .zipWithIndex
        .filter(p => featureIndices.contains(p._2))
        .map(_._1)
        .mkString(sep)
  }

}