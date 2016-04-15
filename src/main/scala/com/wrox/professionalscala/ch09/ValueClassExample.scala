package com.wrox.professionalscala.ch09

object ValueClassExample extends App {
  class Meter(val value: Double) extends AnyVal {
    def add(m: Meter): Meter = new Meter(value + m.value)
  }

  implicit class IntOps(private val x: Int) extends AnyVal {
    def stars: String = "*" * x
  }

  val review = 5 stars // *****

  println(s"review: $review")
}