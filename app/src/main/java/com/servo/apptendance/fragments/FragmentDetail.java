package com.servo.apptendance.fragments;


import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class FragmentDetail {

    public abstract void isVisible(boolean isVisible);
    public abstract void refreshContent();
    public abstract void onPause();
    public abstract void onResume();
    public abstract void onDestroy();
    public abstract boolean onBackPressed();
    public abstract void onCreateView(View viewGroup);
    public abstract void onClickView(View view);
    public void onViewCreated(){}
    public void startActivityForResult(Activity from, Class to, int reqCode){
        Intent intent = new Intent(from, to);
        from.startActivityForResult(intent, reqCode);
    }
    public boolean onActivityResult(int requestCode, int resultCode, Intent data){
        return false;
    }
    public void setActivity(Activity activity){

    }

}