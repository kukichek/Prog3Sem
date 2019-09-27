#include "Controller.h"

Controller::Controller(View *v) : view_(v) {}

void Controller::Push(const std::string &s) {
	if (s.empty()) return;
	queue_.push(s);
	view_->Push(s);
}

void Controller::Pop() {
	if (queue_.isEmpty()) {
		view_->DisplayError("Empty queue");
		return;
	}
	queue_.pop();
	view_->Pop();
}

void Controller::Back() {
	if (queue_.isEmpty()) {
		return;
	}
	view_->BoundaryElement(queue_.back());
}

void Controller::Front() {
	if (queue_.isEmpty()) {
		return;
	}
	view_->BoundaryElement(queue_.front());
}

void Controller::Clear() {
	if (queue_.isEmpty()) {
		view_->DisplayError("Empty queue");
		return;
	}
	queue_.clear();
	view_->Clear();
}

void Controller::Swap(Controller &c) {
	queue_.swap(c.queue_);
	view_->UpdateEdit(queue_.getModelString());
}

void Controller::Out() {
	view_->UpdateEdit(queue_.getModelString());
}