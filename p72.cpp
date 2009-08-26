#include <algorithm>
#include <cstdio>
#include <cstring>
#include "primes.h"

using namespace std;

int totient(int n) {
    if (n <= 0) {
        return 0;
    }

    int ans = n;
    for (int i = 0; primes[i] <= n; ++i) {
        if (n % primes[i] == 0) {
            ans /= primes[i];
            ans *= primes[i] - 1;

            while (n % primes[i] == 0) {
                n /= primes[i];
            }
        }
    }
    return ans;
}

int main() {
    long long s = 0;
    for (int n = 2; n <= 1000000; ++n) {
        int t = totient(n);
        s += totient(n);
    }
    printf("%lld\n", s);
    return 0;
}
