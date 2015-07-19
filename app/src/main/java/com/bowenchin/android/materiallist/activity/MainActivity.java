package com.bowenchin.android.materiallist.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.bowenchin.android.materiallist.R;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private ImageButton FAB;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;
    private int counter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // ADD HERE
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        items.add("This is a task");
        items.add("Press and hold on any tasks to delete them");
        setupListViewListener();

        FAB = (ImageButton) findViewById(R.id.add_task);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddTask(null);

                //Toast.makeText(MainActivity.this, "Creating and saving new task", Toast.LENGTH_SHORT).show();
                /*EditText etNewItem = (EditText) findViewById(R.id.addNewTask);
                String itemText = etNewItem.getText().toString();
                itemsAdapter.add(itemText);
                etNewItem.setText("");
                writeItems();*/
            }
        });

        Bundle extras = getIntent().getExtras();
        String addTask= null;
        if(extras !=null && extras.containsKey("taskText"))
        {
            addTask = extras.getString("taskText");
            itemsAdapter.add(addTask);
            writeItems();


        }

        // display the first navigation drawer view on app launch
        //displayView(0);
    }

    public void onAddItem() {
        //EditText etNewItem = (EditText) findViewById(R.id.addNewTask);
        //String itemText = etNewItem.getText().toString();
        //etNewItem.setText("");
        //itemsAdapter.add(str);
        //writeItems();
        Intent i = getIntent();
        String enteredTask = i.getStringExtra("taskText");
        itemsAdapter.add(enteredTask);
        writeItems();
    }


    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        // Remove the item within array at position
                        items.remove(pos);
                        // Refresh the adapter
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        // Return true consumes the long click event (marks it handled)
                        return true;
                    }

                });
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    public void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                gotoHome();
                return true;
            case R.id.action_settings:
                gotoSettings(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void gotoSettings(View view) {
        Intent settings = new Intent(this, SettingsActivity.class);
        startActivity(settings);
    }

    private void gotoHome() {
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
    }

    private void gotoAddTask(View view) {
        Intent addTask = new Intent(this, TaskActivity.class);
        startActivity(addTask);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                title = getString(R.string.title_home);
                break;
            /*case 1:
                //fragment = new FriendsFragment();
                title = getString(R.string.title_friends);
                break;
            case 2:
                //fragment = new WorkFragment();
                title = getString(R.string.title_messages);
                break;*/
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}
