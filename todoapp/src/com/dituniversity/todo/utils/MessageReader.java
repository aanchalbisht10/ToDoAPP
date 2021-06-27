package com.dituniversity.todo.utils;

import java.util.ResourceBundle;
// static because we are importing a constant and it should be in the file before execution
import static com.dituniversity.todo.utils.Constants.MESSAGE_BUNDLE_FILE_NAME;

public interface MessageReader {
	ResourceBundle rb= ResourceBundle.getBundle(MESSAGE_BUNDLE_FILE_NAME);
	
	public static String getValue(String key) {
		return rb.getString(key);
	}

}
// This is made interface so that method can be called without creating object hence saving memory
