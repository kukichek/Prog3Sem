// Lab3.cpp : Определяет точку входа для приложения.
//

#include "Lab3.h"
#include "View.h"
#include "Controller.h"
#include "QueueDinArrIterator.h"
#include "MyException.h"

// Глобальные переменные:
#define MAX_LOADSTRING 100
#define STR_SIZE 80
#define ID_PUSH 1011
#define ID_POP 1012
#define ID_FRONT 1013
#define ID_BACK 1014
#define ID_CLEAR 1015
#define ID_SWAP 1016
#define ID_EDITIN 1017
#define ID_EDITACTIVEOUT 1018
#define ID_EDITINACTIVEOUT 1019

HINSTANCE hInst;                                // текущий экземпляр
WCHAR szTitle[MAX_LOADSTRING];                  // Текст строки заголовка
WCHAR szWindowClass[MAX_LOADSTRING];            // имя класса главного окна

View* activeView = new View();
View* inactiveView = new View();
Controller activeController(activeView), inactiveController(inactiveView);


// Отправить объявления функций, включенных в этот модуль кода:
ATOM                MyRegisterClass(HINSTANCE hInstance);
BOOL                InitInstance(HINSTANCE, int);
LRESULT CALLBACK    WndProc(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK    About(HWND, UINT, WPARAM, LPARAM);

int APIENTRY wWinMain(_In_ HINSTANCE hInstance,
                     _In_opt_ HINSTANCE hPrevInstance,
                     _In_ LPWSTR    lpCmdLine,
                     _In_ int       nCmdShow)
{
    UNREFERENCED_PARAMETER(hPrevInstance);
    UNREFERENCED_PARAMETER(lpCmdLine);

    // TODO: Разместите код здесь.

    // Инициализация глобальных строк
    LoadStringW(hInstance, IDS_APP_TITLE, szTitle, MAX_LOADSTRING);
    LoadStringW(hInstance, IDC_LAB3, szWindowClass, MAX_LOADSTRING);
    MyRegisterClass(hInstance);

    // Выполнить инициализацию приложения:
    if (!InitInstance (hInstance, nCmdShow))
    {
        return FALSE;
    }

    HACCEL hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_LAB3));

    MSG msg;

    // Цикл основного сообщения:
    while (GetMessage(&msg, nullptr, 0, 0))
    {
        if (!TranslateAccelerator(msg.hwnd, hAccelTable, &msg))
        {
            TranslateMessage(&msg);
            DispatchMessage(&msg);
        }
    }

    return (int) msg.wParam;
}



//
//  ФУНКЦИЯ: MyRegisterClass()
//
//  ЦЕЛЬ: Регистрирует класс окна.
//
ATOM MyRegisterClass(HINSTANCE hInstance)
{
    WNDCLASSEXW wcex;

    wcex.cbSize = sizeof(WNDCLASSEX);

    wcex.style          = CS_HREDRAW | CS_VREDRAW;
    wcex.lpfnWndProc    = WndProc;
    wcex.cbClsExtra     = 0;
    wcex.cbWndExtra     = 0;
    wcex.hInstance      = hInstance;
    wcex.hIcon          = LoadIcon(hInstance, MAKEINTRESOURCE(IDI_LAB3));
    wcex.hCursor        = LoadCursor(nullptr, IDC_ARROW);
    wcex.hbrBackground  = (HBRUSH)(COLOR_WINDOW+1);
    wcex.lpszMenuName   = MAKEINTRESOURCEW(IDC_LAB3);
    wcex.lpszClassName  = szWindowClass;
    wcex.hIconSm        = LoadIcon(wcex.hInstance, MAKEINTRESOURCE(IDI_SMALL));

    return RegisterClassExW(&wcex);
}

//
//   ФУНКЦИЯ: InitInstance(HINSTANCE, int)
//
//   ЦЕЛЬ: Сохраняет маркер экземпляра и создает главное окно
//
//   КОММЕНТАРИИ:
//
//        В этой функции маркер экземпляра сохраняется в глобальной переменной, а также
//        создается и выводится главное окно программы.
//
BOOL InitInstance(HINSTANCE hInstance, int nCmdShow)
{
   hInst = hInstance; // Сохранить маркер экземпляра в глобальной переменной

   HWND hWnd = CreateWindowW(szWindowClass, szTitle, WS_OVERLAPPEDWINDOW,
      CW_USEDEFAULT, 0, 700, 400, nullptr, nullptr, hInstance, nullptr);

   if (!hWnd)
   {
      return FALSE;
   }

   ShowWindow(hWnd, nCmdShow);
   UpdateWindow(hWnd);

   return TRUE;
}

