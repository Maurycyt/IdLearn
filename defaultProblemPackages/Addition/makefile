include makefile.in

CXX = g++
CXXFLAGS = -O3 -Wall -Wextra -DNDEBUG -std=c++20
TestID = 1

.PHONY: all, clean, input, check

all: $(GEN) $(MOD) $(CHE)

clean:
	rm -f $(GEN) $(MOD) $(CHE) $(INPUT) $(USER_OUT) $(MODEL_OUT)

$(GEN): $(GEN_SOURCE) $(UTILS)
	$(CXX) $(CXXFLAGS) $(GEN_SOURCE) -o $(GEN)

$(MOD): $(MOD_SOURCE) $(UTILS)
	$(CXX) $(CXXFLAGS) $(MOD_SOURCE) -o $(MOD)

$(CHE): $(CHE_SOURCE) $(UTILS)
	$(CXX) $(CXXFLAGS) $(CHE_SOURCE) -o $(CHE)

input: $(GEN)
	$(GEN) $(TestID) > $(INPUT)

$(MODEL_OUT): $(INPUT) $(MOD)
	$(MOD) < $(INPUT) > $(MODEL_OUT)

check: $(CHE) $(INPUT) $(USER_OUT) $(MODEL_OUT)
	$(CHE) $(INPUT) $(USER_OUT) $(MODEL_OUT)