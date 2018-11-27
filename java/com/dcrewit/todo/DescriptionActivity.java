package com.dcrewit.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DescriptionActivity extends AppCompatActivity {
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Intent intent = getIntent();
        String tasktitle = intent.getStringExtra(ListActivity.TASK_TITLE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.taskname);
        textView.setText(tasktitle);

        String description=intent.getStringExtra(ListActivity.TASK_DESCRIPTION);
        editText= findViewById(R.id.descripition);
        editText.setText(description);
    }
    public void saveOnclick(View view) {

        String description= editText.getText().toString();
        Intent add = new Intent();
        add.putExtra("TASK_DETAILS",description);
        setResult(Activity.RESULT_OK, add);
        finish();
//        if(!description.trim().isEmpty()) {
//            Intent add = new Intent();
//            add.putExtra("TASK_DETAILS",description);
//            setResult(Activity.RESULT_OK, add);
//            finish();
//        }

    }
}
