include $(GOROOT)/src/Make.$(GOARCH)

TARG = primes

CGOFILES = primes.go
CGO_CFLAGS = -Idata
CGO_LDFLAGS += -L$(HOME)/lib -lprimes

include $(GOROOT)/src/Make.pkg
