#pragma once

#include <cstdint>
#include <cfloat>
#include <random>
#include <fstream>
#include <cerrno>
#include <stdexcept>

namespace pUtils{
	
	using si = int32_t;
	using ui = uint32_t;
	using sll = int64_t;
	using ull = uint64_t;
	using dbl = double;
	using ldbl = long double;
	
	const si SI_MIN = -2147483648;
	
	const si SI_MAX = 2147483647;
	
	const ui UI_MIN = 0;
	
	const ui UI_MAX = 4294967295;
	
	const sll SLL_MIN = -9223372036854775807LL - 1; // This is done, so we are sure there is no stupid type conversion to unsigned and stuff
	
	const sll SLL_MAX = 9223372036854775807LL;
	
	const ull ULL_MIN = 0;
	
	const ull ULL_MAX = 18446744073709551615ULL;
	// Floating-point limits defined in <cfloat>.
	
	template<typename T>
	concept signedIntegral = std::same_as<T, sll> || std::same_as<T, si>;
	
	template<typename T>
	concept unsignedIntegral = std::same_as<T, ull> || std::same_as<T, ui>;
	
	template<typename T>
	concept integral = signedIntegral<T> || unsignedIntegral<T>;
	
	template<typename T>
	concept mediumIntegral = std::same_as<T, ui> || std::same_as<T, si>;
	
	template<typename T>
	concept longIntegral = std::same_as<T, ull> || std::same_as<T, sll>;
	
	template<typename T>
	concept floatingPoint = std::same_as<T, ldbl> || std::same_as<T, dbl>;
	
	template<typename T>
	concept packageType = integral<T> || floatingPoint<T>;

	/**
	 * Returns the maximum value of an integral type.
	 * @tparam T The integral type.
	 * @return The type's maximum value.
	 */
	template<integral T>
	[[nodiscard]]
	T integralMaximum(){
		if constexpr (std::same_as<T, ull>){
			return ULL_MAX;
		}
		else if constexpr (std::same_as<T, sll>){
			return SLL_MAX;
		}
		else if constexpr (std::same_as<T, ui>){
			return UI_MAX;
		}
		else{
			return SI_MAX;
		}
	}

	/**
	 * Returns the minimum value of an integral type.
	 * @tparam T The integral type.
	 * @return The type's minimum value.
	 */
	template<integral T>
	[[nodiscard]]
	T integralMinimum(){
		if constexpr (std::same_as<T, ull>){
			return ULL_MIN;
		}
		else if constexpr (std::same_as<T, sll>){
			return SLL_MIN;
		}
		else if constexpr (std::same_as<T, ui>){
			return UI_MIN;
		}
		else{
			return SI_MIN;
		}
	}

	/**
	 * Returns the negated minimum value of a medium integral type.
	 * @tparam T The integral type.
	 * @return The type's negated minimum value.
	 */
	template<mediumIntegral T>
	[[nodiscard]]
	ui integralNegatedMinimum(){
		if constexpr (std::same_as<T, ui>){
			return UI_MIN;
		}
		else{
			return ui(SI_MAX) + 1;
		}
	}

	/**
	 * Returns the negated minimum value of a long integral type.
	 * @tparam T The integral type.
	 * @return The type's negated minimum value.
	 */
	template<longIntegral T>
	[[nodiscard]]
	ull integralNegatedMinimum(){
		if constexpr (std::same_as<T, ull>){
			return ULL_MIN;
		}
		else{
			return ull(SLL_MAX) + 1;
		}
	}

	/**
	 * Returns the minimum value of a floating point type.
	 * @tparam T A floating point type.
	 * @return The minimum value.
	 */
	template<floatingPoint T>
	[[nodiscard]]
	T floatingPointMinimum(){
		if constexpr (std::same_as<T, ldbl>){
			return LDBL_MIN;
		}
		else{
			return DBL_MIN;
		}
	}

	/**
	 * Returns the maximum value of a floating point type.
	 * @tparam T A floating point type.
	 * @return The maximum value.
	 */
	template<floatingPoint T>
	[[nodiscard]]
	T floatingPointMaximum(){
		if constexpr (std::same_as<T, ldbl>){
			return LDBL_MAX;
		}
		else{
			return DBL_MAX;
		}
	}

	/**
	 * Returns the minimum value of a given type.
	 * @tparam T The given type.
	 * @return The minimum value.
	 */
	template<packageType T>
	[[nodiscard]]
	T minimumValue(){
		if constexpr (integral<T>){
			return integralMinimum<T>();
		}
		else{
			return floatingPointMinimum<T>();
		}
	}

	/**
	 * Returns the maximum value of a given type.
	 * @tparam T The given type.
	 * @return The maximum value.
	 */
	template<packageType T>
	[[nodiscard]]
	T maximumValue(){
		if constexpr (integral<T>){
			return integralMaximum<T>();
		}
		else{
			return floatingPointMaximum<T>();
		}
	}
	
	class Random{
		private:
			// Mersenne Twister random number generator, generates numbers in range [0, 2^64).
			static std::mt19937_64 rng;
		
		public:
			Random() = delete;
			
			Random(Random& r) = delete;
			
			template<unsignedIntegral T>
			[[nodiscard]]
			static T rand(){
				// Random integer in the range of the returned type.
				if constexpr (std::same_as<T, ull>){
					return rng();
				}
				else{
					ull result = rng();
					result &= (ull)UI_MAX;
					return (ui)result;
				}
			}
			
