package com.dcrewit.todo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MessageActivity extends AppCompatActivity {

    EditText editText;
    Context context;
    //Snackbar mySnackbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        editText = findViewById(R.id.editText);
        context = getApplicationContext();
    }
    public void onClickAdd(View view){
        String result = editText.getText().toString();
        if(!result.trim().isEmpty()) {
            Intent add = new Intent();
            add.putExtra("result", result);
            setResult(Activity.RESULT_OK, add);
            finish();

        }
        else{
            Toast.makeText(context,"Task name is empty" ,Toast.LENGTH_SHORT).show();
//            Snackbar.make(findViewById(id.co_layout),string.promt_empty,Snackbar.LENGTH_SHORT).show();
        }
    }

}