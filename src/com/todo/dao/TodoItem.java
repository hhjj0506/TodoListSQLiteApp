package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
    private String title;
    private String desc;
    private String current_date;
    private String category;
    private String due_date;
    private int id;
    private int is_completed;
    private int percent;
    private int priority;

    public TodoItem(String title, String desc, String category, String due_date){
        this.title = title;
        this.desc = desc;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date = f.format(new Date());
        this.category = category;
        this.due_date = due_date;
    }
    
    @Override
	public String toString() {
    	String temp = null;
    	
    	switch (priority) {
    		case 5: temp = "N/A"; break;
    		case 1: temp = "Critical"; break;
    		case 2: temp = "High"; break;
    		case 3: temp = "Medium"; break;
    		case 4: temp = "Low"; break;
    	}
    	
    	if (is_completed == 1) {
    		return id + " [" + category + "] " + title + "[V] - " + desc + " - " + due_date + " - " + current_date + " - " + percent + "% - " + temp;
    	}
		return id + " [" + category + "] " + title + " - " + desc + " - " + due_date + " - " + current_date + " - " + percent + "% - " + temp;
	}
    
    public String toSaveString() {
    	return category + "##" + title + "##" + desc + "##" + due_date + "##" + current_date + "\n";
    }

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIs_completed() {
		return is_completed;
	}

	public void setIs_completed(int is_completed) {
		this.is_completed = is_completed;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}
