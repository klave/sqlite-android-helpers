package com.klave.sqlite.android.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.klave.sqlite.android.helpers.MySqliteHelper;
import com.klave.sqlite.android.model.User;

public class UsersDataSource {

	private SQLiteDatabase database;
	private MySqliteHelper dbHelper;
	private String[] allColumns = { MySqliteHelper.COLUMN_ID,
			MySqliteHelper.COLUMN_USER };

	public UsersDataSource(Context context) {
		dbHelper = new MySqliteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public User createUser(String username) {
		ContentValues values = new ContentValues();
		values.put(MySqliteHelper.COLUMN_USER, username);
		long insertId = database.insert(MySqliteHelper.TABLE_USERS, null,
				values);
		Cursor cursor = database.query(MySqliteHelper.TABLE_USERS, allColumns,
				MySqliteHelper.COLUMN_ID + " = " + insertId, null, null, null,
				null);
		cursor.moveToFirst();
		User newUser = cursorToUser(cursor);
		cursor.close();
		return newUser;
	}

	private User cursorToUser(Cursor cursor) {
		User user = new User();
		user.setId(cursor.getLong(0));
		user.setUsername(cursor.getString(1));
		return user;
	}
	
	public void deleteUser(User user){
		long id= user.getId();
		Log.i("User deleted with id:", ""+id);
	}
	
	public List<User> getAllUsers(){
		List<User> usersList=new ArrayList<User>();
		Cursor cursor = database.query(MySqliteHelper.TABLE_USERS, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			User user= cursorToUser(cursor);
			usersList.add(user);
			cursor.moveToNext();
		}
		cursor.close();
		return usersList;
	}

}
