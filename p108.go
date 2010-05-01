package main

import "container/vector"
import "fmt"
import "primes"
import "sort"

var firstPrimes []int

func init() {
	var a vector.IntVector
	i := 0
	for {
		p := primes.Nth(i)
		if p > 99999 {
			break
		}
		a.Push(p)
		i++
	}
	firstPrimes = a.Data()
}

func divisors(n int) []int {
	seen := make(map[int]bool)
	seen[1] = true
	for _, p := range firstPrimes {
		if p * p > n {
			break
		}
		for k := 1; k * p <= n; k++ {
			d := k * p
			if n % d == 0 {
				seen[d] = true
				seen[n / d] = true
			}
		}
	}
	var a vector.IntVector
	for d := range seen {
		a.Push(d)
	}
	b := a.Data()
	sort.SortInts(b)
	return b
}

func main() {
	for n := 4; ; n++ {
		if n % 1000 == 0 {
			print(".")
		}

		seen := make(map[string]bool)
		for _, p := range divisors(n) {
			if p * p > n {
				break;
			}
			q := n / p
			for a := q + 1; ; a++ {
				n64 := int64(n)
				x := int64(a) * int64(p)
				if x > int64(2 * n) {
					break;
				}
				c := x * n64
				d := x - n64
				if c % d == 0 {
					y := c / d
					if x > y {
						x, y = y, x
					}
					// fmt.Printf("p=%v q=%v a=%v x=%v y=%v\n", p, q, a, x, y)
					s := fmt.Sprintf("%v:%v", x, y)
					seen[s] = true
				}
			}
		}

		//for s := range seen {
		//	println(s)
		//}
		if len(seen) > 1000 {
			println(n)
			break
		}
	}
}
