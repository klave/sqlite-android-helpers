package com.klave.sqlite.android.activity;

import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.klave.sqlite.android.dao.UsersDataSource;
import com.klave.sqlite.android.model.User;
import com.klave.sqlite_android_helpers.R;

public class MainActivity extends ListActivity {
	private UsersDataSource dataSource;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dataSource = new UsersDataSource(this);
		dataSource.open();
		List<User> usersList = dataSource.getAllUsers();

		ArrayAdapter<User> adapter = new ArrayAdapter<User>(this,
				android.R.layout.simple_list_item_1, usersList);
		setListAdapter(adapter);

	}

	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<User> adapter = (ArrayAdapter<User>) getListAdapter();
		User user = null;
		switch (view.getId()) {
		case R.id.add:
			String[] users = new String[] { "Cool", "Very nice", "Hate it" };
			int nextInt = new Random().nextInt(3);
			// save the new comment to the database
			user = dataSource.createUser(users[nextInt]);
			adapter.add(user);
			break;
		case R.id.delete:
			if (getListAdapter().getCount() > 0) {
				user = (User) getListAdapter().getItem(0);
				dataSource.deleteUser(user);
				adapter.remove(user);
			}
			break;
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		dataSource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		dataSource.close();
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
