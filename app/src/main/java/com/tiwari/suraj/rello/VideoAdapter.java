package com.tiwari.suraj.rello;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class VideoAdapter extends FirebaseRecyclerAdapter<VideoItem,VideoAdapter.VideoViewHolder> {

    DatabaseReference likeReference, commentRef;
    Boolean testclick = false;
    FirebaseRecyclerOptions<Comment> commentOption;
    FirebaseRecyclerAdapter<Comment, CommentViewHolder> commentAdaptor;
    public static RecyclerView recyclerViewComment;
    public VideoAdapter(@NonNull FirebaseRecyclerOptions<VideoItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull VideoViewHolder holder, int position, @NonNull VideoItem model) {
        holder.setVideoData(model);
        String userid = PrefConfig.getUsername();
        String videoKey = getRef(position).getKey();
        holder.getLikeButtonStatus(userid,videoKey);
        likeReference = FirebaseDatabase.getInstance().getReference("Likes");
        commentRef = FirebaseDatabase.getInstance().getReference("Comments");

        holder.likebtn.setOnClickListener(new View.OnClickListener() {
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
        holder.commentSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = holder.commentEdit.getText().toString();
                if(comment.isEmpty()){
                    Toast.makeText(v.getContext(), "Write something", Toast.LENGTH_SHORT).show();
                }
                else {
                    AddComment(holder,videoKey,commentRef, userid,comment);
                }
            }
        });

        loadComment(videoKey);

    }

    private void loadComment(String videoKey) {
        recyclerViewComment.setLayoutManager(new LinearLayoutManager(recyclerViewComment.getContext()));
        commentOption = new FirebaseRecyclerOptions.Builder<Comment>().setQuery(commentRef.child(videoKey),Comment.class).build();
        commentAdaptor = new FirebaseRecyclerAdapter<Comment, CommentViewHolder>(commentOption) {
            @Override
            protected void onBindViewHolder(@NonNull CommentViewHolder holder, int position, @NonNull Comment model) {
                holder.userComment.setText(model.getUserComment());
                holder.usernameComment.setText(model.getUsernameComment());
            }

            @NonNull
            @Override
            public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_comment,parent,false);
                return new CommentViewHolder(view);
            }
        };

        commentAdaptor.startListening();
        recyclerViewComment.setAdapter(commentAdaptor);
    }

    private void AddComment(VideoViewHolder holder, String videoKey, DatabaseReference commentRef, String userid, String comment) {
        HashMap hashMap = new HashMap();
        hashMap.put("comment",comment);
        commentRef.child(videoKey).child(userid).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful())
                {

                }
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
        Button commentSend;
        EditText commentEdit;
        ProgressBar videoProgressBar;
        public TextView likeCountTextview;
        FloatingActionButton likebtn;


        public VideoViewHolder(@NonNull View itemView){
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            textVideoTitle = itemView.findViewById(R.id.textVideoTitle);
            textVideoDescription = itemView.findViewById(R.id.textVideoDescription);
            videoProgressBar = itemView.findViewById(R.id.videoProgressBar);
            likebtn = itemView.findViewById(R.id.likeFloatingBtn);
            likeCountTextview = itemView.findViewById(R.id.likeCount);
            commentEdit = itemView.findViewById(R.id.commentEdit);
            commentSend = itemView.findViewById(R.id.commentSendBtn);
            recyclerViewComment = itemView.findViewById(R.id.commentRecycler);
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



        public void getLikeButtonStatus(String userid, String videoKey) {
            DatabaseReference likeDatabaseReference;
            likeDatabaseReference = FirebaseDatabase.getInstance().getReference("Likes");
            likeDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(videoKey).hasChild(userid)){
                        int likeCount = (int) dataSnapshot.child(videoKey).getChildrenCount();
                        likeCountTextview.setText(""+ likeCount + " Likes");
                        likebtn.setImageResource(R.drawable.ic_baseline_favorite_24);

                    }
                    else{
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


}