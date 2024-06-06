package com.example.lyos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lyos.CustomAdapters.RankedSongRecycleViewAdapter;
import com.example.lyos.FirebaseHandlers.SongHandler;
import com.example.lyos.Models.Song;
import com.example.lyos.databinding.FragmentTheMostListenedSongsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TheMostListenedSongsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TheMostListenedSongsFragment extends Fragment {

    FragmentTheMostListenedSongsBinding fragmentTheMostListenedSongsBinding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TheMostListenedSongsFragment() {
        // Required empty public constructor
        songArrayList = null;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TheMostListenedSongsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TheMostListenedSongsFragment newInstance(String param1, String param2) {
        TheMostListenedSongsFragment fragment = new TheMostListenedSongsFragment();
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
        fragmentTheMostListenedSongsBinding = FragmentTheMostListenedSongsBinding.inflate(getLayoutInflater());
        return fragmentTheMostListenedSongsBinding.getRoot();
    }
    private ArrayList<Song> songArrayList;
    private RankedSongRecycleViewAdapter adapter;
    SongHandler songHandler = new SongHandler();
    Context context;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        getTopSongs();
    }

    private void getTopSongs() {
        songHandler.getTopSongs(20).addOnCompleteListener(new OnCompleteListener<ArrayList<Song>>() {
            @Override
            public void onComplete(@NonNull Task<ArrayList<Song>> task) {
                if (task.isSuccessful()) {
                    songArrayList = task.getResult();
                    if(songArrayList != null){
                        if(songArrayList.size() > 0){
                            fragmentTheMostListenedSongsBinding.textViewNoSongs.setVisibility(View.GONE);
                            fragmentTheMostListenedSongsBinding.recycleViewItems.setVisibility(View.VISIBLE);

                            adapter = new RankedSongRecycleViewAdapter(context, songArrayList, "listens");
                            fragmentTheMostListenedSongsBinding.recycleViewItems.setAdapter(adapter);
                            fragmentTheMostListenedSongsBinding.recycleViewItems.addItemDecoration(new DividerItemDecoration(context ,DividerItemDecoration.VERTICAL));
                            RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(context);
                            fragmentTheMostListenedSongsBinding.recycleViewItems.setLayoutManager(mLayoutManager);
                            fragmentTheMostListenedSongsBinding.recycleViewItems.setItemAnimator(new DefaultItemAnimator());

                        }
                        else {
                            fragmentTheMostListenedSongsBinding.recycleViewItems.setVisibility(View.GONE);
                            fragmentTheMostListenedSongsBinding.textViewNoSongs.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        fragmentTheMostListenedSongsBinding.recycleViewItems.setVisibility(View.GONE);
                        fragmentTheMostListenedSongsBinding.textViewNoSongs.setVisibility(View.VISIBLE);
                    }
                } else {
                    // Xử lý lỗi
                    Log.e("TopSong", "Error getting top songs", task.getException());
                }
            }
        });
    }
}