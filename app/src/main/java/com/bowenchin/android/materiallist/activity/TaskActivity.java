package com.bowenchin.android.materiallist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bowenchin.android.materiallist.R;

/**
 * Created by bowenchin on 12/6/15.
 */
public class TaskActivity extends ActionBarActivity {
    private ImageButton FAB;
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private String CLASS_NAME = "dada";
    private ArrayAdapter<String> itemsAdapter;
    private String taskString;
    public TaskActivity() {
        Log.d(CLASS_NAME, "TaskActivity.class....");
        CLASS_NAME = getClass().getName();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    public void toastButton(View view){
        Toast.makeText(TaskActivity.this, "Creating and saving new task", Toast.LENGTH_SHORT).show();
        EditText etNewItem = (EditText) findViewById(R.id.editText);
        String itemText = etNewItem.getText().toString();
        etNewItem.setText("");

        Intent i = new Intent(TaskActivity.this,MainActivity.class);
        i.putExtra("taskText", itemText);
        startActivity(i);

        //taskString = itemText;
        //MainActivity activity = new MainActivity();
        //activity.onAddItem(itemText);

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
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goHome() {
        Log.d(CLASS_NAME, "goHome");
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
    }
}