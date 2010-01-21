LIBRARY = libprimes.so

all : $(LIBRARY)

install : $(LIBRARY)
	cp $< $(HOME)/lib/$(LIBRARY)

$(LIBRARY) : data.o
	$(CC) $(LDFLAGS) -shared -fPIC -o $@ $^


