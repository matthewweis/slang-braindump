// #Sireum

package xs

import org.sireum._

object Xs {

  @pure def pipe: Sources = {
    return CoreSources()
  }

  @pure def math: Uncurried = {
    return CoreUncurried()
  }

  @pure def cmath: Curried = {
    return CoreCurried()
  }

  @pure def fp: Fp = {
    return CoreFp()
  }

}

// common math functions in eta expanded form
@sig sealed trait Uncurried {
  @pure def increase: Z => Z @pure
  @pure def sum: (Z, Z) => Z @pure
  @pure def multiply: (Z, Z) => Z @pure
  @pure def divide: (Z, Z) => Z @pure
}

@datatype class CoreUncurried() extends Uncurried {
  @pure override def increase: Z => Z @pure = {
    return (z: Z) => z.increase
  }
  @pure override def sum: (Z, Z) => Z @pure = {
    return (x: Z, y: Z) => x + y
  }
  @pure override def multiply: (Z, Z) => Z @pure = {
    return (x: Z, y: Z) => x * y
  }
  @pure override def divide: (Z, Z) => Z @pure = {
    return (x: Z, y: Z) => x / y
  }
}

@sig sealed trait Curried {
  @pure def increase: Z => Z @pure
  @pure def sum: Z => (Z => Z @pure) @pure
  @pure def times: Z => (Z => Z @pure) @pure
  @pure def divide: Z => (Z => Z @pure) @pure
}

@datatype class CoreCurried() extends Curried {
  @pure override def increase: Z => Z @pure = {
    return (z: Z) => z.increase
  }
  @pure override def sum: Z => (Z => Z @pure) @pure = {
    return (x: Z) => (y: Z) => x + y
  }
  @pure override def times: Z => (Z => Z @pure) @pure = {
    return (x: Z) => (y: Z) => x * y
  }
  @pure override def divide: Z => (Z => Z @pure) @pure = {
    return (x: Z) => (y: Z) => x / y
  }
}

@sig sealed trait Fp {
  @pure def unit(): Unit
  @pure def identity[T,R](f: T => R @pure): T => R @pure
  @pure def lift[T,R](r: R): T => R @pure // lambda lift
  @pure def uncurry[T,U,R](f: T => (U => R @pure) @pure): (T, U) => R @pure
  @pure def curry[T,U,R](f: (T, U) => R @pure): T => (U => R @pure) @pure
  @pure def swap[T,U,R](f: T => (U => R @pure) @pure): U => (T => R @pure) @pure
  // flip = swap but for uncurried tuple in
  @pure def flip[T,U,R](f: (T, U) => R @pure): (U, T) => R @pure
}

@datatype class CoreFp() extends Fp {
  @pure override def unit(): Unit = {
    // NOP
  }

  @pure override def identity[T,R](f: T => R @pure): T => R @pure = {
    return f
  }

  // lambda lift
  @pure override def lift[T,R](r: R): T => R @pure = {
    return (_: T) => r
  }

  @pure override def uncurry[T,U,R](f: T => (U => R @pure) @pure): (T, U) => R @pure = {
    return (t: T, u: U) => f(t)(u)
  }

  @pure override def curry[T,U,R](f: (T, U) => R @pure): T => (U => R @pure) @pure = {
    return (t: T) => (u: U) => f(t, u)
  }

  @pure override def swap[T,U,R](f: T => (U => R @pure) @pure): U => (T => R @pure) @pure = {
    return (u: U) => (t: T) => f(t)(u)
  }

  @pure override def flip[T,U,R](f: (T, U) => R @pure): (U, T) => R @pure = {
    return (u: U, t: T) => f(t, u)
  }
}

@sig sealed trait Sources {
  @pure def of: CountableSources
  @pure def gen: GeneratedSources
  @pure def wrap: WrapperSources
}

@datatype class CoreSources() extends Sources {
  @pure override def of: CountableSources = {
    return CoreCountableSources()
  }
  @pure override def gen: GeneratedSources = {
    return CoreGeneratedSources()
  }
  @pure override def wrap: WrapperSources = {
    return CoreWrapperSources()
  }
}

