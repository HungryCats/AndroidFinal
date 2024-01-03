package com.sdbi.a2236140201.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sdbi.a2236140201.R;
import com.sdbi.a2236140201.activity.MainActivity;
import com.sdbi.a2236140201.activity.me.AdviceActivity;
import com.sdbi.a2236140201.domain.User;

import java.util.ArrayList;
import java.util.Objects;


public class MeFragment extends Fragment {
    ArrayList<User> list;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("sex", Context.MODE_PRIVATE);
        String sex = sharedPreferences.getString("s", "");

        Intent intent = requireActivity().getIntent();
        list = intent.getParcelableArrayListExtra("LoginUser");
        // list != null
        User user = Objects.requireNonNull(list).get(0);
        final String username = user.getUserId();
        TextView tv_welcome = view.findViewById(R.id.tv_welcome);
        tv_welcome.setText(String.format("%s,%s", username, sex));

        Button yijian = view.findViewById(R.id.btn_yijian);
        yijian.setOnClickListener(e -> {
            startActivity(new Intent(getActivity(), AdviceActivity.class));
        });

        Button button = view.findViewById(R.id.btn_exit);
        button.setOnClickListener(e -> startActivity(new Intent(getActivity(), MainActivity.class)));
        return view;
    }
}