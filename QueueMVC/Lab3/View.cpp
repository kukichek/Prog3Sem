#include "View.h"
#define STR_SIZE 80

void View::Push(const std::string &s) {
	if (curEditState.size() != 0) {
		curEditState += "\r\n";
	}
	curEditState += s;
	SetWindowTextA(hEditOut_, curEditState.c_str());
}

void View::Pop() {
	int tempPos = curEditState.find('\n');
	if (tempPos != std::string::npos) {
		curEditState.erase(0, curEditState.find('\n') + 1);
	}
	else {
		curEditState.clear();
	}
	SetWindowTextA(hEditOut_, curEditState.c_str());
}

void View::Clear() {
	curEditState.clear();
	SetWindowTextA(hEditOut_, curEditState.c_str());
}

void View::UpdateEdit(const std::string &s) {
	curEditState = s;
	SetWindowTextA(hEditOut_, curEditState.c_str());
}

void View::BoundaryElement(const std::string &s) {
	SetWindowTextA(hEditOut_, s.c_str());
}

void View::DisplayError(const std::string &errorMsg) {
	MessageBox(hErrorOutWnd_, errorMsg.c_str(), NULL, MB_OKCANCEL);
}

void View::SetHEditOut(HWND hEditOut) {
	hEditOut_ = hEditOut;
} 

void View::SetHErrorOutWnd(HWND hWnd) {
	hErrorOutWnd_ = hWnd;
}