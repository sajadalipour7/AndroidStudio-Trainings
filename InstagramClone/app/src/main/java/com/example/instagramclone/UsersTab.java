package com.example.instagramclone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsersTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsersTab extends Fragment implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {

    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UsersTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UsersTab.
     */
    // TODO: Rename and change types and number of parameters
    public static UsersTab newInstance(String param1, String param2) {
        UsersTab fragment = new UsersTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_users_tab, container, false);
        listView=view.findViewById(R.id.listView);
        arrayList=new ArrayList();
        arrayAdapter=new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,arrayList);

        listView.setOnItemClickListener(UsersTab.this);
        listView.setOnItemLongClickListener(UsersTab.this);

        TextView txtLoading=view.findViewById(R.id.txtLoading);

        ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if(e==null){
                    if(users.size()>0){
                        for(ParseUser user:users){
                            arrayList.add(user.getUsername());
                        }
                        listView.setAdapter(arrayAdapter);
                        txtLoading.animate().alpha(0).setDuration(2000);
                        listView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent=new Intent(getContext(),UsersPost.class);
        intent.putExtra("username",arrayList.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();
        parseQuery.whereEqualTo("username",arrayList.get(position));
        parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user!=null && e==null){
                    try {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                        alertDialog.setTitle(user.getUsername() + "'s Info")
                                .setMessage("Bio : " + user.get("profileBio") + "\n"
                                        + "Profession : " + user.get("profileProfession") + "\n"
                                        + "Hobbies : " + user.get("profileHobbies") + "\n"
                                        + "Favorite Sports : " + user.get("profileFavSport"))
                                .setIcon(R.drawable.person)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });

                        AlertDialog alertShow = alertDialog.create();
                        alertDialog.show();
                    }catch (Exception ee){
                        FancyToast.makeText(getContext(),ee.getMessage(),FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                    }
//                    PrettyDialog prettyDialog=new PrettyDialog(getContext());
//                    prettyDialog.setTitle(user.getUsername()+"'s Info")
//                        .setMessage(user.get("profileBio")+"\n"
//                        +user.get("profileProfession")+"\n"
//                        +user.get("profileHobbies")+"\n"
//                        +user.get("profileFavSport"))
//                        .setIcon(R.drawable.person)
//                        .addButton("OK",
//                                R.color.pdlg_color_white,
//                                R.color.pdlg_color_green,
//                                new PrettyDialogCallback() {
//                                    @Override
//                                    public void onClick() {
//                                        prettyDialog.dismiss();
//                                    }
//                                }).show();

                }else{
                    FancyToast.makeText(getContext(),e.getMessage(),FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                }
            }
        });
        return true;
    }
}