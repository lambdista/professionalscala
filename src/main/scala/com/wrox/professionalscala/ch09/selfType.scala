package com.wrox.professionalscala.ch09

object ThisAlias {
  trait Foo {
    self =>
    def message: String

    private trait Bar {
      def message: String = self.message + " and Bar"
    }

    val fullMessage = {
      object bar extends Bar

      bar.message
    }
  }
  object FooImpl extends Foo {
    override def message: String = "Hello from Foo"
  }
}


object ThisAliasExample extends App {
  import ThisAlias._

  println(s"Message: ${FooImpl.fullMessage}")
}
