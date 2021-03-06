package com.tiwari.suraj.rello;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


     VideoAdapter videoAdapter;
     FloatingActionButton likeBtn;
    public static TextView likeCountTxtv;
    public static GoogleSignInAccount account;
    public static String username,email;
    public static int likeCount;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        likeCountTxtv = (TextView) v.findViewById(R.id.likeCount);

        final ViewPager2 videoViewPager = v.findViewById(R.id.videosViewPager);
        account = GoogleSignIn.getLastSignedInAccount(getContext());
        if(account != null){
            username = account.getDisplayName();
            email = account.getEmail();
        }

        FirebaseRecyclerOptions<VideoItem> videoitem =
                new FirebaseRecyclerOptions.Builder<VideoItem>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Videos"), VideoItem.class)
                        .build();

        videoAdapter = new VideoAdapter(videoitem);
        videoViewPager.setAdapter(videoAdapter);

        FirebaseRecyclerOptions<Comment> commentFirebaseRecyclerOptions =
                new FirebaseRecyclerOptions.Builder<Comment>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Comments"),Comment.class)
                .build();


        return v;
    }
        @Override
        public void onStart(){
            super.onStart();
            videoAdapter.startListening();
        }
        @Override
        public void onStop(){
            super.onStop();
            videoAdapter.startListening();
        }



}