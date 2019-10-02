#pragma once
#include <string>

template<typename T> 
std::string Converter(const T& value) {
	return std::to_string(value);
}

template<>
std::string Converter(const std::string& value) {
	return value;
}

template<>
std::string Converter(const char& value) {
	return std::string(&value);
}
