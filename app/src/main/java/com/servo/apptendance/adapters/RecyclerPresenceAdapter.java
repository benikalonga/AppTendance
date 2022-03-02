package com.servo.apptendance.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.apg.mobile.roundtextview.RoundTextView;
import com.servo.apptendance.R;
import com.servo.apptendance.activities.MainActivity;
import com.servo.apptendance.modeles.Agent;
import com.servo.apptendance.modeles.Presence;
import com.servo.apptendance.utils.Log;
import com.servo.apptendance.views.TextViewTeach;

import java.util.ArrayList;

public class RecyclerPresenceAdapter extends RecyclerView.Adapter<RecyclerPresenceAdapter.ItemViewHolder> {

    ArrayList<Presence> presences;
    MainActivity mainActivity;

    public RecyclerPresenceAdapter(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    public RecyclerPresenceAdapter(MainActivity mainActivity,ArrayList<Presence> presences){
        this.presences = presences;
        this.mainActivity = mainActivity;
    }

    public void setPresences(ArrayList<Presence> presences) {
        this.presences = presences;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        ViewGroup itemLayout = (ViewGroup) LayoutInflater.from(mainActivity).inflate(R.layout.precence_item, viewGroup, false);
        return new ItemViewHolder(itemLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Presence presence = presences.get(i);
        Agent agent = presence.getAgent();

        String sex = agent.getSex();
        String genre = sex == null ? "" : sex.contains("M")? "Mr": "Mdme";

        String noms = genre+" "+agent.getPrenom()+" "+agent.getNom();

        itemViewHolder.tvNoms.setText(noms);
        itemViewHolder.tvMatricule.setText(agent.getNumeroCard());
        itemViewHolder.tvDebut.setText(presence.getDebutFormatted());

        if (!presence.getFin().equals(Presence.FIN_VIDE)){
            itemViewHolder.linearFin.setVisibility(View.VISIBLE);
            itemViewHolder.tvFin.setText(presence.getFinFormatted());
        }
    }

    @Override
    public int getItemCount() {
        return presences!= null? presences.size() : 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        public RoundTextView iconPresence, iconDebut, iconFin;
        public TextViewTeach tvNoms, tvMatricule, tvDebut, tvFin;
        public LinearLayout linearFin;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            iconPresence = itemView.findViewById(R.id.iconPresence);
            iconDebut = itemView.findViewById(R.id.iconDebut);
            iconFin = itemView.findViewById(R.id.iconFin);

            tvNoms = itemView.findViewById(R.id.textNoms);
            tvMatricule = itemView.findViewById(R.id.textMatricule);
            tvDebut = itemView.findViewById(R.id.textDebut);
            tvFin = itemView.findViewById(R.id.textFin);

            linearFin = itemView.findViewById(R.id.linearFin);

        }
    }
}
