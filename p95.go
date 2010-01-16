package main

import (
	"flag"
	"fmt"
	"sort"
)

const MAXELEM = 1e6

func properDivisors(n int) <-chan int {
	out := make(chan int)
	go func() {
		for i := 1; i <= n/2; i++ {
			if n%i == 0 {
				out <- i
			}
		}
		close(out)
	}()
	return out
}

func succ(n int) int {
	s := 0
	for k := range properDivisors(n) {
		s += k
	}
	return s
}

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

var memo = make(map[int][]int)

func amicableChain(n int) []int { return amicableChain2(n, make(map[int]int)) }

func amicableChain2(n int, seen map[int]int) []int {
	if n > MAXELEM || n == 1 {
		return nil
	}

	result, found := memo[n]
	if !found {
		if times, _ := seen[n]; times == 2 {
			result = make([]int, 0, 4)
			for k, times := range seen {
				if times == 2 {
					result = append(result, k)
				}
			}
			sort.SortInts(result)
		} else {
			seen[n] = 1 + times
			defer func() { seen[n] = times }()
			result = amicableChain2(succ(n), seen)
		}
		memo[n] = result
	}
	return result
}

var max *int = flag.Int("max", 100, "Number")

func main() {
	flag.Parse()
	for n := 1; n < *max; n++ {
		fmt.Print(n, " ")
		c := amicableChain(n)
		if c == nil {
			fmt.Println("nil")
		} else {
			fmt.Println(c)
		}
	}
}
