package primes

import "testing"

const CHECKNUM = 100

func TestFirstFew(t *testing.T) {
	sieved := UpTo(First[CHECKNUM-1])
	for i := 0; i < CHECKNUM; i++ {
		x := sieved[i]
		y := First[i]
		if x != y {
			t.Errorf("prime #%d from UpTo and First don't match: " +
			         "UpTo => %d, First => %d", i, x, y)
			break
		}
	}
}
