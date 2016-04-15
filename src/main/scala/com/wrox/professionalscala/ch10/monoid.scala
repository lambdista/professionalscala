package com.wrox.professionalscala.ch10

trait Monoid[A] extends Semigroup[A] {
  def zero: A
}

object Monoid {
  def apply[A: Monoid]: Monoid[A] = implicitly[Monoid[A]]

  implicit val intMonoid = new Monoid[Int] {
    override def zero: Int = 0

    override def append(a1: Int, a2: Int): Int = a1 + a2
  }

  implicit val stringMonoid = new Monoid[String] {
    override def zero: String = ""

    override def append(a1: String, a2: String): String = Semigroup[String].append(a1, a2)
  }

  implicit def optionMonoid[A: Semigroup]: Monoid[Option[A]] = new Monoid[Option[A]] {
    def zero: Option[A] = None

    def append(f1: Option[A], f2: Option[A]): Option[A] = (f1, f2) match {
      case (Some(a1), Some(a2)) => Some(Semigroup[A].append(a1, a2))
      case (Some(a1), None) => f1
      case (None, Some(a2)) => f2
      case (None, None) => None
    }
  }
}

object MonoidExample extends App {
  def sum[A](elements: List[A])(implicit M: Monoid[A]): A = elements.foldLeft(M.zero)(M.append)

  val intResult: Int = sum(List(1, 2, 3))

  val stringResult: String = sum(List("hello", ", ", "world"))

  val optionResult: Option[String] = sum(List(Some("hello"), Some(", "), None, Some("world")))

  println(s"intResult: $intResult")
  println(s"stringResult: $stringResult")
  println(s"optionResult: $optionResult")
}