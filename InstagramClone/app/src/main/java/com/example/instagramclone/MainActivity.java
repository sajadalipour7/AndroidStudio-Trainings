package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {

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
        btnSignUpSig=findViewById(R.id.btnSignUpSig);
        btnLoginSig=findViewById(R.id.btnLoginSig);

        if(ParseUser.getCurrentUser()!=null){
            ParseUser.getCurrentUser().logOut();
        }

        btnSignUpSig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser appUser=new ParseUser();
                appUser.setEmail(edtEnterEmail.getText().toString());
                appUser.setUsername(edtUserName.getText().toString());
                appUser.setPassword(edtEnterPassword.getText().toString());

                ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Signing up "+edtUserName.getText().toString());
                progressDialog.show();
                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            FancyToast.makeText(getApplicationContext(),appUser.get("username")+" is signed up",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();

                        }else{
                            FancyToast.makeText(getApplicationContext(),"ERROR : "+e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });

        btnLoginSig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }



}