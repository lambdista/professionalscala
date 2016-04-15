package com.wrox.professionalscala.ch09

object StructuralTypes {

  class Foo {
    def close(): Unit = println("Foo closed")
  }

  class Bar {
    def close(): Unit = println("Bar closed")
  }

  class Baz

  def closeResource(resource: { def close(): Unit }): Unit = {
    println("Closing resource...")

    resource.close()
  }

  type Resource = {
    def open(): Unit
    def close(): Unit
  }

  def useResource(resource: Resource): Unit = {
    resource.open()

    // do what you need to do

    resource.close()
  }

}

object StructuralTypesExample extends App {
  import StructuralTypes._

  val foo = new Foo
  closeResource(foo)

  val bar = new Bar
  closeResource(bar)

  val baz = new Baz
  //closeResource(baz) // won't compile
}
