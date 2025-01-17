package tests.feature

import scala.meta.internal.metals.{BuildInfo => V}

import tests.BaseAnalyzeStacktraceSuite

class CrossAnalyzeStacktraceSuite
    extends BaseAnalyzeStacktraceSuite("analyzestacktrace") {

  check(
    "simple",
    """|package a
       |def fetch() =
       |  println("Fetching!")
       |<<1>>  throw new Exception("")
       |
       |@main
       |def main() = 
       |<<2>>  fetch()
       |  
       |def fetch(a: Int) = a + 2
       |""".stripMargin,
    """|Exception in thread "main" java.lang.Exception:
       |at a.other$package$.fetch(other.scala:4)
       |at a.other$package$.main(other.scala:8)
       |at a.main.main(other.scala:6)
       |""".stripMargin,
    filename = "other.scala",
    scalaVersion = V.scala3,
  )

}
