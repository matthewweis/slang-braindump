// #Sireum #Logika

package puzzlers.sets

import org.sireum._

object Sets {

  type ZS = IS[Z,Z]
  type SZ = SZ

  @strictpure def identical[I, T](seq1: IS[I, T], seq2: IS[I, T]): B =
    seq1 == seq2
  // or, element-wise checks with short-circuit size optimization
  // seq1.size == seq2.size && ∀(seq1.indices)(i => ∃(seq2.indices)(j => (i == j) ->: (seq1(i) == seq2(j))))

  @strictpure def sublist[I, T](list: IS[I, T], sublist: IS[I, T]): B =
    sublist.size <= list.size && ∀(sublist.indices)(i => ∃(list.indices)(j => sublist(i) == list(j)))

  // bag/multiset equality (i.e. order agnostic) (i.e. does a shuffle exist for which both are identical ordered lists)
  @strictpure def interchangeable[I, T](bag1: IS[I, T], bag2: IS[I, T]): B = // unneccesary check due to size check?
    bag1.size == bag2.size && ∀(bag1.indices)(i => ∃(bag2.indices)(j => bag1(i) == bag2(j)))
    // or, likely less optimal route:
    // ∀(bag1.indices)(i => ∃(bag2.indices)(j => bag1(i) == bag2(j)))// && ∀(bag2.indices)(i => ∃(bag1.indices)(j => bag2(i) == bag1(j)))

  @strictpure def not_unique[T](elements: ISZ[T]): B =
    ∃(elements.indices)(i => ∃(elements.indices)(j => (i != j) ->: (elements(i) == elements(j))))
  // or, more simply
  // !Set.Elements.unique(elements)

  // KEY INSIGHT: logika deduces 'Contract(Requires(P))' if 'assert(P)' occurs in all code paths
  @pure def assert_unique(xs: ISZ[Z]): ISZ[Z] = {
    Contract(
      Requires(Set.Elements.unique(xs)),
      Ensures(
        identical(Res, xs),
        Set.Elements.unique(Res[ISZ[Z]])
      )
    )
    assert(Set.Elements.unique(xs))
    return xs
  }


  // no duplicates
  val unique1: ZS = ISZ(z"1")
  val unique2: ZS = ISZ(z"1", z"2")
  val unique3: ZS = ISZ(z"1", z"2", z"3")
  val unique4: ZS = ISZ(z"1", z"2", z"3", z"4")
  val unique5: ZS = ISZ(z"1", z"2", z"3", z"4", z"5")
  val unique6: ZS = ISZ(z"1", z"2", z"3", z"4", z"5", z"6")
  val unique7: ZS = ISZ(z"1", z"2", z"3", z"4", z"5", z"6", z"7")
  @spec def contract_unique1 = Invariant(Set.Elements.unique(unique1))
  @spec def contract_unique2 = Invariant(Set.Elements.unique(unique2))
  @spec def contract_unique3 = Invariant(Set.Elements.unique(unique3))
  @spec def contract_unique4 = Invariant(Set.Elements.unique(unique4))
  @spec def contract_unique5 = Invariant(Set.Elements.unique(unique5))
  @spec def contract_unique6 = Invariant(Set.Elements.unique(unique6))
  @spec def contract_unique7 = Invariant(Set.Elements.unique(unique7))

  // duplicates
  val duplicate1: ZS = ISZ(z"1", z"1")
  val duplicate2: ZS = ISZ(z"1", z"1", z"2", z"2")
  val duplicate3: ZS = ISZ(z"1", z"1", z"2", z"2", z"3", z"3")
  val duplicate4: ZS = ISZ(z"1", z"1", z"2", z"2", z"3", z"3", z"4", z"4")
  val duplicate5: ZS = ISZ(z"1", z"1", z"2", z"2", z"3", z"3", z"4", z"4", z"5", z"5")
  val duplicate6: ZS = ISZ(z"1", z"1", z"2", z"2", z"3", z"3", z"4", z"4", z"5", z"5", z"5", z"6")
  val duplicate7: ZS = ISZ(z"1", z"1", z"2", z"2", z"3", z"3", z"4", z"4", z"5", z"5", z"5", z"6", z"6", z"7", z"7")
  @spec def contract_duplicate1 = Invariant(!Set.Elements.unique(unique1))
  @spec def contract_duplicate2 = Invariant(!Set.Elements.unique(unique2))
  @spec def contract_duplicate3 = Invariant(!Set.Elements.unique(unique3))
  @spec def contract_duplicate4 = Invariant(!Set.Elements.unique(unique4))
  @spec def contract_duplicate5 = Invariant(!Set.Elements.unique(unique5))
  @spec def contract_duplicate6 = Invariant(!Set.Elements.unique(unique6))
  @spec def contract_duplicate7 = Invariant(!Set.Elements.unique(unique7))

  // enforce by contract: "zs contains no duplicate elements"
  @pure def set_by_contract(zs: ZS): SZ = {
    Contract(
      Requires(Set.Elements.unique(zs)),
      Ensures(
        Res == Set(zs),
        interchangeable(Set.elementsOf(Res[SZ]), zs),
      )
    )
    val result = Set(zs)
    return result
  }

  // enforce by implementation
  @pure def set_by_impl(values: ZS): SZ = {
    Contract(
      Ensures(
        Set.Elements.unique(values) ->: (Res == Set(values)),
        !Set.Elements.unique(values) ->: sublist(values, Set.elementsOf(Res[SZ])),
        Res == Set(values),
        Set.elementsOf(Res[SZ]).size <= values.size, // our impl removes dupes, so need inequality
        interchangeable(Set.elementsOf(Res[SZ]), values)
      )
    )
    val res = Set.empty[Z] ++ values
    // todo prove for each iteration
    assert(Set.Elements.unique(values) ->: (res == Set(values)))
    // todo while loop proof w/ invariant
    assert(Set.Elements.unique(res.elements))
    return res
  }

  @pure def assert_equals(expected: SZ, actual: SZ): Unit = {
    Contract(Requires(expected == actual), Ensures(expected == actual))
    assert(expected == actual)
  }

  @pure def assert_not_equals(expected: SZ, actual: SZ): Unit = {
    Contract(Requires(expected != actual), Ensures(expected != actual))
    assert(expected != actual)
  }
}