#pragma once
#include <sstream>

template <typename T>
std::string ToString(const char* spacer, T arg)
{
	std::stringstream converter;
	converter << arg;
	return converter.str();
}

template <typename T, typename ... Args>
std::string ToString(const char* spacer, T arg, Args ... args)
{
	auto convert = [](const char* spacer, T arg) { std::stringstream converter;
										 converter << arg << spacer; 
										 return converter.str(); };
	return convert(spacer, arg) + ToString(spacer, args...);
}