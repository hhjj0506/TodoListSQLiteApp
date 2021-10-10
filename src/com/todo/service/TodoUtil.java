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
		
		System.out.println("[�׸� �߰�]\n"
				+ "���� �Է� > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("�ߺ��� ������ �׸��� �ֽ��ϴ�.");
			return;
		}
		
		System.out.println("ī�װ� �Է� > ");
		cate = sc.next();
		
		sc.nextLine();
		System.out.println("���� �Է� > ");
		desc = sc.nextLine().trim();
		
		System.out.println("�������� �Է� (��/��/��) > ");
		due = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc, cate, due);
		if (list.addItem(t) > 0) {
			System.out.println("���ο� �׸��� �߰��Ǿ����ϴ�.");
		}
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[�׸� ����]\n"
				+ "�׸� ��ȣ �Է� > ");
		int index = sc.nextInt();

		if (l.deleteItem(index) > 0) {
			System.out.println("�׸��� ���ŵǾ����ϴ�.");
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[�׸� ����]\n"
				+ "�׸� ��ȣ �Է� > ");
		int num = sc.nextInt();

		System.out.println("���ο� ���� �Է� > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("�ߺ��� ������ �׸��� �ֽ��ϴ�.");
			return;
		}
		
		System.out.println("���ο� ī�װ� �Է� > ");
		String new_category = sc.next();
		
		sc.nextLine();
		System.out.println("���ο� ���� �Է� > ");
		String new_description = sc.nextLine().trim();
		
		sc.nextLine();
		System.out.println("���ο� �������� �Է� (��/��/��) > ");
		String new_due_date = sc.nextLine().trim();

		TodoItem t = new TodoItem(new_title, new_description, new_category ,new_due_date);
		t.setId(num);
		if (l.editItem(t) > 0) {
			System.out.println("�׸��� �����Ǿ����ϴ�.");
		}
	}
	
	public static void completeItem(TodoList l, int num) {
		l.completeItem(num);
	}
	
	public static void findList(TodoList l, String word) {
		int count = 0;
		for (TodoItem item : l.getList(word)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("�� " + count + "���� �׸��� ã�ҽ��ϴ�.");
	}
	
	public static void findCateList(TodoList l, String word) {
		int count = 0;
		for (TodoItem item : l.getListCategory(word)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("�� " + count + "���� �׸��� ã�ҽ��ϴ�.");
	}
	
	public static void listCateAll(TodoList l) {
		int count = 0;
		
		for (String item : l.getCategories()) {
			System.out.println(item + " ");
			count++;
		}
		
		System.out.printf("�� %d���� ī�װ��� �����մϴ�.", count);
	}

	public static void listAll(TodoList l) {
		System.out.printf("[��ü ���, �� %d��]\n", l.getCount());
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
		System.out.println("�� " + count + "���� �׸��� ã�ҽ��ϴ�.");
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[��ü ���, �� %d��]\n", l.getCount());
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
			
			System.out.println("�׸���� ����Ǿ����ϴ�.");
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
			System.out.println(count + "���� �׸���� �ҷ��Խ��ϴ�.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
