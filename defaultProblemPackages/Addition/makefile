CXX = g++
CXXFLAGS = -O3 -Wall -Wextra -DNDEBUG -std=c++20

generator: prog/generator.cpp ../packageUtils.h
	$(CXX) $(CXXFLAGS) prog/generator.cpp -o generator

model: prog/model.cpp ../packageUtils.h
	$(CXX) $(CXXFLAGS) prog/model.cpp -o model

checker: prog/checker.cpp ../packageUtils.h
	$(CXX) $(CXXFLAGS) prog/checker.cpp -o checker
