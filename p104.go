package main

import "math"
import "strconv"
import "sort"
import "container/vector"

func Equal(a, b []int) bool {
	if len(a) != len(b) {
		return false
	}
	for i := 0; i < len(a); i++ {
		if a[i] != b[i] {
			return false
		}
	}
	return true
}

func Digits(n int) []int {
	s := strconv.Itoa(n)
	d := make([]int, len(s))
	for i, c := range s {
		d[i] = int(c) - int('0')
	}
	return d
}

var sortedDigits = []int{1, 2, 3, 4, 5, 6, 7, 8, 9}
func IsPandigital(digits []int) bool {
	sort.SortInts(digits)
	return Equal(digits, sortedDigits)
}

var phi = (float64(1) + math.Sqrt(5)) / float64(2)
var A = math.Log10(phi)
var B = 0.5 * math.Log10(5)

var D = float64(1) / math.Sqrt(5)
func Fibonacci(n int) float64 {
	return (math.Pow(phi, float64(n)) - math.Pow(1 - phi, float64(n))) * D
}

// k digits of Fibonacci number n
func TopFibDigits(n, k int) []int {
	d := vector.IntVector(make([]int, 0))

	logF := float64(n) * A - B
	Fr := math.Pow(10, float64(k) + 2 + logF - math.Floor(logF))
	s := strconv.Ftoa64(Fr, 'e', k + 1)
	for _, c := range s {
		if c == 'e' || len(d) == k {
			break
		}
		i := int(c) - int('0')
		if 0 <= i && i <= 9 {
			d.Push(i)
		}
	}
	return d.Data()
}

func main() {
	a := 1
	b := 1
	i := 1

	for {
		if IsPandigital(Digits(a)) {
			println(i)
			d := TopFibDigits(i, 9)
			if IsPandigital(d) {
				break;
			}
		}
		c := (a + b) % 1000000000
		a, b = b, c
		i++
	}
}
