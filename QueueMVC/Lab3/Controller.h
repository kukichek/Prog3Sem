#pragma once
#include"View.h"
#include"QueueDinArrIterator.h"

class Controller {
public:
	explicit Controller(View *v);

	void Push(const std::string &s);
	void Pop();

	void Back();
	void Front();

	void Clear();
	void Swap(Controller &c);
	void Out();

private:
	View *view_;
	Queue queue_{}; // инициализация значением по умолчанию
};