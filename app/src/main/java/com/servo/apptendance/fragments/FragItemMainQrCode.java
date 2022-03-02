package com.servo.apptendance.fragments;

import android.animation.Animator;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.apg.mobile.roundtextview.RoundLayout;
import com.easyandroidanimations.library.FadeOutAnimation;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;
import com.journeyapps.barcodescanner.ViewfinderView;
import com.marozzi.roundbutton.RoundButton;
import com.servo.apptendance.R;
import com.servo.apptendance.activities.MainActivity;
import com.servo.apptendance.interfaces.GeneralInterface;
import com.servo.apptendance.modeles.Agent;
import com.servo.apptendance.modeles.Presence;
import com.servo.apptendance.sqlite.BddManager;
import com.servo.apptendance.utils.Engine;
import com.servo.apptendance.utils.Log;
import com.servo.apptendance.utils.Plus;
import com.servo.apptendance.views.FancyButtonBK;
import com.servo.apptendance.views.TextViewTeach;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class FragItemMainQrCode extends FragmentDetail implements View.OnClickListener, BarcodeCallback{


    MainActivity mainActivity;

    //Views
    View viewGroup;
    FancyButtonBK fcyPlayPause;

    CardView cardState;
    RoundButton rountCheck;
    TextViewTeach tvNoms, tvMatricule, tvCheck;
    ImageView ivQrCodeAgent;
    RelativeLayout relativeResult;

    boolean isScanning;

    private DecoratedBarcodeView barcodeView;
    private BeepManager beepManager;
    private String lastText;

    BddManager<Presence> presenceBddManager;
    BddManager<Agent> agentBddManager;

    private FragItemMainQrCode() {

    }
    public static FragItemMainQrCode newInstance() {

        FragItemMainQrCode fragment = new FragItemMainQrCode();
        return fragment;
    }

    @Override
    public void onCreateView(View viewGroup) {

        this.viewGroup = viewGroup;
        mainActivity = (MainActivity)viewGroup.getContext();

        fcyPlayPause = viewGroup.findViewById(R.id.fcyPlayPause);
        barcodeView = viewGroup.findViewById(R.id.barcode_scanner);

        rountCheck = viewGroup.findViewById(R.id.roundCheck);
        tvCheck = viewGroup.findViewById(R.id.tvCheck);
        tvNoms = viewGroup.findViewById(R.id.tvNoms);
        tvMatricule = viewGroup.findViewById(R.id.tvMatricule);
        ivQrCodeAgent = viewGroup.findViewById(R.id.ivQrAgent);
        relativeResult = viewGroup.findViewById(R.id.relativeResult);

        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39);
        barcodeView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats,null,null));
        barcodeView.initializeFromIntent(mainActivity.getIntent());
        barcodeView.decodeContinuous(this);
        barcodeView.getStatusView().setVisibility(View.GONE);

        beepManager = new BeepManager(mainActivity);
        beepManager.setVibrateEnabled(true);
        beepManager.setBeepEnabled(true);

        cardState = viewGroup.findViewById(R.id.cardState);

        viewGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                ViewfinderView viewfinderView = barcodeView.getViewFinder();
                viewfinderView.getLayoutParams().width = viewfinderView.getHeight();
                ((FrameLayout.LayoutParams)viewfinderView.getLayoutParams()).gravity = Gravity.CENTER;


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewGroup.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }else{
                    viewGroup.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

            }
        });

    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        fcyPlayPause.setOnClickListener(this);
        presenceBddManager = mainActivity.getPresenceBddManager();
        agentBddManager = mainActivity.getAgentBddManager();
   }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fcyPlayPause:{
                if (isScanning){
                    stopScan();
                }
                else {
                    startScan();
                }
                break;
            }
        }

    }

    @Override
    public void onClickView(View view) {

    }

    @Override
    public void isVisible(boolean isVisible) {

        if (isVisible){
            startScan();
        }
        else {
           stopScan();
        }
    }
    private void startScan(){
        if (!isScanning) {
            barcodeView.resume();
            isScanning = true;
            fcyPlayPause.setText("Pause");
        }
    }
    private void stopScan(){
        barcodeView.pause();
        isScanning = false;
        fcyPlayPause.setText("Scanner");
    }

    @Override
    public void refreshContent() {

    }

    @Override
    public void onPause() {
        stopScan();
    }

    @Override
    public void onResume() {
        startScan();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    private void showResult(){

    }

    @Override
    public void barcodeResult(BarcodeResult result) {
        if(result.getText() == null || result.getText().equals(lastText)) {
            // Si le text scanner est le meme que le dernier text
            return;
        }

        lastText = result.getText();
        barcodeView.setStatusText(result.getText());

        beepManager.playBeepSoundAndVibrate();

        tvCheck.setText(R.string.check_verification);
        relativeResult.setVisibility(View.GONE);

        Plus.animateToScaledSize(barcodeView,0.9f,0.9f,null);

        cardState.setScaleX(0.9f);
        cardState.setScaleY(0.9f);

        Plus.animateToNormalSize(cardState,new Plus.AnimationListener(){
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Engine.checkData(agentBddManager, Agent.NUMERO_CARD, lastText,
                        (GeneralInterface<Agent>) t -> {

                            //Si OK le CodeQR correspond a un agent
                            if (t!=null && t[0] != null){

                                rountCheck.post(()->{
                                    try {
                                        rountCheck.setResultState(RoundButton.ResultState.SUCCESS);
                                    } catch (Exception e) {
                                    }
                                    Agent agent = t[0];

                                    String sex = agent.getSex();
                                    String genre = sex == null ? "" : sex.contains("M")? "Mr": "Mdme";

                                    String noms = genre+" "+agent.getPrenom()+" "+agent.getNom();

                                    tvNoms.setText(noms);
                                    tvMatricule.setText(agent.getNumeroCard());

                                    tvCheck.setText("code valide!");
                                    relativeResult.setVisibility(View.VISIBLE);
                                });

                                //on ajoute une presence
                                Engine.addAgentInPresence(/*Agent*/t[0], presenceBddManager, (GeneralInterface<Presence>) t1 -> {
                                    if (t1 !=null && t1[0]!=null){
                                        rountCheck.post(()->{
                                            hideCardState();
                                        });
                                    }else{
                                        rountCheck.post(()->{
                                            tvCheck.setText("Erreur! l'opération à échouée");
                                            try {
                                                rountCheck.revertAnimation();
                                            } catch (Exception e) {
                                            }
                                            rountCheck.postDelayed(()->{
                                                rountCheck.setResultState(RoundButton.ResultState.FAILURE);
                                                rountCheck.postDelayed(()->{
                                                    hideCardState();
                                                },1000);
                                            },200);
                                        });
                                    }
                                });
                                /*Sinon show CodeQR non valide*/
                            }else {
                                cardState.post(()->{
                                    hideCardState();
                                    Snackbar.make(fcyPlayPause, "Ce code n\'est pas valide",Snackbar.LENGTH_SHORT).show();
                                });
                            }

                        });

                // Added preview of scanned barcode
                // ImageView imageView = (ImageView) findViewById(R.id.barcodePreview);
                // imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW));
            }
        });

    }
    private void hideCardState(){
        new FadeOutAnimation(cardState)
                .setListener((a)->{
                    cardState.setVisibility(View.GONE);
                })
                .animate();
    }

    @Override
    public void possibleResultPoints(List<ResultPoint> resultPoints) {

    }
}
