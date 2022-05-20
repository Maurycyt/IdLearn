include makefile.in

CXX = g++
CXX_FLAGS = -O3 -Wall -Wextra -DNDEBUG -std=c++20
TEST_ID = 1

.PHONY: all, clean, modelOut, input, check

all: $(GEN) $(MOD) $(CHE)

cleanTests:
	rm -f $(INPUT)* $(USER_OUT)* $(MODEL_OUT)*

clean: cleanTests
	rm -f $(GEN) $(MOD) $(CHE)

$(GEN): $(GEN_SOURCE) $(UTILS)
	$(CXX) $(CXX_FLAGS) $(GEN_SOURCE) -o $(GEN)

$(MOD): $(MOD_SOURCE) $(UTILS)
	$(CXX) $(CXX_FLAGS) $(MOD_SOURCE) -o $(MOD)

$(CHE): $(CHE_SOURCE) $(UTILS)
	$(CXX) $(CXX_FLAGS) $(CHE_SOURCE) -o $(CHE)

$(INPUT)$(TEST_ID): $(GEN)
	$(GEN) $(TEST_ID) > $(INPUT)$(TEST_ID)

input: $(INPUT)$(TEST_ID)

$(MODEL_OUT)$(TEST_ID): $(INPUT)$(TEST_ID) $(MOD)
	$(MOD) < $(INPUT)$(TEST_ID) > $(MODEL_OUT)$(TEST_ID)

modelOut: $(MODEL_OUT)$(TEST_ID)

check: $(CHE) $(INPUT)$(TEST_ID) $(USER_OUT)$(TEST_ID) $(MODEL_OUT)$(TEST_ID)
	$(CHE) $(INPUT)$(TEST_ID) $(USER_OUT)$(TEST_ID) $(MODEL_OUT)$(TEST_ID)