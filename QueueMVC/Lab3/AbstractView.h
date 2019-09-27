#pragma once
#include<string>

class AbstractView {
public:
	virtual void Push(const std::string &s) = 0;
	virtual void Pop() = 0;
	virtual void Clear() = 0;
	virtual void UpdatEdit(const std::string &s) = 0;
	virtual void DisplayError(const std::string &s) = 0;
};