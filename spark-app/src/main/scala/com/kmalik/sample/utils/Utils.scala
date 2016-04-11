package com.kmalik.sample.utils

import com.typesafe.config.Config
import scala.collection.JavaConverters._

/**
 *
 */
object Utils {

  def readConfig(config:Config, key:String):String = {
    val matching = config.entrySet().asScala.find(_.getKey.equals(key))
    if (matching.isDefined) {
      matching.get.getValue.unwrapped().toString()
    } else {
      throw new RuntimeException(key+" is mandatory");
    }
  }

  def readConfig(config:Config, key:String, defValue:String):String = {
    val matching = config.entrySet().asScala.find(_.getKey.equals(key))
    if (matching.isDefined) matching.get.getValue.unwrapped().toString else defValue
  }

  def readConfig(config:Config, key:String, defValue:Int):Int = {
    val matching = config.entrySet().asScala.find(_.getKey.equals(key))
    if (matching.isDefined) matching.get.getValue.unwrapped().toString.toInt else defValue
  }

  def readConfig(config:Config, key:String, defValue:Double):Double = {
    val matching = config.entrySet().asScala.find(_.getKey.equals(key))
    if (matching.isDefined) matching.get.getValue.unwrapped().toString.toDouble else defValue
  }

  def readConfig(config:Config, key:String, defValue:Long):Long = {
    val matching = config.entrySet().asScala.find(_.getKey.equals(key))
    if (matching.isDefined) matching.get.getValue.unwrapped().toString.toLong else defValue
  }

  def readConfig(config:Config, key:String, defValue:Boolean):Boolean = {
    val matching = config.entrySet().asScala.find(_.getKey.equals(key))
    if (matching.isDefined) matching.get.getValue.unwrapped().toString.toBoolean else defValue
  }
  
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
  
  def readArg(args:Array[String],argName:String,defValue:Boolean):Boolean = {
    val argPrefix = "--" + argName + "="
    val matching = args.find(_.startsWith(argPrefix))
    if (matching.isDefined) matching.get.substring(argPrefix.length).toBoolean else defValue
  }

}