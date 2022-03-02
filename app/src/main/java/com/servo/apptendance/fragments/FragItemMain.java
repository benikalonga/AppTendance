package com.servo.apptendance.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.servo.apptendance.R;
import com.servo.apptendance.utils.Keys;

public class FragItemMain extends Fragment {

    FragmentDetail currentFragment;
    private static String POSITION = "POSITION";
    int position;

    ViewPager viewPager;
    DrawerLayout drawerLayoutMain;

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public void setDrawerLayoutMain(DrawerLayout drawerLayoutMain) {
        this.drawerLayoutMain = drawerLayoutMain;
    }

    public FragItemMain() {
    }

    public static FragItemMain newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        
        FragItemMain fragment = new FragItemMain();
        fragment.setArguments(args);
        
        return fragment;
    }
    public static FragItemMain newInstance(int position, View... views) {
        Bundle args = new Bundle();
        args.putInt(POSITION, position);

        FragItemMain fragment = new FragItemMain();
        fragment.setDrawerLayoutMain((DrawerLayout) views[0]);
        fragment.setViewPager((ViewPager) views[1]);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view ;

        this.position = getArguments()!=null? getArguments().getInt(POSITION): Keys.indexListePresence;
        
        switch (position){
            default:
            case 0: {
                view = inflater.inflate(R.layout.fragment_listepresence, container, false);
                currentFragment = FragItemMainListePresence.newInstance();
                break;
            }
            case 1:{
                view = inflater.inflate(R.layout.fragment_qrcode, container, false);
                currentFragment = FragItemMainQrCode.newInstance();
                break;
            }
            case 2:{
                view = inflater.inflate(R.layout.fragment_fingerprint, container, false);
                currentFragment = FragItemMainListePresence.newInstance();
                break;
            }
            case 3:{
                view = inflater.inflate(R.layout.fragment_matricule, container, false);
                currentFragment = FragItemMainListePresence.newInstance();
                break;
            }
        }
        currentFragment.setActivity(getActivity());
        currentFragment.onCreateView(view);
        return view;
    }

    public boolean onActivityResults(int requestCode, int resultCode, Intent data){
        return currentFragment.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            currentFragment.onViewCreated();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void onClickView(View view){
        currentFragment.onClickView(view);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try {
            currentFragment.isVisible(isVisibleToUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refresh(){
        currentFragment.refreshContent();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            currentFragment.onResume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            currentFragment.onPause();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            currentFragment.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean onBackPressed(){
        return currentFragment.onBackPressed();
    }

    public int getPosition() {
        return position;
    }
}
