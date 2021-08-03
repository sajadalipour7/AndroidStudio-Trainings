package com.example.telegramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class TelegramPage extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ArrayList<String> telegramUsers;
    private ArrayAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telegram_page);
        setTitle("Telegaboor");

        listView=findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        telegramUsers=new ArrayList<>();
        adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,telegramUsers);
        swipeRefreshLayout=findViewById(R.id.swipeContainer);

        try{
            ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();
            parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
            parseQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if(objects.size()>0 && e==null){
                        for(ParseUser user:objects){
                            telegramUsers.add(user.getUsername());
                        }
                        listView.setAdapter(adapter);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try{
                    ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();
                    parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
                    parseQuery.whereNotContainedIn("username",telegramUsers);
                    parseQuery.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> objects, ParseException e) {
                            if(objects.size()>0 && e==null){
                                for(ParseUser user:objects){
                                    telegramUsers.add(user.getUsername());
                                }
                                adapter.notifyDataSetChanged();
                                if(swipeRefreshLayout.isRefreshing()){
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            }else{
                                if(swipeRefreshLayout.isRefreshing()){
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            }
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutItem:
                FancyToast.makeText(TelegramPage.this, ParseUser.getCurrentUser().getUsername()+" is logged out",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null) {
                            Intent intent = new Intent(TelegramPage.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(TelegramPage.this,ChatActivity.class);
        intent.putExtra("selectedUser",telegramUsers.get(position));
        startActivity(intent);
    }
}