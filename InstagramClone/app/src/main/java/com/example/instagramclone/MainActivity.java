package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtEnterEmail,edtUserName,edtEnterPassword;
    private Button btnSignUpSig,btnLoginSig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Sign Up");

        edtEnterEmail=findViewById(R.id.edtEnterEmail);
        edtUserName=findViewById(R.id.edtUserName);
        edtEnterPassword=findViewById(R.id.edtEnterPassword);

        edtEnterPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i==KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN){
                    onClick(btnSignUpSig);
                }
                return false;
            }
        });

        btnSignUpSig=findViewById(R.id.btnSignUpSig);
        btnLoginSig=findViewById(R.id.btnLoginSig);

        if(ParseUser.getCurrentUser()!=null){
//            ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }

        btnSignUpSig.setOnClickListener(this);
        btnLoginSig.setOnClickListener(this);


    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnLoginSig:
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSignUpSig:
                if(edtUserName.getText().toString().equals("") || edtEnterEmail.getText().toString().equals("") ||
                        edtEnterPassword.getText().toString().equals("")){

                    FancyToast.makeText(getApplicationContext(),"Please fill the form completely",FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                }else {

                    ParseUser appUser = new ParseUser();
                    appUser.setEmail(edtEnterEmail.getText().toString());
                    appUser.setUsername(edtUserName.getText().toString());
                    appUser.setPassword(edtEnterPassword.getText().toString());

                    ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Signing up " + edtUserName.getText().toString());
                    progressDialog.show();
                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(getApplicationContext(), appUser.get("username") + " is signed up", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                progressDialog.dismiss();
                                transitionToSocialMediaActivity();
                            } else {
                                FancyToast.makeText(getApplicationContext(), "ERROR : " + e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                progressDialog.dismiss();
                            }

                        }
                    });
                }
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
        Intent intent=new Intent(MainActivity.this,SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }



}