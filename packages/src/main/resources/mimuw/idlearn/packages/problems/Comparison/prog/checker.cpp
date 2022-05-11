#include <iostream>
#include "../../packageUtils.h"

using namespace std;
using namespace pUtils;

/**
 * The checker should return a non-zero exit code ONLY on failure of execution.
 * A wrong user output should be reported by outputting "WRONG" to stdout and returning 0.
 */
int main(int argc, char * * argv) {
	if (argc != 4) {
		cerr << "Usage: " << argv[0] << " <input> <user output> <model output>\n";
		return 1;
	}

	Reader uOutput(argv[2]);
	Reader mOutput(argv[3]);

	bool result = true;

	try {
		result &= uOutput.read<si>() == mOutput.read<si>();
		result &= uOutput.eof();
	} catch (...) {
		result = false;
	}

	if (result) {
		cout << "OK\n";
	} else {
		cout << "WRONG\n";
	}
}