cmake_minimum_required(VERSION 3.21)
project(packageUtilsTest)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_FLAGS "-Wall -Wextra -O3 --coverage")

set(SOURCE_FILES packageUtilsTest.cpp packageUtils.h)

add_executable(packageUtilsTest ${SOURCE_FILES})
