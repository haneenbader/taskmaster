package com.example.taskmaster;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskItemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "body";
    private static final String ARG_PARAM3= "state";
    private static final String ARG_PARAM4= "lat";
    private static final String ARG_PARAM5= "lon";


    // TODO: Rename and change types of parameters
    private String mTitle;
    private String mBody;
    private String mState;
    private  String mLat;
    private  String mLon;


    public TaskItemFragment() {
        // Required empty public constructor
    }
// Factory method : static method return object of type class


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mTitle Parameter 1.
     * @param mBody Parameter 2.
     * @param mState Parameter 3.
     * @param mLat Parameter 4.
     * @param mLon Parameter 5.
     * @return A new instance of fragment TaskFragmentItem.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskItemFragment newInstance(String mTitle, String mBody, String mState) {
        TaskItemFragment fragment = new TaskItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, mTitle);
        args.putString(ARG_PARAM2, mBody);
        args.putString(ARG_PARAM3, mState);
        args.putString(ARG_PARAM4, fragment.mLat);
        args.putString(ARG_PARAM5, fragment.mLon);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_PARAM1);
            mBody = getArguments().getString(ARG_PARAM2);
            mState = getArguments().getString(ARG_PARAM3);
            mLat = getArguments().getString(ARG_PARAM4);
            mLon = getArguments().getString(ARG_PARAM5);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_item, container, false);
    }
}