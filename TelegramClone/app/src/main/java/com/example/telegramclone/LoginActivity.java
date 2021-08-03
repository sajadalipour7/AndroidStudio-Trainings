package com.example.telegramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.shashank.sony.fancytoastlib.FancyToast;

import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edtUsernameLogin,edtPasswordLogin;
    private Button btnLoginLogin,btnSignUpLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Log in");

        //Initializing UI
        edtUsernameLogin=findViewById(R.id.edtUserNameLogin);
        edtPasswordLogin=findViewById(R.id.edtPasswordLogin);
        btnLoginLogin=findViewById(R.id.btnLoginLogin);
        btnSignUpLogin=findViewById(R.id.btnSignUpLogin);


        edtPasswordLogin.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i== KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN){
                    onClick(btnLoginLogin);
                }
                return false;
            }
        });

        //Token check
        if(ParseUser.getCurrentUser()!=null){
//            ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }

        btnSignUpLogin.setOnClickListener(this);
        btnLoginLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLoginLogin:
                if(edtPasswordLogin.getText().toString().equals("") || edtUsernameLogin.getText().toString().equals("")){
                    FancyToast.makeText(getApplicationContext(),"Please fill the form completely",FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                }else {
//                    ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
//                    progressDialog.setMessage("Logging in " + edtUsernameLogin.getText().toString());
//                    progressDialog.show();
                    ParseUser.logInInBackground(edtUsernameLogin.getText().toString(), edtPasswordLogin.getText().toString(),
                            new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    if (user != null && e == null) {
                                        FancyToast.makeText(getApplicationContext(), user.getUsername() + " is Logged in", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
//                                        progressDialog.dismiss();
                                        transitionToSocialMediaActivity();
                                    }else{
                                        FancyToast.makeText(getApplicationContext(),e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
//                                        progressDialog.dismiss();
                                    }
                                }
                            });
                }
                break;
            case R.id.btnSignUpLogin:
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
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
        Intent intent=new Intent(LoginActivity.this,TelegramPage.class);
        startActivity(intent);
        finish();
    }
}