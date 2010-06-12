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
    char ndigits[80];
    char tdigits[80];
    int nbest = 0;
    double rbest = 2.0;
    for (int n = 2; n < 10000000; ++n) {
        int t = totient(n);
        if (n % 10000 == 9999) {
            printf(".");
            fflush(stdout);
        }
        sprintf(ndigits, "%d", n);
        sprintf(tdigits, "%d", t);
        sort(ndigits, ndigits + strlen(ndigits));
        sort(tdigits, tdigits + strlen(tdigits));
        if (strcmp(ndigits, tdigits) != 0) {
            continue;
        }
        
        double r = double(n) / double(t);
        if (r < rbest) {
            nbest = n;
            rbest = r;
        }
    }
    printf("%d\n", nbest);
    return 0;
}
