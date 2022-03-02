package com.servo.apptendance.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.anthonycr.grant.PermissionsManager;
import com.apg.mobile.roundtextview.RoundTextView;
import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.FadeInAnimation;
import com.easyandroidanimations.library.FadeOutAnimation;
import com.easyandroidanimations.library.ParallelAnimator;
import com.easyandroidanimations.library.ScaleInAnimation;
import com.easyandroidanimations.library.ScaleOutAnimation;
import com.easyandroidanimations.library.SlideInAnimation;
import com.marozzi.roundbutton.RoundButton;
import com.servo.apptendance.R;
import com.servo.apptendance.adapters.AdapterFragItemMain;
import com.servo.apptendance.fragments.FragItemMain;
import com.servo.apptendance.interfaces.GeneralInterface;
import com.servo.apptendance.modeles.Agent;
import com.servo.apptendance.modeles.Presence;
import com.servo.apptendance.sqlite.BddManager;
import com.servo.apptendance.utils.Constants;
import com.servo.apptendance.utils.Keys;
import com.servo.apptendance.utils.Log;
import com.servo.apptendance.utils.Plus;
import com.servo.apptendance.utils.Utils;
import com.servo.apptendance.views.FancyButtonBK;
import com.servo.apptendance.views.FancyLayoutBK;
import com.servo.apptendance.views.TextViewTeach;

