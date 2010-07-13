package main

import "flag"
import "math"

// Converts a tile number to (ring, side, offset) coordinates.
func nToRSI(n int) (r, s, i int) {
	if n == 1 {
		return 0, 0, 0
	}

	disc := 9 + 12*(n - 2)
	r = int(math.Floor((3 + math.Sqrt(float64(disc))) / 6))
	s = 0
	i = 0
	return
}

func main() {
	kp := flag.Int("k", 10, "Print the kth tile for which PD(n) = 3")
	flag.Parse()

	for n := 1; n <= *kp; n++ {
		r, s, i := nToRSI(n)
		r += s + i
		println(n, r)
	}
}
