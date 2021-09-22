package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 추가]\n"
				+ "제목 입력 > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("중복된 제목의 항목이 있습니다.");
			return;
		}
		
		sc.nextLine();
		System.out.println("내용 입력 > ");
		desc = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("새로운 항목이 추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 제거]\n"
				+ "제목 입력 > ");
		String title = sc.next();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("항목이 제거되었습니다.");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 수정]\n"
				+ "제목 입력 > ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("일치하는 항목이 없습니다.");
			return;
		}

		System.out.println("새로운 제목 입력 > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("중복된 제목의 항목이 있습니다.");
			return;
		}
		
		sc.nextLine();
		System.out.println("새로운 내용 입력 > ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("항목이 수정되었습니다.");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[전체 목록]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter(filename);
			
			for (TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			
			System.out.println("항목들이 저장되었습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			
			String line, title, desc, date;
			int count = 0;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				title = st.nextToken();
				desc = st.nextToken();
				date = st.nextToken();
				
				TodoItem t = new TodoItem(title, desc, date);
				l.addItem(t);
				count++;
			}
			br.close();
			System.out.println(count + "개의 항목들을 불러왔습니다.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