import org.json.JSONObject;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;
import me.kaelaela.verticalviewpager.VerticalViewPager;
import me.kaelaela.verticalviewpager.transforms.DefaultTransformer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Resources res;

    CoordinatorLayout mainCoordinator;
    VerticalViewPager verticalViewPager;
    AdapterFragItemMain adapterFragItemMain;

    NavigationTabBar navigationTabBar;

    ImageView viewCircuitIntegre;
    LinearLayout linearLogo;
    FrameLayout frameState, frameInit;

    RoundButton roundButtonInit;
    LinearLayout linearTextInit;
    TextViewTeach tvCount, tvCountMax, tvInit;

    FancyButtonBK fcyReglages, fcyBackSpace;
    FancyLayoutBK fancyLayoutState;

    RoundTextView textNotification;

    BddManager<Agent> agentBddManager;
    BddManager<Presence> presenceBddManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initComponents();
        verifiPermissions();
        initView();
    }
    void initComponents(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        res = getResources();

    }
    void verifiPermissions(){
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, null);
    }
    void initView(){
        setContentView(R.layout.activity_main);

        mainCoordinator  = findViewById(R.id.mainCoordinator);
        verticalViewPager  = findViewById(R.id.verticalViewPager);
        navigationTabBar   = findViewById(R.id.ntb);
        viewCircuitIntegre = findViewById(R.id.viewCircuitIntegre);
        linearLogo = findViewById(R.id.linearLogo);
        frameInit = findViewById(R.id.frameInit);
        frameState = findViewById(R.id.frameState);
        roundButtonInit = findViewById(R.id.roundBouton);
        linearTextInit = findViewById(R.id.linearTextInit);
        tvCount = findViewById(R.id.textCount);
        tvCountMax = findViewById(R.id.textCountMax);
        tvInit = findViewById(R.id.textInit);
        fcyBackSpace = findViewById(R.id.fcyBackSpace);
        fcyReglages = findViewById(R.id.fcyReglages);
        fancyLayoutState = findViewById(R.id.fancyLayoutState);

        textNotification = findViewById(R.id.textNotificationPresence);

        mainCoordinator.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                viewCircuitIntegre.getLayoutParams().height = Utils.getScreenHeight(MainActivity.this)/2;
                frameState.postDelayed(()->{
                    initAgents();
                    initPresences();
                },3000);

                //For notification
                int height = textNotification.getLayoutParams().height/2;
                int topMargin = (Utils.getScreenHeight(MainActivity.this)/8)-height;
                ((ViewGroup.MarginLayoutParams)textNotification.getLayoutParams()).topMargin = topMargin;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mainCoordinator.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }else{
                    mainCoordinator.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });

        initVerticalViewPager();
        initNavigationTabBar();
        initClickCallback();
    }

    void initVerticalViewPager(){

        adapterFragItemMain = new AdapterFragItemMain.Holder(getSupportFragmentManager())
                .add(FragItemMain.newInstance(0))/*Liste*/
                .add(FragItemMain.newInstance(1))/*QrCode*/
                .add(FragItemMain.newInstance(2))/*Finger*/
                .add(FragItemMain.newInstance(3))/*Matricule*/
                .set();

        verticalViewPager.setAdapter(adapterFragItemMain);
        verticalViewPager.setPageTransformer(true, new DefaultTransformer());

        verticalViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        verticalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    void initNavigationTabBar(){
        ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
            String[] colors = res.getStringArray(R.array.colorMenu);

            models.add(createModel(R.string.liste_titre,R.drawable.ic_liste_icone, Color.parseColor(colors[0])));
            models.add(createModel(R.string.qrcode_titre,R.drawable.ic_qr_icone, Color.parseColor(colors[1])));
            models.add(createModel(R.string.fingerprint_titre,R.drawable.ic_finger_print_icone, Color.parseColor(colors[2])));
            models.add(createModel(R.string.matricule_titre,R.drawable.ic_matricule, Color.parseColor(colors[3])));

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(verticalViewPager, Keys.indexListePresence);

    }
    private NavigationTabBar.Model createModel(@StringRes int titre, @DrawableRes int icon, int color){
        return  new NavigationTabBar
                    .Model
                    .Builder(ContextCompat.getDrawable(this,icon), color)
                    .selectedIcon(ContextCompat.getDrawable(this,icon))
                    .title(res.getString(titre))
                    .build();
    }

    private void initAgents(){
         initBddAgents();

        if (agentBddManager.getCount()>0){
            hideInitLayout();
            updateAgents();
        }
        else {
            toogleInitState();
        }
    }
    private void initBddAgents(){
        //si les agents existent deja
        if (agentBddManager == null)
            agentBddManager = new BddManager<>(this, new Agent());
    }
    private void reloadInitAgent(){
        try {
            roundButtonInit.revertAnimation();
        } catch (Exception e) {
        }
        linearTextInit.setVisibility(View.GONE);
        tvInit.setText(R.string.init_init);
        initAgents();
    }
    private void initPresences(){
        if (presenceBddManager == null){
            presenceBddManager = new BddManager<>(this, new Presence());
        }
    }

    public BddManager<Agent> getAgentBddManager() {
        initAgents();
        return this.agentBddManager;
    }
    public BddManager<Presence> getPresenceBddManager(){
        initPresences();
        return this.presenceBddManager;
    }

    private void hideInitLayout(){
        new FadeOutAnimation(frameState)
                .setListener(animation ->
                {
                    frameState.setVisibility(View.GONE);
                    Plus.setStatusbarColorRes(MainActivity.this,R.color.colorPrimaryWhatsapp);
                    new ParallelAnimator()
                            .add(new FadeInAnimation(viewCircuitIntegre))
                            .add(new SlideInAnimation(viewCircuitIntegre).setDirection(Animation.DIRECTION_UP))
                            .setInterpolator(new DecelerateInterpolator())
                            .animate();
                }).animate();
    }

    //si les agents n existent pas
    private void toogleInitState(){
        //change background color
        frameState.setBackgroundColor(Color.WHITE);
        new FadeOutAnimation(linearLogo)
                .setListener(animation ->linearLogo.setVisibility(View.GONE))
                .animate();


        new FadeInAnimation(frameInit)
                .setListener(animation -> {
                    roundButtonInit.setEnabled(true);
                    roundButtonInit.postDelayed(()->{
                        roundButtonInit.startAnimation();
                    },200);
                    requestAgentFromServer();
                })
                .animate();
    }
    private void requestAgentFromServer(){
        new Thread(()->{
            Utils.sendOnServer(MainActivity.this, Request.Method.GET, Constants.URL_AGENTS, Agent.class.getSimpleName(), null,
                    response ->
                            Utils.decodeJsonElement(response, (GeneralInterface<JSONObject>) t -> {
                                if ( t!= null ){
                                    tvInit.post(()->{
                                        tvInit.setText(R.string.init_maj);
                                        tvCountMax.setText("/ "+t.length);
                                        linearTextInit.setVisibility(View.VISIBLE);
                                    });

                                    for (int i = 0; i<t.length ; i++){
                                        long idInserted = agentBddManager.insertModel(Agent.fromJSON(t[i]));
                                        if (idInserted>0) {
                                            int finalI = i;
                                            tvCount.post(()->{
                                              tvCount.setText((idInserted==1? "Ajouté ": "Ajoutés ") + (finalI +1)+" ");
                                            });
                                        }
                                    }
                                    frameState.post(()->{
                                        hideInitLayout();
                                    });
                                }else {
                                    tvInit.post(()->{
                                        roundButtonInit.setResultState(RoundButton.ResultState.FAILURE);
                                        tvInit.setText("Une erreur s\'est produite.\nTouchez pour réessayer");
                                    });
                                }
                    }), error -> {
                        roundButtonInit.post(()->{
                            try {
                                roundButtonInit.setResultState(RoundButton.ResultState.FAILURE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            tvInit.setText("Echec de connexion.\nTouchez ici pour réessayer");
                        });
                        Log.i("Error "+error.getMessage());
                    });
        }).start();
    }
    private void updateAgents(){
        new Thread(()->{
            Utils.sendOnServer(MainActivity.this, Request.Method.GET, Constants.URL_AGENTS, Agent.class.getSimpleName(), null,
                    response ->
                            Utils.decodeJsonElement(response, (GeneralInterface<JSONObject>) t -> {
                                if ( t!= null ){

                                    for (int i = 0; i<t.length ; i++){

                                        Agent agent = agentBddManager.getModel(Agent.NUMERO_MAT, Agent.fromJSON(t[i]).getNumeroMat());
                                        if ( agent == null){
                                            agentBddManager.insertModel(Agent.fromJSON(t[i]));
                                        }
                                        else {
                                            Agent agentNew = Agent.fromJSON(t[i]);
                                            agentNew.setId(agent.getId());
                                            agentBddManager.update(agentNew);
                                        }
                                    }
                                }
                            }), error -> {
                                Log.i("Error Update "+error.getMessage());
                            });
        }).start();
    }

    @Override
    public void onBackPressed() {
        if (verticalViewPager.getCurrentItem()!= Keys.indexListePresence)
            verticalViewPager.setCurrentItem(Keys.indexListePresence, true);
        else
          super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    void initClickCallback(){
        fcyReglages.setOnClickListener(this);
        fcyBackSpace.setOnClickListener(this);
        fancyLayoutState.setOnClickListener(this);
    }

    public void reload() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fcyBackSpace:{
                super.onBackPressed();
                break;
            }
            case R.id.fcyReglages:{
                //startActivity(new Intent(this, Settings.));
                break;
            }
            case R.id.fancyLayoutState:{
                reloadInitAgent();
                break;
            }

        }
    }
    public void onClickView(View v){
        getCurrentFragment().onClickView(v);
    }
    private void startActivityForResult(Class activity, int reqCode){
        Intent intent = new Intent(this, activity);
        startActivityForResult(intent, reqCode);
    }

    public FragItemMain getCurrentFragment(){
        return ((FragItemMain)adapterFragItemMain.getItem(verticalViewPager.getCurrentItem()));
    }
    public void notifyAdd(){
        textNotification.setText(presenceBddManager.getCount()+"");
        hideNotification();
        textNotification.postDelayed(()->
                new ScaleInAnimation(textNotification)
                .setInterpolator(new OvershootInterpolator())
                .setDuration(Animation.DURATION_SHORT)
                .animate(),Animation.DURATION_DEFAULT);

    }
    public void hideNotification(){
        if (textNotification.getVisibility() == View.VISIBLE)
            new ScaleOutAnimation(textNotification)
                    .setInterpolator(new AnticipateInterpolator())
                    .setDuration(Animation.DURATION_SHORT)
                    .animate();
    }

}
