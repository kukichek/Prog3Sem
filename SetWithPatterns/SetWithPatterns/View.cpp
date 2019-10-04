#include "stdafx.h"
#include "View.h"
#define STR_SIZE 80

void View::UpdateEdit(const std::string &s) {
	curEditState = s;
	SetWindowTextA(hEditOut_, curEditState.c_str());
}

void View::DisplayError(const std::string &errorMsg) {
	wchar_t *wcstring = new wchar_t[STR_SIZE];
	size_t convertedChars = 0;
	mbstowcs_s(&convertedChars, wcstring, STR_SIZE, errorMsg.c_str(), _TRUNCATE);

	MessageBox(hErrorOutWnd_, wcstring, NULL, MB_OKCANCEL);
}

void View::SetHEditOut(HWND hEditOut) {
	hEditOut_ = hEditOut;
} 

void View::SetHErrorOutWnd(HWND hWnd) {
	hErrorOutWnd_ = hWnd;
}