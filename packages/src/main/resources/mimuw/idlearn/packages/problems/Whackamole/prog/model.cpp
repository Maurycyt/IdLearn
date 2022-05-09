#include <iostream>

using namespace std;

const int base = 1 << 17;
int tree[2 * base];

void update(int p, int h) {
	p += base;
	tree[p] = h;
	while (p > 1) {
		p /= 2;
		tree[p] = max(tree[2 * p], tree[2 * p + 1]);
	}
}

int main() {
	int n, q, p, h;
	cin >> n >> q;
	for (int i = 0; i < q; i++) {
		cin >> p >> h;
		update(p, h);
		cout << tree[1] << "\n";
	}
}