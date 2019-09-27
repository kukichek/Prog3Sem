#pragma once
#include<string>

class AbstractController {
public:
	virtual void Push(const std::string &_s) = 0;
	virtual void Pop() = 0;

	virtual void Back() = 0;
	virtual void Front() = 0;

	virtual void Clear() = 0;
	virtual void Swap(AbstractController *c) = 0;
protected:
	AbstractView *view_;
	Queue queue_{}; // инициализация значением по умолчанию
};