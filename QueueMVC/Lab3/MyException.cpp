#include "MyException.h"

MyException::MyException(const std::string _error, const std::string _method) : error(_error), method(_method) {}

std::string MyException::getErr() {
	return error;
}

std::string MyException::getMethod() {
	return method;
}