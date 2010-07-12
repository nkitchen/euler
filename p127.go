package main

import "flag"
import "fmt"
import "sort"

var _ = fmt.Printf

var radicals []int

type IntArray struct {
	sort.IntArray
}

func (a IntArray) Less(i, j int) bool {
	b := []int(a.IntArray)
	return radicals[b[i]] < radicals[b[j]]
}

func gcd(a, b int) int {
	if b == 0 {
		return a
	}
	return gcd(b, a % b)
}

func main() {
	mp := flag.Int("m", 1000, "Upper bound on c")
	flag.Parse()

	m := *mp

	radicals = make([]int, m)
	for i := 1; i < len(radicals); i++ {
		radicals[i] = 1
	}

	for i := 2; i < len(radicals); i++ {
		if radicals[i] == 1 {
			for j := i; j < len(radicals); j += i {
				radicals[j] *= i
			}
		}
	}

	v := make([]int, m/2)
	for i := 1; i < len(v); i++ {
		v[i] = i
	}

	w := IntArray{sort.IntArray(v)}
	sort.Sort(w)

	s := int64(0)
	for b := 2; b < m - 1; b++ {
		for _, a := range(v) {
			if a == 0 || a >= b || gcd(a, b) != 1 {
				continue
			}
			c := a + b
			if c >= m {
				continue
			}

			if gcd(a, c) * gcd(b, c) != 1 {
				panic("Sum of co-prime numbers is not co-prime")
			}

			ra := radicals[a]
			rb := radicals[b]
			rc := radicals[c]

			if ra * rb >= c {
				break
			}

			if ra * rb * rc < c {
				s += int64(c)
			}
		}
	}

	println(s)

	s = int64(0)
	for b := 2; b < m - 1; b++ {
		for a := 1; a < b; a++ {
			if gcd(a, b) != 1 {
				continue
			}

			c := a + b
			if c >= m {
				continue
			}

			ra := radicals[a]
			rb := radicals[b]
			rc := radicals[c]

			if ra * rb * rc < c {
				s += int64(c)
			}
		}
	}
	println(s)
}
