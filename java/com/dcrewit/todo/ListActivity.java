package com.dcrewit.todo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    public static final String TASK_TITLE = "TASKNAME";
    public static final String TASK_DESCRIPTION = "DESCRIPTION";
    private List<Task> messageList;
    int index = 0;

    private MenuItem menuItemSearch;
    private MenuItem menuItemDelete;
    private TextView alertTextView;
    CustomAdapter adapter;
    String datetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ListView lv = findViewById(R.id.list_item);
        //messageList = new ArrayList<>();
        loadData();
        adapter = new CustomAdapter(getApplicationContext(), messageList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                index = i;
                //Snackbar.make(findViewById(R.id.list_co_layout),"You selected com.example.todo.Task:"+messageList.get(i),Snackbar.LENGTH_SHORT).show();
                Intent intent = new Intent(ListActivity.this, DescriptionActivity.class);
                TextView title = findViewById(R.id.descripition);
                intent.putExtra(TASK_TITLE, messageList.get(i).getMessage());

                if (messageList.get(i).getDescription()!=null && !messageList.get(i).getDescription().isEmpty())
                    intent.putExtra(TASK_DESCRIPTION, messageList.get(i).getDescription());
                startActivityForResult(intent, 2);
            }
        });

        lv.setOnItemLongClickListener((parent, view, position, id) -> {
            //our implementation here
            adapter.handleLongPress(position, view);
            if(adapter.getCount()> 0){
                showDeleteMenu(true);
            }else{
                showDeleteMenu(false);
            }
            return true;
        });


    }
    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(messageList);
        editor1.putString("task list", json);
        editor1.apply();

    }
    private void loadData(){
        SharedPreferences sharedPreferences=getSharedPreferences("shared preferences",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("task list",null);
        Type type=new TypeToken<ArrayList<Task>>(){}.getType();
        messageList=gson.fromJson(json,type);
        if(messageList==null){
            messageList=new ArrayList<>();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menuItemDelete = menu.findItem(R.id.action_delete);
        menuItemSearch = menu.findItem(R.id.action_search);
        menuItemDelete.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_logout:
                popUp();
                return true;

            case R.id.action_search:
                return true;

            case R.id.action_delete:

                adapter.removeSelectedPersons();
                adapter.notifyDataSetChanged();
                showDeleteMenu(false);
                saveData();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    public void fabOnClick(View view) {
        Intent msg = new Intent(this, MessageActivity.class);
        startActivityForResult(msg, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                String datetime=dateTime();
                Task task=new Task(result,null,datetime);
                messageList.add(task);
                saveData();
            }
        }
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("TASK_DETAILS");
                messageList.get(index).setDescription(result);
                saveData();
            }
        }

    }//onActivityResult
    public String dateTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());
        return  strDate;
    }

    private void showDeleteMenu(boolean show) {
        menuItemDelete.setVisible(show);
        menuItemSearch.setVisible(!show);

    }
    public void popUp(){

            AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);

            builder.setCancelable(true);
            builder.setTitle("Logout Alert!!!");
            builder.setMessage("Your data will be lost.Do you want to continue?");

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferences settings1 = getSharedPreferences("PREFS_NAME", 0);
                    SharedPreferences.Editor editor = settings1.edit();
                    editor.clear();
                    editor.apply();
                    SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor1=sharedPreferences.edit();
                    editor1.clear();
                    editor1.apply();
                    Intent intent=new Intent(ListActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            builder.show();

    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit?");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        builder.show();
    }

}

