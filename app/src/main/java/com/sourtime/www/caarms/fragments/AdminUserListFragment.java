package com.sourtime.www.caarms.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sourtime.www.caarms.R;
import com.sourtime.www.caarms.activities.AdminUserActivity;
import com.sourtime.www.caarms.activities.AdminUserListActivity;
import com.sourtime.www.caarms.managers.DatabaseManager;
import com.sourtime.www.caarms.managers.SurveyManager;
import com.sourtime.www.caarms.managers.UserManager;
import com.sourtime.www.caarms.models.User;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by user on 03/02/2017.
 */

public class AdminUserListFragment extends Fragment {

    private static final String TAG = "AdminUserListFragment";

    private RecyclerView recyclerView;
    private DatabaseManager databaseHelper;
    //    private UserManager userManager;
    private List<User> userList;
    private UserListAdapter adapter;
    private UserManager userManager;
    private SurveyManager surveyManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager layoutManager;

        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        databaseHelper = DatabaseManager.getInstance(getActivity());
        userManager = UserManager.getInstance(getActivity());
        surveyManager = SurveyManager.getInstance(getActivity());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (recyclerView.getAdapter() == null) {
            Log.d(TAG, "adapter is null");
            adapter = new UserListAdapter(getUserList());
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private List<User> getUserList() {
        int currentUser = (int) userManager.getmCurrentUserId();
        Log.i(TAG, "Current user: " + currentUser);

        DatabaseManager.UserCursor cursor = databaseHelper.queryUsers();

        userList = new ArrayList<User>();
        cursor.moveToNext();
        while (cursor.moveToNext()) {
            User user = cursor.getUser();
            userList.add(user);
//            Log.i(TAG, "User user: " + user.getUser_id());
//            Log.i(TAG, "User id: " + user.getmId());

        }
        cursor.close();

        return userList;
    }

    class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.CustomViewHolder> {

        private List<User> users;

        public UserListAdapter(List<User> users) {
            super();
            this.users = users;
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getActivity().getLayoutInflater().inflate(R.layout.item_admin_user_list, parent, false);
            return new CustomViewHolder(v);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            User user = users.get(position);

            holder.userTitle.setText(user.getmEmail());

        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView userTitle;

            public CustomViewHolder(View itemView) {
                super(itemView);
                userTitle = (TextView) itemView.findViewById(R.id.user_name);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                final int position = getAdapterPosition();
                int userId = position + 2;
                userManager.setmCurrentUserId(userId);
                Intent i = new Intent(getActivity(), AdminUserActivity.class);
                startActivity(i);
//                Log.i(TAG,"position: " + position);
            }
        }
    }
}

