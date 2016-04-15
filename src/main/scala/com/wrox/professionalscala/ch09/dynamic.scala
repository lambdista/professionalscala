package com.wrox.professionalscala.ch09

class Magic extends Dynamic {
  def selectDynamic(field: String): Magic = {
    println(s"You called $field")

    this
  }

  def applyDynamic(name: String)(args: Any*): Unit =
    println(s"method '$name' called with arguments: ${args.mkString(", ")}")
}

object DynamicExample extends App {
  val magic = new Magic

  magic.foo
  magic.bar

  magic.foo.bar

  magic.someMethod("foo", 42, List(1, 2, 3))
}
