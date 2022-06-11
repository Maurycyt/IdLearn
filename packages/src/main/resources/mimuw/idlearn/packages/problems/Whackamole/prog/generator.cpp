#include <iostream>
#include <vector>
#include <cstring>
#include "../../packageUtils.h"

using namespace std;
using namespace pUtils;

const int MaxN = 1e5;
const int MaxQ = 1e5;
const int MaxH = 1e9;

struct MaxTree {
	int base;
	vector<pair<int,int>> tree;

	MaxTree() = default;

	MaxTree(int power) :
	base(1 << power),
	tree(vector<pair<int,int>>(2 * base)) {
		for (int i = base; i < 2 * base; i++) {
			tree[i].second = i - base;
		}
		for (int i = base - 1; i > 0; i--) {
			tree[i] = max(tree[2 * i], tree[2 * i + 1]);
		}
	}

	pair<int,int> getMax() {
		return tree[1];
	}

	void update(int p, int h) {
		p += base;
		tree[p].first = h;
		while (p > 1) {
			p /= 2;
			tree[p] = max(tree[2 * p], tree[2 * p + 1]);
		}
	}
};

struct TestSize {
	int n;
	int q;

	TestSize(int id) :
	n(min(id, MaxN)),
	q(min(id, MaxQ)) {
		// Seed the RNG.
		Random::seed(id);
	}
};

struct TestCase {
	unsigned int n, q;
	vector<unsigned int> P;
	vector<unsigned int> H;
	MaxTree tree;

	friend ostream & operator<< (ostream & out, const TestCase & t) {
		out << t.n << " " << t.q << "\n";
		for (unsigned int i = 0; i < t.q; i++) {
			out << t.P[i] << " " << t.H[i] << "\n";
		}
		return out;
	}

	TestCase(TestSize ts) :
	n(Random::rand<ui>() % ((ts.n + 1) / 2) + (ts.n / 2)),
	q(Random::rand<ui>() % ((ts.q + 1) / 2) + (ts.q / 2)) {
		if (n == 0) {
			q = 0;
		}
		P.resize(q);
		H.resize(q);
		int basePower = 1;
		while ((1 << basePower) <= int(n)) {
			basePower++;
		}
		tree = MaxTree(basePower);

		// For the first q / 2 queries: complete randomness
		for (unsigned int i = 0; i < q / 2; i++) {
			P[i] = Random::rand<ui>() % n + 1;
			H[i] = Random::rand<ui>() % (MaxH / MaxN * n);
			tree.update(P[i], H[i]);
		}
		// For the other q / 2 queries we decrease the maxima
		for (unsigned int i = q / 2; i < q; i++) {
			pair<int,int> previousMax = tree.getMax();
			P[i] = previousMax.second;
			H[i] = Random::rand<ui>() % max(previousMax.first, 1);
			tree.update(P[i], H[i]);
		}
	}
};

int main(int argc, char * * argv) {
	(void)argc;
	TestSize ts(Reader::parseULL(argv[1], strlen(argv[1])));
	TestCase tc(ts);
	cout << tc;
}