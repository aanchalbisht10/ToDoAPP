package com.dituniversity.todo.repo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import static com.dituniversity.todo.utils.Constants.PATH;

import com.dituniversity.todo.dto.ToDoDTO;

public class ToDoRepo implements IToDoRepo {
	private File file;
	private static ToDoRepo todoRepo; // bydefault this is null object of todorepo class
	private ToDoRepo() throws IOException {
		file= new File(PATH);
		file.createNewFile();
	}
	
	public static ToDoRepo getInstance() throws IOException {
		if(todoRepo==null) {
			todoRepo= new ToDoRepo();
		}
		return todoRepo;
	}

	@Override
	public boolean addTask(ArrayList<ToDoDTO> tasks) throws IOException {// earlier here was todo it has to be removed because it took one object but here we are trying to work with multiple data at once using tasks
		
		FileOutputStream fo=null;
		ObjectOutputStream os=null;
		try {
			fo= new FileOutputStream(file);
			os= new ObjectOutputStream(fo);
			os.writeObject(tasks);
		}
		finally {
			if(os!=null) {
				os.close();
			}
			if(fo!=null) {
				fo.close();
			}
		}		
		return true;
	}

	@Override
	public ArrayList<ToDoDTO> printTasks() throws FileNotFoundException, IOException, ClassNotFoundException {
		ArrayList<ToDoDTO> list=new ArrayList<>();
		try(FileInputStream fi= new FileInputStream(file)){
			try(ObjectInputStream oi= new ObjectInputStream(fi)){
				list= (ArrayList<ToDoDTO>)oi.readObject();
			}
			
		}
		return list;
	}
	

}
