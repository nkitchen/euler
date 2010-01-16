package main

import (
	"flag"
	"fmt"
)

const MAXELEM = 1e6

func append(a []int, x int) []int {
	if 1+len(a) > cap(a) {
		// Reallocate
		b := make([]int, (1+cap(a))*2)
		copy(b, a)
		a = b[0:len(a)]
	}
	a = a[0 : 1+len(a)]
	a[len(a)-1] = x
	return a
}

var divMemo = make(map[int] []int)
func properDivisors(n int) []int {
	divs, found := divMemo[n]
	if !found {
		divs = make([]int, 0, 4)
		for i := 1; i <= n/2; i++ {
			if n%i == 0 {
				divs = append(divs, i)
			}
		}
		divMemo[n] = divs
	}
	return divs
}

func succ(n int) int {
	s := 0
	for _, k := range properDivisors(n) {
		s += k
	}
	return s
}

type Chain struct {
	length int
	min int
}

var chainMemo = make(map[int]Chain)

func amicableChain(n int) Chain { return amicableChain2(n, make(map[int]int)) }

func amicableChain2(n int, seen map[int]int) Chain {
	if n > MAXELEM || n == 1 {
		return Chain{ 0, 0 }
	}

	result, found := chainMemo[n]
	if !found {
		if times, _ := seen[n]; times == 2 {
			result.length = 0
			result.min = -1
			for k, times := range seen {
				if times == 2 {
					result.length++
					if result.min == -1 || k < result.min {
						result.min = k
					}
				}
			}
		} else {
			seen[n] = 1 + times
			defer func() { seen[n] = times }()
			result = amicableChain2(succ(n), seen)
		}
		chainMemo[n] = result
	}
	return result
}

var max *int = flag.Int("max", 100, "Number")

func main() {
	flag.Parse()
	for n := 1; n < *max; n++ {
		fmt.Print(n, " ")
		c := amicableChain(n)
		if c.length == 0 {
			fmt.Println("nil")
		} else {
			fmt.Println(c)
		}
	}

	fmt.Println(15472, amicableChain(15472))
}
