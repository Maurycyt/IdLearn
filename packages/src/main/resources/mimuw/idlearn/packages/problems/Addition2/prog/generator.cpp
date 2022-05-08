#include <iostream>
#include <cstring>
#include "../../packageUtils.h"

using namespace std;
using namespace pUtils;

struct TestSize {
	int n;

	TestSize(size_t id) : n(min(id, size_t(1e9))) {
		// Seed the RNG.
		Random::seed(id);
	}
};

struct TestCase {
	unsigned int n;
	vector<unsigned int> A;

	friend ostream & operator<< (ostream & out, const TestCase & t) {
		out << t.n << "\n";
		for (int i : t.A) {
			out << i << " ";
		}
		return out;
	}

	TestCase(TestSize ts) :
	n(ts.n) {
		A.resize(n);
		for (int i = 0; i < n; i++) {
			A[i] = Random::rand<ui> % min(n, 1000);
		}
	}
};

int main(int argc, char * * argv) {
	(void)argc;
	TestSize ts(Reader::parseULL(argv[1], strlen(argv[1])));
	TestCase tc(ts);
	cout << tc;
}