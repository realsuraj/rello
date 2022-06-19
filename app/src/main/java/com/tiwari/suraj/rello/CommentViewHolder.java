package com.tiwari.suraj.rello;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class CommentViewHolder extends RecyclerView.ViewHolder {

TextView usernameComment, userComment;
    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);
        usernameComment = itemView.findViewById(R.id.usernameComment);
        userComment = itemView.findViewById(R.id.userComment);
    }
}
