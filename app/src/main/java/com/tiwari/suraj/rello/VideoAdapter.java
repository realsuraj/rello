package com.tiwari.suraj.rello;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class VideoAdapter extends FirebaseRecyclerAdapter<VideoItem,VideoAdapter.VideoViewHolder> {

    DatabaseReference likeReference;
    Boolean testclick = false;
    public static DatabaseReference likeDatabaseReference;
    public static TextView likeCountTextview;
    public static FloatingActionButton likebtn;

    public VideoAdapter(@NonNull FirebaseRecyclerOptions<VideoItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull VideoViewHolder holder, int position, @NonNull VideoItem model) {
        holder.setVideoData(model);
        String userid = PrefConfig.getUsername();
        String videoKey = getRef(position).getKey();

        getLikeButtonStatus(userid,videoKey);
        likeReference = FirebaseDatabase.getInstance().getReference("Likes");
        likebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testclick = true;
            likeReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(testclick == true){                        if(dataSnapshot.child(videoKey).hasChild(userid)){
                            likeReference.child(videoKey).removeValue();
                            testclick = false;
                        }
                        else {
                            likeReference.child(videoKey).child(userid).setValue(true);
                            testclick =false;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            }
        });

    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_video,parent,false);
        return new VideoViewHolder(view);
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {

        VideoView videoView;
        TextView textVideoTitle, textVideoDescription;
        ProgressBar videoProgressBar;




        public VideoViewHolder(@NonNull View itemView){
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            textVideoTitle = itemView.findViewById(R.id.textVideoTitle);
            textVideoDescription = itemView.findViewById(R.id.textVideoDescription);
            videoProgressBar = itemView.findViewById(R.id.videoProgressBar);
            likebtn = itemView.findViewById(R.id.likeFloatingBtn);
            likeCountTextview = itemView.findViewById(R.id.likeCount);

        }
        void setVideoData(VideoItem videoItem){
            textVideoTitle.setText(videoItem.videoTitle);
            textVideoDescription.setText(videoItem.videoDescription);
            videoView.setVideoPath(videoItem.videoUrl);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    videoProgressBar.setVisibility(View.GONE);
                    mp.start();

                    float videoRatio = mp.getVideoWidth() / (float) mp.getVideoHeight();
                    float screenRatio = videoView.getWidth() / (float) videoView.getHeight();
                    float scale = videoRatio / screenRatio;

                    if(scale >= 1f){
                        videoView.setScaleX(scale);
                    } else{
                        videoView.setScaleY( 1f / scale);
                    }
                }
            });
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.start();
                }
            });
        }
    }

    public static void getLikeButtonStatus(String userid, String videoKey) {
        likeDatabaseReference = FirebaseDatabase.getInstance().getReference("Likes");

        likeDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(videoKey).hasChild(userid)){
                    int likeCount = (int) dataSnapshot.child(videoKey).getChildrenCount();
                    likeCountTextview.setText(likeCount + " Likes");
                    likebtn.setImageResource(R.drawable.ic_baseline_favorite_24);

                }
                else {
                    int likeCount = (int) dataSnapshot.child(videoKey).getChildrenCount();
                    likeCountTextview.setText(likeCount + " Likes");
                    likebtn.setImageResource(R.drawable.ic_baseline_favorite_border_24);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
