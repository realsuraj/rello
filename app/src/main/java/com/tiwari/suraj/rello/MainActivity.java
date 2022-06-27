package com.tiwari.suraj.rello;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    DatabaseReference databaseReference;
    GoogleSignInAccount account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            PrefConfig.setUsername(account.getDisplayName());
        }

        if(PrefConfig.getUsername() != null)
        {
            Intent intent = new Intent(this,HomePage.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this,LoginPage.class);
            startActivity(intent);
        }

    }
}