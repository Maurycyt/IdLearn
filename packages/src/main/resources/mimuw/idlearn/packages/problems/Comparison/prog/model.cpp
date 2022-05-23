#include <iostream>

using namespace std;

int main() {
	int q, a, b;
	cin >> q >> a >> b;
	cout << ((q % 2) ? min(a, b) : max(a, b));
}