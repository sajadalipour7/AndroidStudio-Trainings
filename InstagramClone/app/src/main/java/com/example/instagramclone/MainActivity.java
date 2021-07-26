package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SignUpLoginActivity.class);
                startActivity(intent);
            }
        });
    }



    public void helloWorldTapped(View view){

//        ParseObject boxer=new ParseObject("Boxer");
//        boxer.put("punch_speed",200);
//        boxer.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e==null){
//                    Toast.makeText(getApplicationContext(),"boxer object is saved successfully",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//        ParseObject kickBoxer=new ParseObject("KickBoxer");
//        kickBoxer.put("name","John");
//        kickBoxer.put("punchSpeed",1000);
//        kickBoxer.put("punchPower",2000);
//        kickBoxer.put("kickSpeed",3000);
//        kickBoxer.put("kickPower",4000);
//        kickBoxer.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                Toast.makeText(getApplicationContext(),kickBoxer.get("name")+" is saved to server",Toast.LENGTH_SHORT).show();
//
//            }
//        });




    }
}