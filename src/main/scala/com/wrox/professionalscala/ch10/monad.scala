package com.wrox.professionalscala.ch10

trait Monad[M[_]] extends Applicative[M] {
  def unit[A](a: A): M[A]

  def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]

  // a monad is "naturally" an applicative functor, indeed pure and ap can be expressed using unit, map and flatMap
  override def pure[A](a: A): M[A] = unit(a)

  override def ap[A, B](fa: M[A])(f: M[A => B]): M[B] = flatMap(f)(map(fa))
}

object Monad {
  def apply[M[_] : Monad]: Monad[M] = implicitly[Monad[M]]

  def flatMap[M[_] : Monad, A, B](ma: M[A])(f: A => M[B]): M[B] = Monad[M].flatMap(ma)(f)

  implicit val optionMonad = new Monad[Option] {
    override def unit[A](a: A): Option[A] = Option(a)

    override def flatMap[A, B](ma: Option[A])(f: A => Option[B]): Option[B] = ma.flatMap(f)
  }

  implicit val listMonad = new Monad[List] {
    override def unit[A](a: A): List[A] = List(a)

    override def flatMap[A, B](ma: List[A])(f: A => List[B]): List[B] = ma.flatMap(f)
  }
}

object MonadExample extends App {
  import Monad._

  val sqrt: Double => Option[Double] = { value =>
    val result = math.sqrt(value)

    if (result.toInt.toDouble == result) Some(result) else None
  }


  val aSquare: Option[Double] = Some(49)
  val notASquare: Option[Double] = Some(42)

  val result1: Option[Double] = flatMap(aSquare)(sqrt)

  val result2: Option[Double] = flatMap(notASquare)(sqrt)

  println(s"result1: $result1")
  println(s"result2: $result2")
}
