package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLoginEmail,edtLoginPassword;
    private Button btnLoginLog,btnSignUpLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Log In");

        edtLoginEmail=findViewById(R.id.edtLoginEmail);
        edtLoginPassword=findViewById(R.id.edtLoginPassword);

        edtLoginPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i==KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN){
                    onClick(btnLoginLog);
                }
                return false;
            }
        });

        btnLoginLog=findViewById(R.id.btnLoginLog);
        btnSignUpLog=findViewById(R.id.btnSignUpLog);

        if(ParseUser.getCurrentUser()!=null){
//            ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }

        btnLoginLog.setOnClickListener(this);
        btnSignUpLog.setOnClickListener(this);
    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnLoginLog:
                if(edtLoginPassword.getText().toString().equals("") || edtLoginEmail.getText().toString().equals("")){
                    FancyToast.makeText(getApplicationContext(),"Please fill the form completely",FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                }else {
                    ParseUser.logInInBackground(edtLoginEmail.getText().toString(), edtLoginPassword.getText().toString(),
                            new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    if (user != null && e == null) {
                                        FancyToast.makeText(getApplicationContext(), user.getUsername() + " is Logged in", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                        transitionToSocialMediaActivity();
                                    }
                                }
                            });
                }
                break;
            case R.id.btnSignUpLog:
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }


    public void rootLayoutTapped(View v){
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){

        }
    }

    private void transitionToSocialMediaActivity(){
        Intent intent=new Intent(LoginActivity.this,SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }
}