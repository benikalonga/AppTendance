package com.servo.apptendance.fragments;

import android.content.ContentValues;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;

import com.servo.apptendance.R;
import com.servo.apptendance.activities.MainActivity;
import com.servo.apptendance.adapters.RecyclerPresenceAdapter;
import com.servo.apptendance.modeles.Presence;
import com.servo.apptendance.sqlite.BddListner;
import com.servo.apptendance.sqlite.BddManager;
import com.servo.apptendance.utils.Utils;
import com.servo.apptendance.views.FancyButtonBK;
import com.servo.apptendance.views.RecyclerViewBK;
import com.servo.apptendance.views.TextViewTeach;

import java.util.Calendar;
import java.util.HashMap;

public class FragItemMainListePresence extends FragmentDetail implements View.OnClickListener, BddListner {

    MainActivity mainActivity;

    //Views
    View viewGroup;
    TextViewTeach tvTitre, tvDate;
    FancyButtonBK fcyRecherche;
    FrameLayout frameTitre;
    RecyclerViewBK recyclerViewBK;
    View emptyView;

    BddManager<Presence> presenceBddManager;
    RecyclerPresenceAdapter presenceAdapter;

    String textTitre;

    private FragItemMainListePresence() {
    }

    public static FragItemMainListePresence newInstance() {

        FragItemMainListePresence fragment = new FragItemMainListePresence();
        return fragment;
    }
    @Override
    public void onCreateView(View viewGroup) {
        this.viewGroup = viewGroup;
        this.mainActivity = (MainActivity)viewGroup.getContext();
        this.fcyRecherche = viewGroup.findViewById(R.id.fcyRecherche);
        this.tvDate = viewGroup.findViewById(R.id.tvDate);
        this.tvTitre = viewGroup.findViewById(R.id.tvTitre);
        this.frameTitre = viewGroup.findViewById(R.id.frameTitre);
        this.recyclerViewBK = viewGroup.findViewById(R.id.recylerList);
        this.emptyView = viewGroup.findViewById(R.id.emptyView);
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        tvDate.setText(getDate());
        textTitre = mainActivity.getText(R.string.liste_titre).toString();

        initPresences();
        initRecycler();
   }
   private String getDate(){
        HashMap<String, String> dateElem = Utils.getDateElements(Calendar.getInstance().getTimeInMillis());
        String day = dateElem.get("jour");
        String mois = dateElem.get("mois");
        String annee = dateElem.get("annee");

        mois = Utils.getMoisAbr(mainActivity,Integer.parseInt(mois));

        return day+" "+mois+" "+annee;
   }

   private void initPresences(){
       presenceBddManager = mainActivity.getPresenceBddManager();
       presenceBddManager.setListner(this);
   }

   private void initRecycler(){
       recyclerViewBK.setEmptyView(emptyView);
       recyclerViewBK.setItemAnimator(new DefaultItemAnimator());
       recyclerViewBK.addItemDecoration(new RecyclerViewBK.ItemOffsetDecoration(mainActivity, R.dimen.len_4));
       presenceAdapter = new RecyclerPresenceAdapter(mainActivity, presenceBddManager.getAllmodels());
       recyclerViewBK.setAdapter(presenceAdapter);
       recyclerViewBK.setLayoutManager(new GridLayoutManager(mainActivity,2));

       presenceAdapter.notifyDataSetChanged();
   }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }

    }

    @Override
    public void onClickView(View view) {

    }

    @Override
    public void isVisible(boolean isVisible) {

        if (isVisible){
            tvTitre.setText(presenceBddManager.getCount()>0? textTitre+"("+presenceBddManager.getCount()+")": textTitre);
            mainActivity.hideNotification();
        }
        else ;
    }

    @Override
    public void refreshContent() {
        presenceAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onOpen() {
    }

    @Override
    public void onClose() {
    }

    @Override
    public void onInsert(String tableName, long id, ContentValues values) {
        presenceAdapter.setPresences(presenceBddManager.getAllmodels());
        presenceAdapter.notifyDataSetChanged();
        mainActivity.notifyAdd();
    }

    @Override
    public void onUpdateLine(String tableName, long id, ContentValues values) {
        presenceAdapter.setPresences(presenceBddManager.getAllmodels());
        presenceAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreated(String tableName) {
        presenceAdapter.setPresences(presenceBddManager.getAllmodels());
        presenceAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDeleteAll(String tableName) {
        presenceAdapter.setPresences(presenceBddManager.getAllmodels());
        presenceAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDelete(String tableName, long id) {
        presenceAdapter.setPresences(presenceBddManager.getAllmodels());
        presenceAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdateTable(String tableName) {
        presenceAdapter.setPresences(presenceBddManager.getAllmodels());
        presenceAdapter.notifyDataSetChanged();
    }
}
