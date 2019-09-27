#pragma once

class MyException {
public:
	MyException(const std::string _error, const std::string _method);
	std::string getErr();
	std::string getMethod();
private:
	const std::string error, method;
};