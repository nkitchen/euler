// The factor package provides functions for factoring numbers.
package factor

import (
	"primes"
)

// Factor returns the prime factors of n as a map to multiplicities.
func Factor(n int) map[int] int {
	if n <= 1 {
		return nil
	}

	factors := make(map[int] int)
	i := 0
	for n > 1 {
		p := primes.Nth(i)
		if n % p == 0 {
			m, _ := factors[p]
			factors[p] = m + 1
			n /= p
		} else {
			i += 1
		}
	}
	return factors
}

// Divisors returns the divisors of n, including n itself.
func Divisors(n int) <-chan int {
	if n <= 1 {
		return nil
	}

	return products(Factor(n))
}

// Returns all products of prime factors.
func products(factors map[int] int) <-chan int {
	out := make(chan int)
	if len(factors) == 0 {
		go func() {
			out <- 1
			close(out)
		}()
		return out
	}

	var f, m int
	for f, m = range factors {
		break
	}

	go func() {
		factors[f] = 0, false
		defer func() { factors[f] = m }()

		for p := range products(factors) {
			q := 1
			for i := 0; i <= m; i++ {
				out <- p * q
				q *= f
			}
		}
		close(out)
	}()
	return out
}
