package com.wrox.professionalscala.ch10

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

object Functor {
  def apply[F[_]: Functor]: Functor[F] = implicitly[Functor[F]]

  def map[A, B, F[_] : Functor](fa: F[A])(f: A => B): F[B] = Functor[F].map(fa)(f)

  implicit val optionFunctor = new Functor[Option] {
    override def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)
  }

  implicit val listFunctor = new Functor[List] {
    override def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)
  }
}

object FunctorExample extends App {
  import Functor._

  val o1: Option[Int] = Some(42)
  val o2: Option[Int] = None

  val r1 = map(o1)(identity)

  val r2 = map(o2)(identity)

  assert(o1 == r1)
  assert(o2 == r2)

  val f: Int => Int = _ * 2
  val g: Int => Int = _ - 1

  val r3 = map(o1)(f compose g)
  val r4 = map(map(o1)(g))(f)

  val result1 = r3 == r4

  assert(result1)

  println(s"result1: $result1")

  val result2 = Some(42).map(f compose g) == Some(42).map(g).map(f)

  assert(result2)

  println(s"result1: $result1")
}