@sig sealed trait GeneratedSources {
  @pure def closure[T](closure: T => T): Fork[T]
  @pure def seeded[T](seed: T, generator: T => T @pure): Fork[T]
}

@datatype class CoreGeneratedSources() extends GeneratedSources {
  @pure override def closure[T](closure: T => T): Fork[T] = {
    halt("todo") // todo
  }

  @pure override def seeded[T](seed: T, generator: T => T @pure): Fork[T] = {
    halt("todo") // todo
  }
}

@sig sealed trait WrapperSources {
  @pure def array[T](array: ISZ[T]): Fork[T]
}

@datatype class CoreWrapperSources() extends WrapperSources {
  @pure override def array[T](array: ISZ[T]): Fork[T] = {
    CoreFork(CorePipes((cons: T => Unit) => (for (value <- array) yield cons(value)).asInstanceOf[Unit]))
  }
}

@sig sealed trait CountableSources {
  @pure def zero[T](): Fork[T]
  @pure def one[T](value: T): Fork[T]
  @pure def two[T](first: T, second: T): Fork[T]
  @pure def three[T](first: T, second: T, third: T): Fork[T]
  @pure def four[T](first: T, second: T, third: T, fourth: T): Fork[T]
  @pure def five[T](first: T, second: T, third: T, fourth: T, fifth: T): Fork[T]
  @pure def six[T](first: T, second: T, third: T, fourth: T, fifth: T, sixth: T): Fork[T]
  @pure def seven[T](first: T, second: T, third: T, fourth: T, fifth: T, sixth: T, seventh: T): Fork[T]
  @pure def eight[T](first: T, second: T, third: T, fourth: T, fifth: T, sixth: T, seventh: T, eighth: T): Fork[T]
  @pure def nine[T](first: T, second: T, third: T, fourth: T, fifth: T, sixth: T, seventh: T, eighth: T, ninth: T): Fork[T]
}

@datatype class CoreCountableSources() extends CountableSources {

  @pure override def zero[T](): Fork[T] = {
    CoreFork(CorePipes(Xs.fp.lift[T => Unit @pure, Unit](Xs.fp.unit())))
  }

  @pure override def one[T](value: T): Fork[T] = {
    CoreFork(CorePipes((cons: T => Unit @pure) => {
      cons(value)
    }))
  }

  @pure override def two[T](first: T, second: T): Fork[T] = {
    CoreFork(CorePipes((cons: T => Unit @pure) => {
      cons(first)
      cons(second)
    }))
  }

  @pure override def three[T](first: T, second: T, third: T): Fork[T] = {
    CoreFork(CorePipes((cons: T => Unit @pure) => {
      cons(first)
      cons(second)
      cons(third)
    }))
  }

  @pure override def four[T](first: T, second: T, third: T, fourth: T): Fork[T] = {
    CoreFork(CorePipes((cons: T => Unit @pure) => {
      cons(first)
      cons(second)
      cons(third)
      cons(fourth)
    }))
  }

  @pure override def five[T](first: T, second: T, third: T, fourth: T, fifth: T): Fork[T] = {
    CoreFork(CorePipes((cons: T => Unit @pure) => {
      cons(first)
      cons(second)
      cons(third)
      cons(fourth)
      cons(fifth)
    }))
  }

  @pure override def six[T](first: T, second: T, third: T, fourth: T, fifth: T, sixth: T): Fork[T] = {
    CoreFork(CorePipes((cons: T => Unit @pure) => {
      cons(first)
      cons(second)
      cons(third)
      cons(fourth)
      cons(fifth)
      cons(sixth)
    }))
  }

  @pure override def seven[T](first: T, second: T, third: T, fourth: T, fifth: T, sixth: T, seventh: T): Fork[T] = {
    CoreFork(CorePipes((cons: T => Unit @pure) => {
      cons(first)
      cons(second)
      cons(third)
      cons(fourth)
      cons(fifth)
      cons(sixth)
      cons(seventh)
    }))
  }

