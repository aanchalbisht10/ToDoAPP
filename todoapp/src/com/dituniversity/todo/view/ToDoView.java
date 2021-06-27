/**
 * We used property files so that there is no hard coded text in our classes.
 * 
 * */
package com.dituniversity.todo.view;

import com.dituniversity.todo.repo.*;
import static com.dituniversity.todo.utils.Constants.ADD_TASK;
import static com.dituniversity.todo.utils.Constants.PRINT_TASK;
import static com.dituniversity.todo.utils.Constants.SEARCH_TASK;
import static com.dituniversity.todo.utils.Constants.DELETE_TASK;
import static com.dituniversity.todo.utils.Constants.UPDATE_TASK;
import static com.dituniversity.todo.utils.Constants.EXIT;

import static com.dituniversity.todo.utils.MessageReader.getValue; // getValue() called

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.dituniversity.todo.dto.ToDoDTO;
import com.dituniversity.todo.repo.IToDoRepo;
import com.dituniversity.todo.repo.ToDoRepo;
public class ToDoView {
	private static Scanner scanner = new Scanner(System.in);
	
	private static void addTask() {
		scanner.nextLine();
		System.out.println(getValue("input.taskname"));
		String name= scanner.nextLine();
		System.out.println(getValue("input.taskdesc"));
		String desc=scanner.nextLine();
		
		ToDoDTO todo=new ToDoDTO(name,desc);
		// Object has all entered values now
		String result=getValue("record.notadded");
		
		//IToDoRepo repo= new ToDoRepo();
		
		
			ArrayList<ToDoDTO> tasks=null;
			try {
				
				IToDoRepo repo= ToDoRepo.getInstance();   //Object create Now 2 objects will not be created 
				// Upcasting
				
				try {
					tasks=repo.printTasks();    //old tasks fetch from a file
				} catch (EOFException e) {
					System.out.println("File is Empty and add a new record in empty file");
				}
				if(tasks!=null && tasks.size()>0) {
					tasks.add(todo);   //if present add the new tasks
				}
				else {
					tasks= new ArrayList<>(); //if not create new arraylist and add here
					tasks.add(todo);
				}
				result = repo.addTask(tasks)?getValue("record.added"): getValue("record.notadded");  // since addtask is boolean type so if we didn't use the ternary operator this will show error that boolean can't be converted to string
			}
              catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
	
		System.out.println(result);
		
	}
	
	 private static void printAllTasks(){
		
		 try { 
			IToDoRepo repo= ToDoRepo.getInstance();   //Object will be shared
			ArrayList<ToDoDTO> tasks=  repo.printTasks(); // repo.printTasks() : have all the tasks stored inside it.+ added to the arraylist
			for(ToDoDTO task : tasks)
				System.out.println(task);
		} catch (ClassNotFoundException | IOException e) {
			
			e.printStackTrace();
		}
	 }
	 
		private static void updateTask() {
			IToDoRepo repo=null;
			int flag=0;
			try {
				repo = ToDoRepo.getInstance();
			} catch (IOException e) {
				e.printStackTrace();
			}
			ArrayList<ToDoDTO> tasks=null;
			try {
				tasks = repo.printTasks();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			scanner.nextLine();
			System.out.println("Enter the task name you want to update from the list ");
			String name= scanner.nextLine();
			
			for(ToDoDTO task: tasks) {
				if(name.equalsIgnoreCase(task.getName())){
					System.out.println("Enter the details you want to update in the task name ");
					String updateName=scanner.nextLine();
					System.out.println("Enter the details you want to update in the task desc");
					String updateDesc=scanner.nextLine();
					task.setName(updateName);    // this sets the value only now to write it on the file we used addTask()
					task.setDesc(updateDesc);
					System.out.println("The tasks have been updated");
					try {
						repo.addTask(tasks);  // To add updated values in the file
					} catch (IOException e) {
						e.printStackTrace();
					}
					flag=1;
				}
			}
			if(flag!=1) {
				System.out.println("You are trying to update/access the data which is not present");
			}
		}

		private static void deleteAllTask() {
			
			int flag=0;
			IToDoRepo repo=null;
			try {
				repo = ToDoRepo.getInstance();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			ArrayList<ToDoDTO> tasks=null;
			try {
				tasks = repo.printTasks();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			} 
			// data read plus feed into an arraylist
			scanner.nextLine();
			System.out.println("Enter the task name you want to delete from the list ");
			String delete_name= scanner.nextLine();
			for(ToDoDTO task: tasks) {
				if(delete_name.replaceAll("//s","").equalsIgnoreCase(task.getName().replaceAll("//s",""))) {
				//	tasks.remove(task.getName());    // Remove syntax: arrayname.remove() :-This can be used here because we are performing inside the arraylist now.
					System.out.println("Task removed from the list");
					flag=1;
					continue;
				}
				else {
					try {
						repo.addTask(tasks);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if(flag!=1) {
				System.out.println("The name you have eneterd to delete is not present in the list.");
			}
		}
		
		private static void searchTask() {
			
			int flag=0;
			IToDoRepo repo=null;    // due to try- catch
			try {
				repo = ToDoRepo.getInstance();   //Object will be shared
			} catch (IOException e) {
				e.printStackTrace();
			} 
			scanner.nextLine();
			System.out.println("Enter the task name you want to search: ");
			String choice= scanner.nextLine();
			ArrayList<ToDoDTO> tasks=null;
			try {
				tasks = repo.printTasks();                // read all tasks: because tasks are present inside it + added to the arraylist
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			for(ToDoDTO task: tasks) {
				
				boolean value=choice.replaceAll("//s","").equalsIgnoreCase(task.getName().replaceAll("//s",""));
				if(value) {
					System.out.println("Record found in the already existing tasks which is- "+task.getName()+" and the desc is- "+ task.getDesc());
					flag=1;
				}
//				else {
//					System.out.println("Record doesnot exists");
//				}
			}
			if(flag!=1) {
				System.out.println("No such record Exists.");
			}
			
			//ArrayList<ToDoDTO> tasks=  repo.printTasks();
			
		}
	 
	 
	public static void main(String[] args) {
		
		outer:
		while(true) {
			System.out.println(getValue("addtask"));
			System.out.println(getValue("deletetask"));
			System.out.println(getValue("updatetask"));
			System.out.println(getValue("searchtask"));
			System.out.println(getValue("printtask"));
			System.out.println(getValue("exittask"));
			System.out.println(getValue("choice"));
			
			int choice= scanner.nextInt();
			switch(choice) {
			case ADD_TASK:
				addTask();
				break;
			case PRINT_TASK:
				printAllTasks();
				break;
			case SEARCH_TASK:
				searchTask();
				break;
			case DELETE_TASK:
				deleteAllTask();
				break;
			case UPDATE_TASK:
				updateTask();
				break;
			case EXIT:
				break outer;
			}
		}
		scanner.close();             //outer apply otherwise this says unreachable code as while will always work (infinite loop)
	}



}
