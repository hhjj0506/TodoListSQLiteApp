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
		
		String title, desc, cate, due;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 추가]\n"
				+ "제목 입력 > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("중복된 제목의 항목이 있습니다.");
			return;
		}
		
		System.out.println("카테고리 입력 > ");
		cate = sc.next();
		
		sc.nextLine();
		System.out.println("내용 입력 > ");
		desc = sc.nextLine().trim();
		
		System.out.println("마감일자 입력 (년/월/일) > ");
		due = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc, cate, due);
		if (list.addItem(t) > 0) {
			System.out.println("새로운 항목이 추가되었습니다.");
		}
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 제거]\n"
				+ "항목 번호 입력 > ");
		int index = sc.nextInt();

		if (l.deleteItem(index) > 0) {
			System.out.println("항목이 제거되었습니다.");
		}
	}
	
	public static void percItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 진행도 입력]\n"
				+ "항목 번호 입력 > ");
		int index =  sc.nextInt();
		
		System.out.println("진행도 입력 (0-100) > ");
		int perc =  sc.nextInt();
		
		l.percItem(index, perc);
	}

	public static void setPrior(TodoList l) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 중요도 입력]\n"
				+ "항목 번호 입력 > ");
		int index = sc.nextInt();
		
		System.out.println("중요도 입력 (1. Critical, 2. High, 3. Medium, 4. Low) > ");
		int prior = sc.nextInt();
		
		l.setPrior(index, prior);
	}

	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 수정]\n"
				+ "항목 번호 입력 > ");
		int num = sc.nextInt();

		System.out.println("새로운 제목 입력 > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("중복된 제목의 항목이 있습니다.");
			return;
		}
		
		System.out.println("새로운 카테고리 입력 > ");
		String new_category = sc.next();
		
		sc.nextLine();
		System.out.println("새로운 내용 입력 > ");
		String new_description = sc.nextLine().trim();
		
		//sc.nextLine();
		System.out.println("새로운 마감일자 입력 (년/월/일) > ");
		String new_due_date = sc.nextLine().trim();

		TodoItem t = new TodoItem(new_title, new_description, new_category ,new_due_date);
		t.setId(num);
		if (l.editItem(t) > 0) {
			System.out.println("항목이 수정되었습니다.");
		}
	}
	
	public static void completeItem(TodoList l, int num) {
		l.completeItem(num);
	}
	
	public static void uncompleteItem(TodoList l, int num) {
		l.uncompleteItem(num);
	}
	
	public static void findList(TodoList l, String word) {
		int count = 0;
		for (TodoItem item : l.getList(word)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	
	public static void findCateList(TodoList l, String word) {
		int count = 0;
		for (TodoItem item : l.getListCategory(word)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	
	public static void findPerc(TodoList l, int perc) {
		int count = 0;
		for (TodoItem item : l.getPerc(perc)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	
	public static void listCateAll(TodoList l) {
		int count = 0;
		
		for (String item : l.getCategories()) {
			System.out.println(item + " ");
			count++;
		}
		
		System.out.printf("총 %d개의 카테고리가 존재합니다.", count);
	}
	
	public static void listPriorAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getPrior()) {
			System.out.println(item.toString());
		}
	}

	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l, int num) {
		int count = 0;
		
		for (TodoItem item : l.getList(num)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
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
			
			String line, title, desc, date, due, cate;
			int count = 0;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				cate = st.nextToken();
				title = st.nextToken();
				desc = st.nextToken();
				due = st.nextToken();
				date = st.nextToken();
				
				TodoItem t = new TodoItem(title, desc, cate, due);
				t.setCurrent_date(date);
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
