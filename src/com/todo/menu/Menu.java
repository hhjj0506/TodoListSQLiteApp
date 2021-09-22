package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("--- TODO List 명령어 사용법 ---");
        System.out.println("add - 항목을 추가합니다.");
        System.out.println("del - 항목을 삭제합니다.");
        System.out.println("edit - 항목을 수정합니다.");
        System.out.println("ls - 저장된 모든 항목들을 보입니다.");
        System.out.println("ls_name_asc - 저장된 모든 항목들을 제목순으로 정렬하여 보입니다.");
        System.out.println("ls_name_desc - 저장된 모든 항목들을 제목역순으로 정렬하여 보입니다.");
        System.out.println("ls_date - 저장된 모든 항목들을 날짜순으로 정렬하여 보입니다.");
        System.out.println("exit - 프로그램을 종료합니다.");
    }

	public static void prompt() {
		System.out.println("\n명령어 입력 > ");
	}
}
