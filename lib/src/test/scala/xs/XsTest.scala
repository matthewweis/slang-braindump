package xs

import org.sireum._
import lib.Xs._
import lib.Xs.fp

class XsTest extends test.TestSuite {

  val tests = Tests {

    * - assert(T)
    "Summary Sum" - {
      * - assert(z"00" == summary_sum(ISZ()))
      * - assert(z"01" == summary_sum(ISZ(1)))
      * - assert(z"03" == summary_sum(ISZ(1, 2)))
      * - assert(z"06" == summary_sum(ISZ(1, 2, 3)))
      * - assert(z"10" == summary_sum(ISZ(1, 2, 3, 4)))
      * - assert(z"15" == summary_sum(ISZ(1, 2, 3, 4, 5)))
      * - assert(z"21" == summary_sum(ISZ(1, 2, 3, 4, 5, 6)))
      * - assert(z"28" == summary_sum(ISZ(1, 2, 3, 4, 5, 6, 7)))
    }

    "Geometry Sum" - {
      * - assert(z"00" == geometry_sum(ISZ()))
      * - assert(z"01" == geometry_sum(ISZ(1)))
      * - assert(z"03" == geometry_sum(ISZ(1, 2)))
      * - assert(z"06" == geometry_sum(ISZ(1, 2, 3)))
      * - assert(z"10" == geometry_sum(ISZ(1, 2, 3, 4)))
      * - assert(z"15" == geometry_sum(ISZ(1, 2, 3, 4, 5)))
      * - assert(z"21" == geometry_sum(ISZ(1, 2, 3, 4, 5, 6)))
      * - assert(z"28" == geometry_sum(ISZ(1, 2, 3, 4, 5, 6, 7)))
    }

    "Summary Sum vs. Geometry Sum" - {
      * - {
        def assertEquals(ns: ISZ[Z]): Unit = assert(summary_sum(ns) == geometry_sum(ns))
        def rangeTo(n: Z): ISZ[Z] = z"1" to n
        def assertEqualsForRange(n: Z): Unit = assertEquals(rangeTo(n))

        val stop = z"200"
        assert(z"1" < stop)
        for (n <- z"1" to stop) {
          assertEqualsForRange(n)
        }
      }
    }


      // "fp"
      * - {
        val start = z"0"
        val stop = z"10"
        assert(start < stop)
        for (n <- start until stop; m <- n.increase to stop) {
          def times(n: Z, m: Z): Z = n * m
          val f = fp.curry(times)
          assert(fp.identity(f)(n)(m) == f(n)(m))
          val g = fp.uncurry(f)
          assert(f(n)(m) == g(n, m))
          val f_n = f(n)
          assert(f_n(m) == g(n, m))
          val f_n_m = f_n(m)
          assert(f_n_m == g(n, m))
        }
      }

      // "fp.swap"
      * - {
        def minus(n: Z, m: Z): Z = n - m
        val f: Z => Z => Z = fp.curry(minus)
        val g = fp.swap(f)
        //
        val start = z"-5"
        val stop = z"5"
        assert(start < stop)
        for (n <- start until stop; m <- start to stop) {
          assert(f(n)(m) == g(m)(n))
        }
      }

      // "fp.flip"
      * - {
        def minus(n: Z, m: Z): Z = n - m
        val f: (Z,Z) => Z = minus
        val g = fp.flip(f)
        //
        val start = z"-5"
        val stop = z"5"
        assert(start < stop)
        for (n <- start until stop; m <- start to stop) {
          assert(f(n,m) == g(m,n))
        }
      }

    "Pipe" - {
      // "map reduce"
      * - {
        val actual = lib.Xs.pipe
          .wrap.array(z"0" to z"20" by z"2")
          .ops.map(fp.swap(cmath.divide)(z"2"))
          .sink.reduce(z"0", math.sum)
        val expected = z"1" + z"2" + z"3" + z"4" + z"5" + z"6" + z"7" + z"8" + z"9" + z"10"
        assert(actual == expected)
      }

    }
  }

  @pure def geometry_sum(ns: ISZ[Z]): Z = {
    // precondition: ns is of the form { 0, 1, 2, ..., n.length - 1 }
    val l = ns.size
    return (l * l.increase) / z"2"
  }

  @pure def summary_sum(ns: ISZ[Z]): Z = {
    // precondition: ns is of the form { 0, 1, 2, ..., n.length - 1 }
    var sum = z"0"
    for (n <- ns) {
      sum = sum + n
    }
    return sum
  }

}