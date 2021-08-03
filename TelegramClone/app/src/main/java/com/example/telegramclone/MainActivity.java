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

import com.parse.ParseException;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.parse.Parse;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtEmailSignUp,edtUsernameSignUp,edtPasswordSignUp;
    private Button btnSignUpSignUp,btnLoginSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Sign Up");



        //Initializing UI
        edtEmailSignUp=findViewById(R.id.edtEmailSignUp);
        edtUsernameSignUp=findViewById(R.id.edtUserNameSignUp);
        edtPasswordSignUp=findViewById(R.id.edtPasswordSignUp);
        btnSignUpSignUp=findViewById(R.id.btnSignUpSignUp);
        btnLoginSignUp=findViewById(R.id.btnLoginSignUp);

        //Done by enter for password
        edtPasswordSignUp.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i==KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN){
                    onClick(btnSignUpSignUp);
                }
                return false;
            }
        });

        //Token Check
        if(ParseUser.getCurrentUser()!=null){
//            ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }

        btnSignUpSignUp.setOnClickListener(this);
        btnLoginSignUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLoginSignUp:
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnSignUpSignUp:
                if(edtUsernameSignUp.getText().toString().equals("") || edtEmailSignUp.getText().toString().equals("") ||
                        edtPasswordSignUp.getText().toString().equals("")){

                    FancyToast.makeText(getApplicationContext(),"Please fill the form completely",FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                }else {

                    ParseUser appUser = new ParseUser();
                    appUser.setEmail(edtEmailSignUp.getText().toString());
                    appUser.setUsername(edtUsernameSignUp.getText().toString());
                    appUser.setPassword(edtPasswordSignUp.getText().toString());

                    ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Signing up " + edtUsernameSignUp.getText().toString());
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
        Intent intent=new Intent(MainActivity.this,TelegramPage.class);
        startActivity(intent);
        finish();
    }
}