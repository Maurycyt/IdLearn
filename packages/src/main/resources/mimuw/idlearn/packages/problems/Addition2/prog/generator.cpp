#include <iostream>
#include <vector>
#include <cstring>
#include "../../packageUtils.h"

using namespace std;
using namespace pUtils;

const int MaxN = 1e6;

struct TestSize {
	int n;

	TestSize(size_t id) : n(min(id, size_t(MaxN))) {
		// Seed the RNG.
		Random::seed(id);
	}
};

struct TestCase {
	unsigned int n;
	vector<unsigned int> A;

	friend ostream & operator<< (ostream & out, const TestCase & t) {
		out << t.n << "\n";
		for (int a : t.A) {
			out << a << " ";
		}
		return out << "\n";
	}

	TestCase(TestSize ts) :
	n(ts.n) {
		A.resize(n);
		for (unsigned int i = 0; i < n; i++) {
			A[i] = Random::rand<ui>() % min(n, (ui)1000);
		}
	}
};

int main(int argc, char * * argv) {
	(void)argc;
	TestSize ts(Reader::parseULL(argv[1], strlen(argv[1])));
	TestCase tc(ts);
	cout << tc;
}