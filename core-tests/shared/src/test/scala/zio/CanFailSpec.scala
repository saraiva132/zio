package zio

import zio.test._
import zio.test.Assertion._

object CanFailSpec extends ZIOBaseSpec {

  def spec = suite("CanFailSpec")(
    testM("useful combinators compile") {
      val result = typeCheck {
        """
            import zio._
            val io =  IO(1 / 0)
            val uio = UIO(0)
            io.orElse(uio)
            """
      }
      assertM(result, isRight(anything))
    },
    testM("useless combinators don't compile") {
      val result = typeCheck {
        """
            import zio._
            val io =  IO(1 / 0)
            val uio = UIO(0)
            uio.orElse(io)
            """
      }
      assertM(result, isLeft(anything))
    }
  )
}
