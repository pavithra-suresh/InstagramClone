package com.example.instagramclone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class UsersTab extends Fragment {

    private ListView mListView;

    private ArrayList mArrayList;

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
}