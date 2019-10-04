#include "View.h"
#define STR_SIZE 80

void View::UpdateEdit(const std::string &s) {
	curEditState = s;
	SetWindowTextA(hEditOut_, curEditState.c_str());
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