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
		due = sc.next();
		
		TodoItem t = new TodoItem(title, desc, cate, due);
		list.addItem(t);
		System.out.println("새로운 항목이 추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 제거]\n"
				+ "항목 번호 입력 > ");
		int num = sc.nextInt();
		num = num - 1;
		String dec;
		
		TodoItem item = l.getList().get(num);
		System.out.println((num+1) + ". " + item.toString());
		System.out.println("해당 항목을 삭제하시겠습니까? (y/n) > ");
		dec = sc.next();
		
		if (dec.charAt(0) == 'y') {
			l.deleteItem(item);
			System.out.println("항목이 제거되었습니다.");
		}
		
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 수정]\n"
				+ "항목 번호 입력 > ");
		int num = sc.nextInt();
		num = num - 1;
		
		TodoItem item = l.getList().get(num);
		System.out.println((num+1) + ". " + item.toString());

		System.out.println("새로운 제목 입력 > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("중복된 제목의 항목이 있습니다.");
			return;
		}
		
		System.out.println("새로운 카테고리 입력 > ");
		String new_category = sc.next().trim();
		
		sc.nextLine();
		System.out.println("새로운 내용 입력 > ");
		String new_description = sc.nextLine().trim();
		
		sc.nextLine();
		System.out.println("새로운 마감일자 입력 (년/월/일) > ");
		String new_due_date = sc.nextLine().trim();

		l.deleteItem(item);
		TodoItem t = new TodoItem(new_title, new_description, new_category ,new_due_date);
		l.addItem(t);
		System.out.println("항목이 수정되었습니다.");
	}
	
	public static void findItem(TodoList l, String word) {
		int num = 1, count = 0;
		for (TodoItem item : l.getList()) {
			if (item.getTitle().contains(word) || item.getDesc().contains(word)) {
				System.out.println(num + ". " + item.toString());
				count++;
			}
			num++;
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	
	public static void findCategory(TodoList l, String word) {
		int num = 1, count = 0;
		for (TodoItem item : l.getList()) {
			if (item.getCategory().contains(word)) {
				System.out.println(num + ". " + item.toString());
				count++;
			}
			num++;
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	
	public static void listCate(TodoList l) {
		Set<String> set = new HashSet<String>();
		
		for (TodoItem item : l.getList()) {
			set.add(item.getCategory());
		}
		
		System.out.println(set);
		System.out.println("총 " + set.size() + "개의 카테고리가 존재합니다.");
	}

	public static void listAll(TodoList l) {
		System.out.println("[전체 목록, 총 " + l.getList().size() + "개]");
		int count = 1;
		for (TodoItem item : l.getList()) {
			System.out.println(count + ". " + item.toString());
			count++;
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
