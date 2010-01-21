package factor

import (
		"fmt"
		"reflect"
		"sort"
		"testing"
		"container/vector"
)

type Factors map[int] int

func factorsEqual(a, b Factors) bool {
	return fmt.Sprintf("%v", a) == fmt.Sprintf("%v", b)
}

func TestFactor(t *testing.T) {
	type Test struct {
		n int
		factors Factors
	}
	tests := []Test {
		Test{ 2, map[int] int {2: 1} },
		Test{ 3, map[int] int {3: 1} },
		Test{ 4, map[int] int {2: 2} },
		Test{ 12, map[int] int {2: 2, 3: 1} },
		Test{ 100, map[int] int {2: 2, 5: 2} },
		Test{ 144, map[int] int {2: 4, 3: 2} },
		Test{ 79999, map[int] int {79999: 1} },
	}

	for _, test := range tests {
		factors := Factor(test.n)
		if !factorsEqual(factors, test.factors) {
			t.Errorf("Factor(%v) => %v, want %v",
			         test.n, factors, test.factors)
		}
	}
}

func collect(c <-chan int) []int {
	a := new(vector.IntVector)
	for x := range c {
		a.Push(x)
	}
	b := a.Data()
	sort.SortInts(b)
	return b
}

func TestDivisors(t *testing.T) {
	type Test struct {
		n int
		divisors []int
	}
	tests := []Test {
		Test{ 2, []int { 1, 2 } },
		Test{ 4, []int { 1, 2, 4 } },
		Test{ 12, []int { 1, 2, 3, 4, 6, 12 } },
		Test{ 16, []int { 1, 2, 4, 8, 16 } },
		Test{ 100, []int { 1, 2, 4, 5, 10, 20, 25, 50, 100 } },
	}

	for _, test := range tests {
		divisors := collect(Divisors(test.n))
		if !reflect.DeepEqual(divisors, test.divisors) {
			t.Errorf("Divisors(%v) => %v, want %v",
			         test.n, divisors, test.divisors)
		}
	}
}
