package com.wrox.professionalscala.ch10

trait Applicative[F[_]] extends Functor[F] {
  def pure[A](a: A): F[A]

  def ap[A, B](fa: F[A])(fab: F[A => B]): F[B]

  // an applicative is "naturally" a functor, indeed map can be expressed using ap and pure
  override def map[A, B](fa: F[A])(fab: A => B): F[B] = ap(fa)(pure(fab))
}

object Applicative {
  def apply[F[_]: Applicative]: Applicative[F] = implicitly[Applicative[F]]

  def pure[A, F[_]: Applicative](a: A): F[A] = Applicative[F].pure(a)

  def ap[A, B, F[_]: Applicative](fa: F[A])(fab: F[A => B]): F[B] = Applicative[F].ap(fa)(fab)

  implicit val optionApplicative = new Applicative[Option] {
    override def pure[A](a: A): Option[A] = Option(a)

    override def ap[A, B](fa: Option[A])(fab: Option[A => B]): Option[B] = for {
      a <- fa
      f <- fab
    } yield f(a)
  }

  implicit val listApplicative = new Applicative[List] {
    override def pure[A](a: A): List[A] = List(a)

    override def ap[A, B](fa: List[A])(fab: List[A => B]): List[B] = for {
      a <- fa
      f <- fab
    } yield f(a)
  }
}

object ApplicativeExample extends App {
  import Applicative._

  def interpret(str: String): Option[Int => Int] = str.toLowerCase match {
    case "incr" => Some(_ + 1)
    case "decr" => Some(_ - 1)
    case "square" => Some(x => x * x)
    case "halve" => Some(x => x / 2)
    case _ => None
  }

  val func: Option[Int => Int] = interpret("incr")

  val v: Option[Int] = Some(42)

  val result: Option[Int] = ap(v)(func)

  println(s"result: $result")
}