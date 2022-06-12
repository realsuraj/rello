package com.tiwari.suraj.rello;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SecondFragment() {
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
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
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
         View v = inflater.inflate(R.layout.fragment_second, container, false);
        final ViewPager2 videoViewPager = v.findViewById(R.id.videosViewPager);
        
        List<VideoItem> videoItems = new ArrayList<>();
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
         return v;
    }
}