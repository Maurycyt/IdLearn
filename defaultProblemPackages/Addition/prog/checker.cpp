#include <ifstream>
#include <iostream>

using std::ifstream;
using std::cerr;
using std::cout;

int main(int argc, char * * argv) {
	if (argc != 4) {
		cerr << "Usage: " << argv[0] << " <input> <user output> <model output>\n";
		return 1;
	}

	ifstream input(argv[1]);
	ifstream uOutput(argv[2]);
	ifstream mOutput(argv[3]);

	
}