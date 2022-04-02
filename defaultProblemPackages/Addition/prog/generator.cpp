#include <iostream>
#include <cstring>
#include "../../packageUtils.h"

using namespace std;
using namespace pUtils;

struct TestSize {
	int n;

	TestSize(size_t id) : n(min(id, size_t(1e9))) {
	}
};

struct TestCase {
	int a, b;

	friend ostream & operator<< (ostream & out, const TestCase & t) {
		return out << t.a << " " << t.b << "\n";
	}

	TestCase(TestSize ts) : a(Random::rand<si>() % ts.n), b(Random::rand<si>() % ts.n) {
	}
};

int main(int argc, char * * argv) {
	(void)argc;
	TestSize ts(Reader::parseULL(argv[1], strlen(argv[1])));
	TestCase tc(ts);
	cout << tc;
}