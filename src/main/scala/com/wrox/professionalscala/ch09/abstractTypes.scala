package com.wrox.professionalscala.ch09

trait Food

class Grass extends Food {
  override def toString = "Grass"
}

class Fish extends Food {
  override def toString = "Fish"
}

trait Animal {
  type SuitableFood <: Food

  def eat(food: SuitableFood): Unit = println(s"Eating $food...")
}

class Cow extends Animal {
  type SuitableFood = Grass
}

class Cat extends Animal {
  type SuitableFood = Fish
}

object AbstractTypesExample extends App {
  val grass = new Grass
  val cow = new Cow

  val fish = new Fish
  val cat = new Cat


  cow.eat(grass)
//  cow.eat(fish) // won't compile

  cat.eat(fish)
//  cat.eat(grass) // won't compile
}
