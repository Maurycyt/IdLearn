CXX = g++-10
CXXFLAGS = -O3 -Wall -Wextra -DNDEBUG -std=c++20
TestID = 1

.PHONY: all
all: generator model checker

.PHONY: clean
clean:
	rm -f generator model checker input.in model.out user.out

generator: prog/generator.cpp ../packageUtils.h
	$(CXX) $(CXXFLAGS) prog/generator.cpp -o generator

model: prog/model.cpp ../packageUtils.h
	$(CXX) $(CXXFLAGS) prog/model.cpp -o model

checker: prog/checker.cpp ../packageUtils.h
	$(CXX) $(CXXFLAGS) prog/checker.cpp -o checker

input: generator
	./generator $(TestID) > input.in

model.out: input.in model
	./model < input.in > model.out

.PHONY: check
check: checker
	./checker input.in user.out model.out