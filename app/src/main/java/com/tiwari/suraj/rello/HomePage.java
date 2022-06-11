package com.tiwari.suraj.rello;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    TextView infotxt ;
    Button logoutBtn;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        final ViewPager2 videoViewPager = findViewById(R.id.videosViewPage);
        List<VideoItem> videoItems = new ArrayList<>();



        infotxt = findViewById(R.id.detailtxt);
        logoutBtn = findViewById(R.id.logoutbtn);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);


        if(account != null){
            String name = account.getDisplayName();
            String mail = account.getEmail();
            infotxt.setText(name + " /n" + mail);
        }

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignOut();
            }


        });

        VideoItem videoItemCelebration = new VideoItem();
        videoItemCelebration.videoUrl = "https://assets.mixkit.co/videos/preview/mixkit-mysterious-pale-looking-fashion-woman-at-winter-39878-large.mp4";
        videoItemCelebration.videoTitle = "first video";
        videoItemCelebration.videoDescription = "first Description";
        videoItems.add(videoItemCelebration);

        VideoItem videoItemStart = new VideoItem();
        videoItemStart.videoUrl = "https://assets.mixkit.co/videos/preview/mixkit-portrait-of-a-woman-in-a-pool-1259-large.mp4";
        videoItemStart.videoTitle = "secound video";
        videoItemStart.videoDescription = "secound Description";
        videoItems.add(videoItemStart);

        VideoItem videoItemStart1 = new VideoItem();
        videoItemStart1.videoUrl = "https://assets.mixkit.co/videos/preview/mixkit-portrait-of-a-woman-in-a-pool-1259-large.mp4";
        videoItemStart1.videoTitle = "third video";
        videoItemStart1.videoDescription = "third Description";
        videoItems.add(videoItemStart1);

        videoViewPager.setAdapter(new VideoAdapter(videoItems));
    }

    private void SignOut() {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(HomePage.this,LoginPage.class));
            }
        });

    }
}