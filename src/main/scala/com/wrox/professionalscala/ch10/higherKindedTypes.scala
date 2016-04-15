package com.wrox.professionalscala.ch10

trait Mapper[M[_]] {
  def map[A, B](ma: M[A])(f: A => B): M[B]
}

object Mapper {
  def map[A, B, M[_] : Mapper](ma: M[A])(f: A => B): M[B] = implicitly[Mapper[M]].map(ma)(f)

  implicit val optionMapper = new Mapper[Option] {
    override def map[A, B](ma: Option[A])(f: A => B): Option[B] = ma.map(f)
  }

  implicit val listMapper = new Mapper[List] {
    override def map[A, B](ma: List[A])(f: A => B): List[B] = ma.map(f)
  }
}

object HigherKindedTypesExample extends App {
  import Mapper._

  val as: List[Int] = List(1, 2, 3)
  val bs: List[Int] = map(as)(_ + 1)

  println(s"bs: $bs")

  val a: Option[String] = Some("1")
  val b: Option[Int] = map(a)(_.toInt + 1)

  println(s"b: $b")
}
