ifeq ($(GOARCH),x86)
    O = 8
else ifeq ($(GOARCH),amd64)
    O = 6
endif

GC = $(O)g
GLD = $(O)l

all:

% : %.$(O)
	$(GLD) -o $@ $<

%.$(O) : %.go
	$(GC) $<
