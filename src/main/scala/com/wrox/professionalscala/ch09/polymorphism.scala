package com.wrox.professionalscala.ch09

object SubtypeExample extends App {
  trait Shape {
    def area: Double
  }

  class Rectangle(val width: Double, val height: Double) extends Shape {
    override def area: Double = width * height
  }

  class Square(val width: Double) extends Shape {
    override def area: Double = width * width
  }

  class Circle(val radius: Double) extends Shape {
    override def area: Double = math.Pi * radius * radius
  }

  val shape: Shape = new Rectangle(10, 5)

  println(s"Area: ${shape.area}")
}

object ParametricExample extends App {
  def map[A, B](xs: List[A])(f: A => B): List[B] = xs map f

  val stringList: List[String] = map(List(1, 2, 3))(_.toString)

  val intList: List[Int] = map(List("1", "2", "3"))(_.toInt)

  println(s"From List[Int] to List[String]: $stringList")

  println(s"From List[String] to List[Int]: $intList")
}

object TypeClassExample extends App {
  trait Equal[A] {
    def eq(a1: A, a2: A): Boolean
  }

  object Equal {
    def apply[A: Equal]: Equal[A] = implicitly[Equal[A]]
//    def areEqual[A](a1: A, a2: A)(implicit equal: Equal[A]): Boolean = equal.eq(a1, a2)
//    def areEqual[A: Equal](a1: A, a2: A): Boolean = implicitly[Equal[A]].eq(a1, a2)
    def areEqual[A: Equal](a1: A, a2: A): Boolean = Equal[A].eq(a1, a2)
  }

  case class Person(firstName: String, lastName: String)

  object Person {

    implicit object PersonEqual extends Equal[Person] {
      override def eq(a1: Person, a2: Person): Boolean = a1.firstName.equalsIgnoreCase(a2.firstName) &&
        a1.lastName.equalsIgnoreCase(a2.lastName)
    }

  }

  val p1 = Person("John", "Doe")
  val p2 = Person("john", "doe")

  val comparisonResult = Equal.areEqual(p1, p2)
  println(s"Comparison Result: $comparisonResult")

  trait AreaComputer[T] {
    def area(t: T): Double
  }

  case class Rectangle(width: Double, height: Double)

  case class Square(width: Double)

  case class Circle(radius: Double)

  object AreaComputer {
    def areaOf[T](t: T)(implicit computer: AreaComputer[T]): Double = computer.area(t)

    implicit val rectAreaComputer = new AreaComputer[Rectangle] {
      override def area(rectangle: Rectangle): Double = rectangle.width * rectangle.height
    }

    implicit val squareAreaComputer = new AreaComputer[Square] {
      override def area(square: Square): Double = square.width * square.width
    }

    implicit val circleAreaComputer = new AreaComputer[Circle] {
      override def area(circle: Circle): Double = math.Pi * circle.radius * circle.radius
    }
  }

  import AreaComputer._

  val square = Square(10)
  val area = areaOf(square)

  println(s"Area of square: $area")
}
