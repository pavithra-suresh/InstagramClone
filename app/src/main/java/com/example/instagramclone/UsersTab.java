package com.example.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


public class UsersTab extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private ListView mListView;
    private ArrayList<String> mArrayList;
    private ArrayAdapter mArrayAdapter;

    public UsersTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_tab, container, false);

        mListView = view.findViewById(R.id.listView);
        mArrayList = new ArrayList();
        mArrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,mArrayList);

        mListView.setOnItemClickListener(UsersTab.this);
        mListView.setOnItemLongClickListener(UsersTab.this);

        final TextView txtloadingUsers = view.findViewById(R.id.txtloadingUsers);

        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();

        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());

        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {

                if(e==null) {

                    if(users.size()>0) {

                        for(ParseUser user: users){

                            mArrayList.add(user.getUsername());


                        }

                        mListView.setAdapter(mArrayAdapter);
                        txtloadingUsers.animate().alpha(0).setDuration(2000);
                        mListView.setVisibility(View.VISIBLE);

                    }

                }

            }
        });

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getContext(), UsersPosts.class);
        intent.putExtra("username",mArrayList.get(position));
        startActivity(intent);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereEqualTo("username",mArrayList.get(position));
        parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user!= null && e==null){

                    //FancyToast.makeText(getContext(),user.get("profileProfession")+ "", Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();

                    final PrettyDialog prettyDialog =  new PrettyDialog(getContext());

                    prettyDialog.setTitle(user.getUsername() + " 's Info")
                            .setMessage(user.get("profileBio") + "\n"
                                    + user.get("profileProfession") + "\n"
                                    + user.get("profileHobbies") + "\n"
                                    + user.get("profileFavSport"))
                            .setIcon(R.drawable.person)
                            .addButton(
                                    "OK",     // button text
                                    R.color.pdlg_color_white,  // button text color
                                    R.color.pdlg_color_green,  // button background color
                                    new PrettyDialogCallback() {  // button OnClick listener
                                        @Override
                                        public void onClick() {
                                            // Do what you gotta do
                                            prettyDialog.dismiss();
                                        }
                                    }
                            )
                            .show();

                }
            }
        });

        return true;
    }
}