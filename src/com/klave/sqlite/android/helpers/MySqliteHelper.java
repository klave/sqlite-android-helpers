package com.klave.sqlite.android.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqliteHelper extends SQLiteOpenHelper {

	public static final String TABLE_USERS = "users";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_USER = "user";

	private static final String DATABASE_NAME = "users.db";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table " + TABLE_USERS
			+ "(" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_USER + " text not null);";

	public MySqliteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySqliteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		onCreate(db);
	}

}
