package com.kmalik.sample.utils

/**
 *
 */
object Utils {
  
  def readArg(args:Array[String],argName:String):String = {
    val argPrefix = "--" + argName + "="
    val matching = args.find(_.startsWith(argPrefix))
    if (matching.isDefined) {
      matching.get.substring(argPrefix.length) 
    } else {
      throw new RuntimeException(argName+" is mandatory");
    }
  }
  
  def readArg(args:Array[String],argName:String,defValue:String):String = {
    val argPrefix = "--" + argName + "="
    val matching = args.find(_.startsWith(argPrefix))
    if (matching.isDefined) matching.get.substring(argPrefix.length) else defValue
  }
  
  def readArg(args:Array[String],argName:String,defValue:Int):Int = {
    val argPrefix = "--" + argName + "="
    val matching = args.find(_.startsWith(argPrefix))
    if (matching.isDefined) matching.get.substring(argPrefix.length).toInt else defValue
  }
  
  def readArg(args:Array[String],argName:String,defValue:Double):Double = {
    val argPrefix = "--" + argName + "="
    val matching = args.find(_.startsWith(argPrefix))
    if (matching.isDefined) matching.get.substring(argPrefix.length).toDouble else defValue
  }
  
  def readArg(args:Array[String],argName:String,defValue:Long):Long = {
    val argPrefix = "--" + argName + "="
    val matching = args.find(_.startsWith(argPrefix))
    if (matching.isDefined) matching.get.substring(argPrefix.length).toLong else defValue
  }

}