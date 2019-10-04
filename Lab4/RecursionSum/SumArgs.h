#pragma once

template <typename T>
auto sumArgs(T elem)
{
	return elem;
}

template<typename T, typename ... Args>
auto sumArgs(T elem, Args ... args)
{
	auto func = [](Args... args) {return sumArgs(args...); };
	return elem + func(args...);
}