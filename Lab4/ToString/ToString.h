#pragma once
#include <sstream>

template <typename T>
std::string Converter(const char* spacer, T arg)
{
	std::stringstream converter;
	converter << arg << spacer;
	return converter.str();
}

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
	auto func = [&](const char* spacer, Args ... args) {return ToString(spacer, args...); };
	return Converter(spacer, arg) + func(spacer, args...);
}