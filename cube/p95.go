package main

import (
	"factor"
	"flag"
	"fmt"
)

const MAXELEM = 1e6

func succ(n int) int {
	s := 0
	for d := range factor.Divisors(n) {
		if d != n {
			s += d
		}
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
	var longest Chain
	for n := 1; n < *max; n++ {
		if (n + 1) % 10000 == 0 {
			fmt.Print(".")
		}

		c := amicableChain(n)
		if c.length > longest.length {
			longest = c
			fmt.Println("New longest:", longest)
		}
	}
	fmt.Println(longest)
}
