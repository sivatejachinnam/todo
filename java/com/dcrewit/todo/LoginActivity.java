package com.dcrewit.todo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    CheckBox keeplog;
    boolean isChecked = false;
    EditText editTextUserName;
    EditText editTextPassword;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // creating a instance of SQLite Database
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        // Get The Refference Of Buttons
//        btnSignUp=(Button)findViewById(R.id.buttonSignUP);
//
//        // Set OnClick Listener on SignUp button
//        btnSignUp.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//
//                // Create Intent for SignUpActivity abd Start The Activity
//                Intent intent=new Intent(LoginActivity.this,SignUPActivity.class);
//                startActivity(intent);
//            }
//        });


        editTextUserName= findViewById(R.id.editTextUserName);
        editTextPassword=findViewById(R.id.editTextPassword);
        keeplog = findViewById(R.id.checkBoxSignedIn);
        keeplog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("isChecked", isChecked);
                editor.apply();
            }
        });

        SharedPreferences settings1 = getSharedPreferences("PREFS_NAME", 0);
        isChecked = settings1.getBoolean("isChecked", false);
        if (isChecked) {
            View view=findViewById(R.id.button2);
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
            finish();
        }else{
            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor1=sharedPreferences.edit();
            editor1.clear();
            editor1.apply();
        }

    }
    public void Login(View view) {
        // get The User name and Password
        String userName=editTextUserName.getText().toString();
        String password=editTextPassword.getText().toString();

        // fetch the Password form database for respective user name
        String storedPassword=loginDataBaseAdapter.getSinlgeEntry(userName);

        // check if the Stored password matches with Password entered by user
        if(password.equals(storedPassword))
        {
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(LoginActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();

        }
        else
        {
            Toast.makeText(LoginActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
        }
    }

    public void signUp(View view){
        Intent intent=new Intent(LoginActivity.this,SignUPActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    // Close The Database
        loginDataBaseAdapter.close();
    }
}