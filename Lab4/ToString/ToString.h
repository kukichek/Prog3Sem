#pragma once
#include <sstream>
#include "Converter.h"

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
	return Converter(spacer, arg) + ToString(spacer, args...);
}