			template<floatingPoint T>
			[[nodiscard]]
			static T rand(){
				// Random real number in the range [0, 1).
				ldbl result = rng();
				result /= (1LL << 32);
				return result / (1LL << 32);
			}
	};

	// Initialize the private static member, lest there will be a linker error.
	std::mt19937_64 Random::rng;
	
	class EOFException : std::exception{
		public:
			[[nodiscard]]
			const char* what() const noexcept override{
				return "File reached EOF and tried to be readToBuffer from.";
			}
	};
	
	class ParsingException : std::exception{
		public:
			[[nodiscard]]
			const char* what() const noexcept override{
				return "Cannot convert string to expected type.";
			}
	};
	
	class OverflowException : std::exception{
		public:
			[[nodiscard]]
			const char* what() const noexcept override{
				return "Value represented by string is not in range of type.";
			}
	};
	
	class OutOfRangeException : std::exception{
		public:
			[[nodiscard]]
			const char* what() const noexcept override{
				return "Parsed value is not in expected range.";
			}
	};
	
	class Reader{
		private:
			std::ifstream file;
			std::string buffer;
			
			void skipWhitespace(){
				while(!file.eof() && std::isspace(file.peek())){
					file.get();
				}
			}
			
			void readToBuffer(){
				skipWhitespace();
				if(file.eof()){
					throw EOFException();
				}
				file >> buffer;
			}
			
			static bool isDigit(char c){
				return (c >= '0' && c <= '9');
			}
		
		public:
			// Possible optimization: array with values of ULL_MAX - digit / 10 for all digit values.
			/**
			 * Parses the string under the given pointer into an unsigned long long.
			 * Sets errno to 0 on success.
			 * Sets errno to 1 on unexpected character or lack of characters (parsing error).
			 * Sets errno to 2 on overflow.
			 * @param str Pointer to the string to be parsed.
			 * @param len Length of the string to be parsed.
			 * @return The parsed value, always 0 on failure.
			 */
			static ull parseULL(const char* str, const size_t len){
				errno = 0;
				if(len == 0){
					throw ParsingException();
				}
				
				ull result = 0;
				
				for(size_t index = 0; index < len; index++){
					if(!isDigit(str[index])){
						throw ParsingException();
					}
					int digit = str[index] - '0';
					// check that result * 10 + digit <= ULL_MAX
					if((ULL_MAX - digit) / 10 < result){
						throw OverflowException();
					}
					result = result * 10 + digit;
				}
				
				return result;
			}
			
			/**
			 * Parses the string under the given pointer into a long double.
			 * Sets errno to 0 on success.
			 * Sets errno to 1 on unexpected character or lack of characters (parsing error).
			 * @param str Pointer to the string to be parsed.
			 * @param len Length of the string to be parsed.
			 * @return The parsed value, always 0 on failure.
			 */
			static ldbl parseLDBL(const char* str, const size_t len){
				errno = 0;
				if(len == 0){
					throw ParsingException();
				}
				
				ldbl result = 0;
				ldbl fraction = 0.1;
				
				size_t index = 0;
				for(; index < len; index++){
					if(str[index] == '.'){
						index++;
						break;
					}
					if(!isDigit(str[index])){
						throw ParsingException();
					}
					int digit = str[index] - '0';
					result = result * 10 + digit;
				}
				
				for(; index < len; index++){
					if(!isDigit(str[index])){
						throw ParsingException();
					}
					int digit = str[index] - '0';
					result = result + digit * fraction;
					fraction /= 10;
				}
				
				return result;
			}
			
			explicit Reader(const std::string& filename) : file(filename){}
			
			bool eof(){
				skipWhitespace();
				return file.eof();
			}
			
			template<integral T>
			T read(){
				readToBuffer();
				
				if constexpr (signedIntegral<T>){
					// reading a signed integer
					if(buffer[0] == '-'){
						// negative integer
						ull result = parseULL(buffer.data() + 1, buffer.size() - 1);
						ull negatedMinimum = integralNegatedMinimum<T>();
						if(result > negatedMinimum){
							throw OverflowException();
						}
						if(result == negatedMinimum){
							return integralMinimum<T>();
						}
						return -T(result);
					}
					else{
						// positive integer
						ull result = parseULL(buffer.data(), buffer.size());
						if(result > integralMaximum<T>()){
							throw OverflowException();
						}
						return result;
					}
				}
				else{
					// reading an unsigned integral
					ull result;
					if(buffer[0] == '-'){
						// we expect an unsigned integer, but we check try to parse before we check overflow.
						parseULL(buffer.data() + 1, buffer.size() - 1);
						throw OverflowException();
					}
					else{
						result = parseULL(buffer.data(), buffer.size());
					}
					if(result > integralMaximum<T>()){
						throw OverflowException();
					}
					return result;
				}
			}
			
			template<floatingPoint T>
			T read(){
				readToBuffer();
				bool sign = (buffer[0] == '-');
				T result;
				if(sign){
					result = parseLDBL(buffer.data() + 1, buffer.size() - 1);
				}
				else{
					result = parseLDBL(buffer.data(), buffer.size());
				}
				if(sign){
					return -result;
				}
				else{
					return result;
				}
			}
	};
	
} // namespace pUtils
