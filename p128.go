package main

import "big"
import "flag"
import "fmt"
import "math"

var _ = fmt.Printf

// Returns the index of the first tile in a ring.
func firstInRing(r int64) int64 {
	if r < 0 {
		return -1
	}
	if r == 0 {
		return 1
	}
	return 2 + 3 * r * (r - 1)
}

// Converts a tile number to (ring, side, offset) coordinates.
func nToRsi(n int64) (r, s, i int64) {
	if n == 1 {
		return 0, 0, 0
	}

	disc := 9 + 12*(n - 2)
	r = int64(math.Floor((3 + math.Sqrt(float64(disc))) / 6))

	f := firstInRing(r)
	d := n - f

	per_side := r
	s = d / per_side
	i = d % per_side
	return
}

func rsiToN(r, s, i int64) int64 {
	if r == 0 && s == 0 && i == 0 {
		return 1
	}

	return firstInRing(r) + r*s + i
}

func rsiToXy(r, s, i int64) (x, y float64) {
	if r <= 0 {
		return 0, 0
	}

	if s >= 3 {
		x, y = rsiToXy(r, s - 3, i)
		return -x, -y
	}

	fr := float64(r)
	fi := float64(i)

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
	default:
		panic("Bad side number")
	}
	return
}

func xyToRsi(x, y float64) (r, s, i int64) {
	if x == 0 && y == 0 {
		return 0, 0, 0
	}

	if x > 0 && y >= 0 || x >= 0 && y < 0 {
		r, s, i = xyToRsi(-x, -y)
		return r, s + 3, i
	}

	switch {
	case y > 0:
		r = int64(y - 0.5*x)
		i = int64(-x)
		if i < r {
			s = 0; return
		}
	case y <= 0:
		r = int64(-y - 0.5*x)
		i = int64(0.5*x - y)
		if i >= 0 {
			s = 2; return
		}
	}

	s = 1
	r = int64(-x)
	i = int64(-0.5*x - y)
	return
}

type xy struct { x, y float64 }

var deltas = []xy{
	xy{0, 1},
	xy{-1, 0.5},
	xy{-1, -0.5},
	xy{0, -1},
	xy{1, -0.5},
	xy{1, 0.5},
}
func neighbors(x, y float64) []xy {
	a := make([]xy, len(deltas))
	for i, d := range deltas {
		a[i] = xy{x + d.x, y + d.y}
	}
	return a
}

func abs(x int64) int64 {
	if x < 0 {
		return -x
	}
	return x
}

func isPrime(n int64) bool {
	z := big.NewInt(n)
	return big.ProbablyPrime(z, 20)
}

func pd(n int64, x, y float64) int {
	c := 0
	for _, xy := range neighbors(x, y) {
		r, s, i := xyToRsi(xy.x, xy.y)
		m := rsiToN(r, s, i)
		if isPrime(abs(n - m)) {
			c += 1
		}
	}
	return c
}

func pd3ers() <-chan int64 {
	out := make(chan int64)

	go func() {
		r := int64(0)
		for {
			x := float64(0)
			y := float64(r)
			n := rsiToN(r, 0, 0)
			if pd(n, x, y) == 3 {
				out <- n
			}

			if r > 0 {
				x = float64(1)
				y = float64(r) - 0.5
				n = rsiToN(r, 5, r - 1)
				if pd(n, x, y) == 3 {
					out <- n
				}
			}

			r++
		}
	}()

	return out
}

func main() {
	kp := flag.Int("k", 10, "Print the kth tile for which PD(n) = 3")
	flag.Parse()

	s := pd3ers()
	for i := 0; i < *kp - 1; i++ {
		if i % 100 == 0 {
			print(".")
		}
		<-s
	}

	n := <-s
	println(n)
}
