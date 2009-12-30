package main

import "os"

func step(n int) int {
    s := 0;
    for n > 0 {
        d := n % 10;
        n /= 10;
        s += d * d;
    }
    return s;
}

var memo map[int] int = make(map[int] int);
func dest(n int) int {
    if n < 1 {
        return 0;
    }

    if n == 1 || n == 89 {
        return n;
    }

    if r, ok := memo[n]; ok {
        return r;
    }

    r := dest(step(n));
    if n < 600 {
        memo[n] = r;
    }
    return r;
}

func main() {
    n := 0;
    for i := 1; i < 10000000; i++ {
        if (i + 1) % 100000 == 0 {
            os.Stderr.WriteString(".");
        }
        if dest(i) == 89 {
            n++;
        }
    }
    println(n);
}
