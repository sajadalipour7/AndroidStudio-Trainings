package com.example.telegramclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView chatListView;
    private ArrayList<String> chatsList;
    private ArrayAdapter adapter;
    private String selectedUser;

    private Button btnSend;
    private EditText edtChat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        selectedUser=getIntent().getStringExtra("selectedUser");
        setTitle(selectedUser);
        FancyToast.makeText(ChatActivity.this,"Chat with "+selectedUser,FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
        edtChat=findViewById(R.id.edtChat);
        btnSend=findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        chatListView=findViewById(R.id.chatListView);
        chatsList=new ArrayList<>();
        adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,chatsList);
        chatListView.setAdapter(adapter);


        try {
            ParseQuery<ParseObject> firstUserChatQuery = ParseQuery.getQuery("Chat");
            ParseQuery<ParseObject> secondUserChatQuery = ParseQuery.getQuery("Chat");

            firstUserChatQuery.whereEqualTo("Sender", ParseUser.getCurrentUser().getUsername());
            firstUserChatQuery.whereEqualTo("Target", selectedUser);

            secondUserChatQuery.whereEqualTo("Sender", selectedUser);
            secondUserChatQuery.whereEqualTo("Target", ParseUser.getCurrentUser().getUsername());

            ArrayList<ParseQuery<ParseObject>> allQueries = new ArrayList<>();
            allQueries.add(firstUserChatQuery);
            allQueries.add(secondUserChatQuery);

            ParseQuery<ParseObject> myQuery = ParseQuery.or(allQueries);
            myQuery.orderByAscending("createdAt");

            myQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (objects.size() > 0 && e == null) {
                        for (ParseObject chatObject : objects) {
                            String message = chatObject.get("Message") + "";
                            if (chatObject.get("Sender").equals(ParseUser.getCurrentUser().getUsername())) {
                                message = ParseUser.getCurrentUser().getUsername() + " : " + message;
                            }
                            if (chatObject.get("Sender").equals(selectedUser)) {
                                message = selectedUser + " : " + message;
                            }
                            chatsList.add(message);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        ParseObject chat=new ParseObject("Chat");
        chat.put("Sender", ParseUser.getCurrentUser().getUsername());
        chat.put("Target",selectedUser);
        String message=edtChat.getText().toString();
        chat.put("Message",message);
        chat.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    FancyToast.makeText(ChatActivity.this,"Message from "+ParseUser.getCurrentUser().getUsername()+" sent to "+selectedUser+" successfully." ,FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                    chatsList.add(ParseUser.getCurrentUser().getUsername()+" : "+message);
                    adapter.notifyDataSetChanged();
                    edtChat.setText("");
                }
            }
        });
    }
}