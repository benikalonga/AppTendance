package com.servo.apptendance.modeles;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Amunaso Tyty on 26/10/2018.
 */

public class Presence extends ModelAbstract<Presence> {

    //clefs
    public static String ID_PRESENCE = "idPresences", DEBUT = "debut", FIN = "fin", AGENT_MATRICULE ="Agents_NumeroMat", ENVOYER="EnvoyerAuServer" , FIN_VIDE = "---";
    public static String ENVOYER_AU_SERVER = "ENVOYER_AU_SERVER", NON_ENVOYER_AU_SERVER = "NON_ENVOYER_AU_SERVER";

    //
    private String idPresence , debut , fin, agentMatricule, debutFormatted, finFormatted, envoyerAuServer;
    private int id;
    private Agent agent;

    public Presence() {
    }

    @Override
    public Presence fromCursor(Cursor cursor) {

        try {
            Presence presence = new Presence();
            presence.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
            presence.setIdPresence(cursor.getString(cursor.getColumnIndex(ID_PRESENCE)));
            presence.setDebut(cursor.getString(cursor.getColumnIndex(DEBUT)));
            presence.setFin(cursor.getString(cursor.getColumnIndex(FIN)));
            presence.setAgentMatricule(cursor.getString(cursor.getColumnIndex(AGENT_MATRICULE)));
            presence.setEnvoyerAuServer(cursor.getString(cursor.getColumnIndex(ENVOYER)));

            return presence;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Presence fromJSON(JSONObject jsonObject){
        try {
            Presence presence = new Presence();

            return presence;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdPresence() {
        return idPresence;
    }

    public void setIdPresence(String idPresence) {
        this.idPresence = idPresence;
    }

    public String getDebut() {
        return debut;
    }

    public void setDebut(String debut) {
        this.debut = debut;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getAgentMatricule() {
        return agentMatricule;
    }

    public void setAgentMatricule(String agentMatricule) {
        this.agentMatricule = agentMatricule;
    }

    public String getDebutFormatted() {
        return debutFormatted;
    }

    public void setDebutFormatted(String debutFormatted) {
        this.debutFormatted = debutFormatted;
    }

    public String getFinFormatted() {
        return finFormatted;
    }

    public void setFinFormatted(String finFormatted) {
        this.finFormatted = finFormatted;
    }

    public Agent getAgent() {
        return agent;
    }

    public String getEnvoyerAuServer() {
        return envoyerAuServer;
    }

    public void setEnvoyerAuServer(String envoyerAuServer) {
        this.envoyerAuServer = envoyerAuServer;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    @Override
    public ArrayList<String[]> getKeysType() {
        ArrayList<String[]> keysType = new ArrayList<>();

        keysType.add(STRING_TO_TAB(_ID, TYPE_INTEGER+CLEF_PRIMAIRE+AUTOINCREMENT));
        keysType.add(STRING_TO_TAB(ID_PRESENCE, TYPE_TEXT));
        keysType.add(STRING_TO_TAB(DEBUT, TYPE_TEXT));
        keysType.add(STRING_TO_TAB(FIN, TYPE_TEXT));
        keysType.add(STRING_TO_TAB(AGENT_MATRICULE, TYPE_TEXT));
        keysType.add(STRING_TO_TAB(ENVOYER, TYPE_TEXT));

        return keysType;
    }

    @Override
    public String TABLE_NAME() {
        return Presence.class.getSimpleName();
    }

    @Override
    public String BDD_NAME() {
        return Presence.class.getSimpleName();
    }

    @Override
    public ContentValues getContentValues() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_PRESENCE, getIdPresence() );
        contentValues.put(DEBUT, getDebut() );
        contentValues.put(FIN, getFin());
        contentValues.put(AGENT_MATRICULE, getAgentMatricule());
        contentValues.put(ENVOYER, getEnvoyerAuServer());

        return contentValues;
    }

    @Override
    public String[] getColumnNames() {
        return new String[]{_ID,ID_PRESENCE,DEBUT, FIN,AGENT_MATRICULE, ENVOYER};
    }
}
