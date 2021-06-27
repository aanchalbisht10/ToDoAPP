package com.dituniversity.todo.repo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.dituniversity.todo.dto.ToDoDTO;

public interface IToDoRepo {
	public boolean addTask(ArrayList<ToDoDTO> tasks) throws IOException;
	public ArrayList<ToDoDTO> printTasks() throws FileNotFoundException, IOException, ClassNotFoundException; // arraylist type is tododto which is object

}
