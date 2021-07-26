package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ProgressCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class UsersPost extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_post);
        linearLayout=findViewById(R.id.linearLayout);

        Intent receivedIntentObject=getIntent();
        String receivedUserName=receivedIntentObject.getStringExtra("username");

        setTitle(receivedUserName+"'s posts");

        ParseQuery<ParseObject> parseQuery=new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username",receivedUserName);
        parseQuery.orderByDescending("createdAt");

        ProgressDialog dialog=new ProgressDialog(UsersPost.this);
        dialog.setMessage("Retrieving data from server...");
        dialog.show();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects.size()>0 && e==null){
                    FancyToast.makeText(UsersPost.this,receivedUserName,FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                    for(ParseObject post:objects){
                        TextView txtCaption=new TextView(UsersPost.this);
                        if(post.get("image_des")==null){
                            txtCaption.setText("No caption");
                        }else {
                            txtCaption.setText(post.get("image_des").toString());
                        }
                        ParseFile postPicture=(ParseFile) post.get("picture");
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(data!=null && e==null){
                                    Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView postImageView = new ImageView(UsersPost.this);
                                    LinearLayout.LayoutParams imageViewParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imageViewParams.setMargins(5,5,5,5);
                                    postImageView.setLayoutParams(imageViewParams);
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams captionParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                    captionParams.setMargins(5,5,5,15);
                                    txtCaption.setLayoutParams(captionParams);
                                    txtCaption.setGravity(Gravity.CENTER);
                                    txtCaption.setBackgroundColor(Color.BLUE);
                                    txtCaption.setTextColor(Color.WHITE);
                                    txtCaption.setTextSize(30f);

                                    linearLayout.addView(postImageView);
                                    linearLayout.addView(txtCaption);
                                }
                            }
                        });

                    }
                }else{
                    FancyToast.makeText(UsersPost.this,receivedUserName+" doesn't have any post!",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                    finish();
                }
                dialog.dismiss();
            }
        });
    }
}