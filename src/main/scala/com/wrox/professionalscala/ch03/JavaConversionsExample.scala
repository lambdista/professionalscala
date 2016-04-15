package com.wrox.professionalscala.ch03

import java.util.{ArrayList => JArrayList, List => JList}

import scala.collection.JavaConversions._

object JavaConversionsExample extends App {
  val javaList: JList[Int] = new JArrayList()
  javaList.add(42)
  javaList.add(1)

  val asScala = javaList.map(_ + 1)

  println(s"Java List to Scala: $asScala")
}