//
//  ФУНКЦИЯ: WndProc(HWND, UINT, WPARAM, LPARAM)
//
//  ЦЕЛЬ: Обрабатывает сообщения в главном окне.
//
//  WM_COMMAND  - обработать меню приложения
//  WM_PAINT    - Отрисовка главного окна
//  WM_DESTROY  - отправить сообщение о выходе и вернуться
//
//
LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
	static HWND hButtonPush, hButtonPop, hButtonFront, hButtonBack,
		hButtonClear, hButtonSwap, hEditIn, hEditActiveOut, hEditInactiveOut;
	inactiveView->SetHEditOut(hEditInactiveOut);
	activeView->SetHEditOut(hEditActiveOut);
	activeView->SetHErrorOutWnd(hWnd);
	std::string inputData;
	char *cstr = new char[STR_SIZE];
    switch (message)
    {
	case WM_CREATE:
		hEditActiveOut = CreateWindow("Edit", "", WS_VISIBLE | WS_CHILD | WS_BORDER | ES_AUTOVSCROLL | ES_MULTILINE | ES_READONLY, 
			20, 20, 180, 300, hWnd, (HMENU)ID_EDITACTIVEOUT, hInst, NULL);
		hEditIn = CreateWindow("Edit", "", WS_VISIBLE | WS_CHILD | WS_BORDER | ES_RIGHT,
			220, 100, 120, 30, hWnd, (HMENU)ID_EDITIN, hInst, NULL);
		hEditInactiveOut = CreateWindow("Edit", "", WS_VISIBLE | WS_CHILD | WS_BORDER | ES_AUTOVSCROLL | ES_MULTILINE | ES_READONLY,
			480, 20, 180, 300, hWnd, (HMENU)ID_EDITINACTIVEOUT, hInst, NULL);

		hButtonPop = CreateWindow("Button", "Pop", WS_VISIBLE | WS_CHILD | BS_DEFPUSHBUTTON,
			220, 20, 120, 30, hWnd, (HMENU)ID_POP, hInst, NULL);
		hButtonPush = CreateWindow("Button", "Push", WS_VISIBLE | WS_CHILD | BS_DEFPUSHBUTTON,
			220, 60, 120, 30, hWnd, (HMENU)ID_PUSH, hInst, NULL);

		hButtonFront = CreateWindow("Button", "Front", WS_VISIBLE | WS_CHILD | BS_DEFPUSHBUTTON,
			220, 160, 120, 30, hWnd, (HMENU)ID_FRONT, hInst, NULL);
		hButtonBack = CreateWindow("Button", "Back", WS_VISIBLE | WS_CHILD | BS_DEFPUSHBUTTON,
			220, 200, 120, 30, hWnd, (HMENU)ID_BACK, hInst, NULL);

		hButtonClear = CreateWindow("Button", "Clear", WS_VISIBLE | WS_CHILD | BS_DEFPUSHBUTTON,
			220, 250, 120, 30, hWnd, (HMENU)ID_CLEAR, hInst, NULL);
		hButtonSwap = CreateWindow("Button", "Swap", WS_VISIBLE | WS_CHILD | BS_DEFPUSHBUTTON,
			220, 290, 120, 30, hWnd, (HMENU)ID_SWAP, hInst, NULL);

		break;
    case WM_COMMAND:
        {
            int wmId = LOWORD(wParam);
            // Разобрать выбор в меню:
            switch (wmId)
            {
			case ID_PUSH:
				GetWindowTextA(hEditIn, cstr, STR_SIZE);
				inputData.assign(cstr);
				activeController.Push(inputData);
				break;
			case ID_POP:
				activeController.Pop();
				break;
			case ID_FRONT:
				activeController.Front();
				break;
			case ID_BACK:
				activeController.Back();
				break;
			case ID_CLEAR:
				activeController.Clear();
				break;
			case ID_SWAP:
				activeController.Swap(inactiveController);
				inactiveController.Out();
				break;
            case IDM_ABOUT:
                DialogBox(hInst, MAKEINTRESOURCE(IDD_ABOUTBOX), hWnd, About);
                break;
            case IDM_EXIT:
                DestroyWindow(hWnd);
                break;
            default:
                return DefWindowProc(hWnd, message, wParam, lParam);
            }
        }
        break;
    case WM_PAINT:
        {
            PAINTSTRUCT ps;
            HDC hdc = BeginPaint(hWnd, &ps);
            // TODO: Добавьте сюда любой код прорисовки, использующий HDC...
            EndPaint(hWnd, &ps);
        }
        break;
    case WM_DESTROY:
        PostQuitMessage(0);
        break;
    default:
        return DefWindowProc(hWnd, message, wParam, lParam);
    }
    return 0;
}

// Обработчик сообщений для окна "О программе".
INT_PTR CALLBACK About(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
    UNREFERENCED_PARAMETER(lParam);
    switch (message)
    {
    case WM_INITDIALOG:
        return (INT_PTR)TRUE;

    case WM_COMMAND:
        if (LOWORD(wParam) == IDOK || LOWORD(wParam) == IDCANCEL)
        {
            EndDialog(hDlg, LOWORD(wParam));
            return (INT_PTR)TRUE;
        }
        break;
    }
    return (INT_PTR)FALSE;
}
