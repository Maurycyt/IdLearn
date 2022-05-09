#include <iostream>
#include <cstring>
#include "../../packageUtils.h"

using namespace std;
using namespace pUtils;

struct TestSize {
	unsigned int n;

	TestSize(size_t id) :
	n(min(id, size_t(1e9))) {
		// Seed the RNG.
		Random::seed(id);
	}
};

struct TestCase {
	unsigned int a, b;

	friend ostream & operator<< (ostream & out, const TestCase & t) {
		return out << t.a << " " << t.b << "\n";
	}

	TestCase(TestSize ts) {
		if (Random::rand<ui>() % 2) {
			// Random test case
			a = Random::rand<ui>() % ts.n + 1;
			b = Random::rand<ui>() % ts.n + 1;
		} else {
			// Fibonacci test case
			a = 0, b = 1;
			while (a + b <= ts.n) {
				a = a + b;
				swap(a, b);
			}
			if (Random::rand<ui>() % 2) {
				swap(a, b);
			}
		}
	}
};

int main(int argc, char * * argv) {
	(void)argc;
	TestSize ts(Reader::parseULL(argv[1], strlen(argv[1])));
	TestCase tc(ts);
	cout << tc;
}