  @pure override def eight[T](first: T, second: T, third: T, fourth: T, fifth: T, sixth: T, seventh: T, eighth: T): Fork[T] = {
    CoreFork(CorePipes((cons: T => Unit @pure) => {
      cons(first)
      cons(second)
      cons(third)
      cons(fourth)
      cons(fifth)
      cons(sixth)
      cons(seventh)
      cons(eighth)
    }))
  }

  @pure override def nine[T](first: T, second: T, third: T, fourth: T, fifth: T, sixth: T, seventh: T, eighth: T, ninth: T): Fork[T] = {
    CoreFork(CorePipes((cons: T => Unit @pure) => {
      cons(first)
      cons(second)
      cons(third)
      cons(fourth)
      cons(fifth)
      cons(sixth)
      cons(seventh)
      cons(eighth)
      cons(ninth)
    }))
  }

}

@sig sealed trait Fork[T] {
  @pure def ops: Ops[T]
  @pure def sink: Sinks[T]
}

@datatype class CoreFork[T](@hidden pipe: Pipes[T]) extends Fork[T] {
  @pure override def ops: Ops[T] = {
    CoreOps(pipe)
  }

  @pure override def sink: Sinks[T] = {
    CoreSinks(pipe)
  }
}

@sig sealed trait Ops[T] {
  @pure def map[R](mapper: T => R @pure): Ops[R]
  @pure def sink: Sinks[T]
}

@datatype class CoreOps[T](@hidden pipe: Pipes[T]) extends Ops[T] {
  @pure override def map[R](mapper: T => R @pure): Ops[R] = {
    return CoreOps(CorePipes((cons: R => Unit) => pipe.stage((e: T) => cons(mapper(e)))))
  }

  @pure override def sink: Sinks[T] = {
    return CoreSinks(pipe)
  }
}

@sig sealed trait Sinks[T] {
  @pure def scan(accumulator: (T, T) => T @pure): Option[T]
  @pure def reduce(initial: T, accumulator: (T, T) => T @pure): T
  @pure def combine[S](initial: S, accumulator: (S, T) => S @pure, combiner: (S, S) => S @pure): S
  @pure def count(): Z
}

@datatype class CoreSinks[T](@hidden pipe: Pipes[T]) extends Sinks[T] {

  @pure override def scan(accumulator: (T, T) => T @pure): Option[T] = {
    val box: UnitBox[Option[T]] = UnitBox(None[T]())
    pipe.consume((next: T) => box.map((opt: Option[T]) => {
      opt match {
        case Some(acc) => Some(accumulator(acc, next))
        case _ => Some(next)
      }
    }))
    return box.get()
  }

  @pure override def reduce(initial: T, accumulator: (T, T) => T @pure): T = {
    val box = UnitBox(initial)
    pipe.consume((next: T) => box.map((last: T) => accumulator(last, next)))
    return box.get()
  }

  @pure override def combine[S](initial: S, accumulator: (S, T) => S @pure, combiner: (S, S) => S @pure): S = {
    // todo combiner optimization
    val box = UnitBox(initial)
    pipe.consume((t: T) => box.map((s: S) => accumulator(s, t)))
    return box.get()
  }

  @pure override def count(): Z = {
    val box = UnitBox(z"0")
    pipe.consume(Xs.fp.lift(box.map(Xs.math.increase)))
    return box.get()
  }

}

@sig trait Pipes[T] {

  def stage: (T => Unit) => Unit

  def consume(inner: T => Unit): Unit = {
    stage(inner)
  }

  def transform(): Ops[T] = {
    return CoreOps(this)
  }
}

@datatype class CorePipes[T](@hidden val stage: (T => Unit) => Unit) extends Pipes[T] {}

@record class UnitBox[T](var data: T) {
  def get(): T = {
    return data
  }

  def set(o: T): Unit = {
    data = o
  }

  def map(mapper: T => T@pure): Unit = {
    set(mapper(get()))
  }
}