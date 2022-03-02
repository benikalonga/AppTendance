package com.servo.apptendance.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.servo.apptendance.interfaces.GeneralInterface;
import com.servo.apptendance.modeles.Agent;
import com.servo.apptendance.modeles.Presence;
import com.servo.apptendance.sqlite.BddManager;

public class Engine {

   public static void checkData(BddManager<Agent> agentBddManager, String DATA_TYPE, String data, GeneralInterface<Agent> agentGeneralInterface){
       new Thread(() -> {
           agentGeneralInterface.onDone(agentBddManager.getModel(DATA_TYPE, data));
           agentBddManager.close();
       }).start();
   }
   public static void getAgentsFromServer(Context context, GeneralInterface<Object> result){
       new Thread(()->{
           Utils.sendOnServer(context, Request.Method.GET, Constants.URL_AGENTS, null, null, new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {
                    Log.i(response);
               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                   result.onDone(Constants.ECHEC, error.getMessage());
               }
           });
       }).start();
   }
   public static void addAgentInPresence(Agent agent, BddManager<Presence> presenceBddManager, GeneralInterface<Presence> result){
       new Thread(()->{

           //Get la presence de l agent
           Presence presence = presenceBddManager.getModel(Presence.AGENT_MATRICULE, agent.getNumeroMat());

           //Si n existe pas, on cree;
           if (presence == null){
               presence = new Presence();
               presence.setAgentMatricule(agent.getNumeroMat());
               presence.setDebut(Utils.getActualDate());
               presence.setFin(Presence.FIN_VIDE);
               presence.setAgent(agent);

               if (presenceBddManager.insertModel(presence)>0){
                   result.onDone(presence);
               }else {
                   result.onDone(null);
               }
           /*Sinon on ajoute la fin */
           }else {
               presence.setFin(Utils.getActualDate());
               presenceBddManager.update(presence);
           }
       }).start();
   }
}
