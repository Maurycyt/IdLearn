#include "packageUtils.h"
#include <iostream>
#include <cassert>
#include <cstring> // std::memset
#include <cstdio>  // std::remove
#include <vector>

using namespace pUtils;

/**
 * Checks if the distribution of randomly generated integral numbers is uniform.
 * @param size The number of data points to be generated.
 * @param divisions The number of groups to divide the range into.
 * @param threshold The fraction of data points which should land in each division.
 * When divisions = 16, a perfect threshold would be 0.0625.
 */
template<pUtils::unsignedIntegral T>
void randomIntegralUniformityTest(int size = 1'000'000, int divisions = 16, double threshold = 0.0615) {
	bool result = true;
	int counters[divisions];
	memset(counters, 0, sizeof(counters));
	T step;
	if constexpr (pUtils::longIntegral<T>) {
		step = ULL_MAX / divisions + 1;
	} else {
		step = UI_MAX / divisions + 1;
	}

	T dataPoint;
	for (int i = 0; i < size; i++) {
		dataPoint = Random::rand<T>();
		int counter = dataPoint / step;
		counters[counter]++;
	}

	for (int i = 0; i < divisions; i++) {
		if (counters[i] < size * threshold) {
			result = false;
		}
	}

	assert(result);
}

/**
 * Checks if the distribution of randomly generated floating point numbers is uniform.
 * @param size The number of data points to be generated.
 * @param divisions The number of groups to divide the range into.
 * @param threshold The fraction of data points which should land in each division.
 * When divisions = 16, a perfect threshold would be 0.0625.
 */
template<pUtils::floatingPoint T>
void randomFloatingPointUniformityTest(int size = 1'000'000, int divisions = 16, double threshold = 0.0615) {
	bool result = true;
	int counters[divisions];
	memset(counters, 0, sizeof(counters));
	ldbl step = 1.0L / divisions;

	T dataPoint;
	for (int i = 0; i < size; i++) {
		dataPoint = Random::rand<T>();
		int counter = dataPoint / step;
		counters[counter]++;
	}

	for (int i = 0; i < divisions; i++) {
		if (counters[i] < size * threshold) {
			result = false;
		}
	}

	assert(result);
}

const std::string garbageWhitespace = "  \t\t\n\n\v\v\n\r\f  \n \t\t ";

/**
 * Returns a list of strings for parsing tests.
 * @return List of strings.
 */
const std::vector<std::string> & getStringsToParse() {
	static std::vector<std::string> result {
		"0",
		"1",
		"-1",
		"2147483647",
		"2147483648",
		"-2147483648",
		"-2147483649",
		"4294967295",
		"4294967296",
		"9223372036854775807",
		"9223372036854775808",
		"-9223372036854775808",
		"-9223372036854775809",
		"18446744073709551615",
		"18446744073709551616",
		"0.1",
		"-0.1",
		"123abc",
		"1.0ghoul",
		"-"
	};

	return result;
}

/**
 * Returns when a parsing error is expected when parsing to type T.
 * @tparam T The type to parse to.
 * @return List of flags when parsing error is expected.
 */
template<packageType T>
std::vector<bool> getExpectedParsingErrors() {
	std::vector<bool> result(20, false);
	result[17] = result[18] = result [19] = true;
	if constexpr (pUtils::integral<T>) {
		result[15] = result[16] = true;
	}
	return result;
}

/**
 * Returns when an overflow error is expected when parsing to type T, given that no parsing error occurs.
 * @tparam T The type to parse to.
 * @return List of flags when overflow error is expected.
 */
template<packageType T>
std::vector<bool> getExpectedOverflowErrors() {
	if constexpr (pUtils::integral<T>) {
		if constexpr (std::same_as<T, ull>) {
			return {false, false, true, false, false, true, true, false, false, false, false, true, true, false, true, false, false, false, false, false};
		} else if constexpr (std::same_as<T, sll>) {
			return {false, false, false, false, false, false, false, false, false, false, true, false, true, true, true, false, false, false, false, false};
		} else if constexpr (std::same_as<T, ui>) {
			return {false, false, true, false, false, true, true, false, true, true, true, true, true, true, true, false, false, false, false, false};
		} else {
			return {false, false, false, false, true, false, true, true, true, true, true, true, true, true, true, false, false, false, false, false};
		}
	} else {
		return std::vector<bool>(20, false);
	}
}

/**
 * Returns the expected values of the results of parsing to to type T, given that no parsing or overflow errors occur.
 * @tparam T The type to parse to.
 * @return List of expected values.
 */
template<packageType T>
std::vector<T> getExpectedValues() {
	const std::vector<std::string> & stringsToParse = getStringsToParse();
	std::vector<T> result {
		T(0), T(1), T(-1),
		T(2147483647), T(2147483648), T(-2147483648), T(-2147483649), T(4294967295), T(4294967296),
		T(9223372036854775807), T(9223372036854775808ULL), -T(9223372036854775808ULL),
		-T(9223372036854775809ULL), T(18446744073709551615ULL), T(18446744073709551616.L),
		T(0.1L), T(-0.1L), T(0), T(0), T(0)
	};

	return result;
}

ldbl margin = 1e-10;

/**
 * Performs reader class parsing tests.
 * @tparam T The type to parse to.
 */
template<packageType T>
void parsingTest() {
	// Prepare result, filename, file, and vectors with information, and reader.
	char filename [10] = "test.txt";
	std::ofstream file(filename);
	const std::vector<std::string> & stringsToParse = getStringsToParse();
	const std::vector<bool> expectedParsingErrors = getExpectedParsingErrors<T>();
	const std::vector<bool> expectedOverflowErrors = getExpectedOverflowErrors<T>();
	const std::vector<T> expectedValues = getExpectedValues<T>();
	Reader reader((std::string(filename)));

	assert(stringsToParse.size() == expectedParsingErrors.size());
	assert(stringsToParse.size() == expectedOverflowErrors.size());
	assert(stringsToParse.size() == expectedValues.size());

	for (const std::string& str : stringsToParse) {
		file << garbageWhitespace << str;
	}
	file << garbageWhitespace;
	file.close();

	// Perform the test.
	T value;
	for (size_t i = 0; i < stringsToParse.size(); i++) {
		assert(!reader.eof());
		try {
			value = reader.read<T>();
		} catch (ParsingException &) {
			assert(expectedParsingErrors[i]);
			continue;
		} catch (OverflowException &) {
			assert(expectedOverflowErrors[i]);
			continue;
		}
		assert(!expectedParsingErrors[i]);
		assert(!expectedOverflowErrors[i]);
		if constexpr (pUtils::integral<T>) {
			assert(value == expectedValues[i]);
		} else {
			ldbl difference = value - expectedValues[i];
			ldbl ratio = value / expectedValues[i];
			assert((difference > -margin && difference < margin) || (ratio > 1 - margin && ratio < 1 + margin));
		}
	}

	bool caughtEOF = false;
	assert(reader.eof());
	try {
		reader.read<T>();
	} catch (EOFException &) {
		caughtEOF = true;
	}
	assert(caughtEOF);

	// Remove the file.
	remove(filename);
}

int main () {
	// pUtils::Random tests
	randomIntegralUniformityTest<ull>();
	randomIntegralUniformityTest<ui>();
	randomFloatingPointUniformityTest<ldbl>();
	randomFloatingPointUniformityTest<dbl>();

	// pUtils::Reader::read tests
	parsingTest<ull>();
	parsingTest<sll>();
	parsingTest<ui>();
	parsingTest<si>();
	parsingTest<ldbl>();
	parsingTest<dbl>();

	std::cout << "OK!\n";
}
