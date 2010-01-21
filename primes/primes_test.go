package primes

import "testing"

const CHECKNUM = 1000

func TestFirst(t *testing.T) {
	sieved := UpTo(Nth(CHECKNUM-1))
	for i := 0; i < CHECKNUM; i++ {
		x := sieved[i]
		y := Nth(i)
		if x != y {
			t.Errorf("prime #%d from UpTo and Nth don't match: " +
			         "UpTo => %d, Nth => %d", i, x, y)
			break
		}
	}
}
