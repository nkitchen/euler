package main

import "flag"
import "fmt"
import "math"

// Returns the index of the first tile in a ring.
func firstInRing(r int) int {
	if r < 0 {
		return -1
	}
	if r == 0 {
		return 1
	}
	return 2 + 3 * r * (r - 1)
}

// Converts a tile number to (ring, side, offset) coordinates.
func nToRsi(n int) (r, s, i int) {
	if n == 1 {
		return 0, 0, 0
	}

	disc := 9 + 12*(n - 2)
	r = int(math.Floor((3 + math.Sqrt(float64(disc))) / 6))

	f := firstInRing(r)
	d := n - f

	per_side := r
	s = d / per_side
	i = d % per_side
	return
}

func rsiToXy(r, s, i int) (x, y float) {
	if r <= 0 {
		return 0, 0
	}

	fr := float(r)
	fi := float(i)

	switch s {
	case 0:
		x = -fi
		y = fr - 0.5*fi
	case 1:
		x = -fr
		y = fr*0.5 - fi
	case 2:
	    x = -fr + fi
		y = -fr*0.5 - 0.5*fi
	case 3:
	    x = fi
		y = -fr + 0.5*fi
	case 4:
		x = fr
		y = -fr*0.5 + fi
	case 5:
		x = fr - fi
		y = fr*0.5 + 0.5*fi
	default:
		panic("Bad side number")
	}
	return
}

func main() {
	kp := flag.Int("k", 10, "Print the kth tile for which PD(n) = 3")
	flag.Parse()

	for n := 1; n <= *kp; n++ {
		x, y := rsiToXy(nToRsi(n))
		fmt.Printf("%2d %2.1f %2.1f\n", n, x, y)
	}
}
