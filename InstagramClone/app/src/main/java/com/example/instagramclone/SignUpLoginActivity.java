package com.example.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpLoginActivity extends AppCompatActivity {

    private EditText edtUserNameLogin,edtUserNameSignUp,edtPasswordLogin,edtPasswordSignUp;
    private Button btnLogin,btnSignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup_login_activity);

        edtUserNameLogin=findViewById(R.id.edtUserNameLogin);
        edtPasswordLogin=findViewById(R.id.edtPasswordLogin);
        edtUserNameSignUp=findViewById(R.id.edtUserNameSignUp);
        edtPasswordSignUp=findViewById(R.id.edtPasswordSignUp);
        btnLogin=findViewById(R.id.btnLogin);
        btnSignUp=findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser appUser=new ParseUser();
                appUser.setUsername(edtUserNameSignUp.getText().toString());
                appUser.setPassword(edtPasswordSignUp.getText().toString());

                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            FancyToast.makeText(getApplicationContext(),appUser.get("username")+" is signed up successfully",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                            Intent intent=new Intent(SignUpLoginActivity.this,WelcomeActivity.class);
                            startActivity(intent);
                        }else{
                            FancyToast.makeText(getApplicationContext(),e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                        }
                    }
                });
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logInInBackground(edtUserNameLogin.getText().toString(), edtPasswordLogin.getText().toString(),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if(user!=null && e==null){
                                    FancyToast.makeText(getApplicationContext(),user.get("username")+" is logged in successfully",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                                    Intent intent=new Intent(SignUpLoginActivity.this,WelcomeActivity.class);
                                    startActivity(intent);
                                }else{
                                    FancyToast.makeText(getApplicationContext(),e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                                }
                            }
                        });
            }
        });
    }
}
