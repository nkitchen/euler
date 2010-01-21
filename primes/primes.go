// The primes package provides prime numbers.
package primes

// #include "data.h"
import "C"

import (
	"container/vector"
)

func generate() chan int {
	ch := make(chan int)
	go func() {
		for i := 2; ; i++ {
			ch <- i
		}
	}()
	return ch
}

func filter(in chan int, prime int) chan int {
	out := make(chan int)
	go func() {
		for {
			if i := <-in; i%prime != 0 {
				out <- i
			}
		}
	}()
	return out
}

func sieve() chan int {
	out := make(chan int)
	go func() {
		ch := generate()
		for {
			prime := <-ch
			out <- prime
			ch = filter(ch, prime)
		}
	}()
	return out
}

// UpTo returns all primes <= max.
func UpTo(max int) []int {
	var a vector.IntVector
	for p := range sieve() {
		if p > max {
			return a.Data()
		}
		a.Push(p)
	}
	return nil
}

// Nth returns the nth prime.
func Nth(n int) int {
	p := C.GetPrime((C.int)(n))
	return int(p)
}
