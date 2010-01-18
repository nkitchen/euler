package main

import (
	"flag"
	"fmt"
	"primes"
)

var max = flag.Int("max", 100, "max")

func main() {
	flag.Parse()
	ps := primes.UpTo(*max)
	fmt.Println(ps[len(ps)-1])
}
