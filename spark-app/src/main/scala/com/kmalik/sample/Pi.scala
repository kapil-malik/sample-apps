package com.kmalik.sample

import org.apache.spark.SparkContext
import scala.math.random
import com.kmalik.sample.utils.Utils
import com.typesafe.config.Config

/**
 *
 */
object Pi {

  def run(sc:SparkContext, args:Array[String]): Unit = {
    
    val points = Utils.readArg(args, "points", 400000L)
    val slices = Utils.readArg(args, "slices", 4)    
    extracted(points, slices, sc)    
  }

  def run(sc:SparkContext, config:Config): String = {
    
    val points = Utils.readConfig(config, "points", 400000L)
    val slices = Utils.readConfig(config, "slices", 4)    
    extracted(points, slices, sc).toString 
  }

  private def extracted(points: Long, slices: Int, sc: org.apache.spark.SparkContext) = {
    val n = math.min(points, Int.MaxValue).toInt // avoid overflow
    
    println("Computing Pi for " + n + " points in " + slices + " slices")
    
    val count = sc.parallelize(1 until n, slices)
                  .map { i =>
                    val x = random * 2 - 1
                    val y = random * 2 - 1
                    if (x*x + y*y < 1) 1 else 0
                  }
                  .reduce(_ + _)
    
    val pi = 4.0 * count / n
    println("Pi is roughly " + pi)
    pi                  
  }
}