#pragma once

//template<typename... TArgs>
//auto sumArgs(TArgs... args)
//{
//	return (args + ...);
//}

template <typename T>
auto sumArgs(T elem)
{
	return elem;
}

template<typename T, typename ... Args>
auto sumArgs(T elem, Args ... args)
{
	return elem + sumArgs(args...);
}