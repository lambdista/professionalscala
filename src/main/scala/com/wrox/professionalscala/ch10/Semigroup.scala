package com.wrox.professionalscala.ch10

trait Semigroup[A] {
  def append(a1: A, a2: A): A
}

object Semigroup {
  def apply[A: Semigroup]: Semigroup[A] = implicitly[Semigroup[A]]

  implicit val intSemigroup = new Semigroup[Int] {
    override def append(a1: Int, a2: Int): Int = a1 + a2
  }

  implicit val stringSemigroup = new Semigroup[String] {
    override def append(a1: String, a2: String): String = a1 + a2
  }

  implicit def optionSemigroup[A: Semigroup]: Semigroup[Option[A]] = new Semigroup[Option[A]] {
    def append(o1: Option[A], o2: Option[A]): Option[A] = (o1, o2) match {
      case (Some(a1), Some(a2)) => Some(Semigroup[A].append(a1, a2))
      case (Some(a1), None) => o1
      case (None, Some(a2)) => o2
      case (None, None) => None
    }
  }
}