package tn.esprit.pidev.services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import tn.esprit.pidev.entities.Formation;
import tn.esprit.pidev.utils.Database;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FormationService {
    public ArrayList<Formation> formationArrayList;

    public static FormationService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public FormationService() {
        req = new ConnectionRequest();
    }

    public static FormationService getInstance() {
        if (instance == null) {
            instance = new FormationService();
        }
        return instance;
    }
    public ArrayList<Formation> parseFormation(String jsonText) {
        try {
            formationArrayList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> formationListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) formationListJson.get("root");
            for (Map<String, Object> obj : list) {
                Formation formation = new Formation();
                formation.setId((int) Float.parseFloat(obj.get("id").toString()));
                formation.setTitre(obj.get("Titre").toString());
                formation.setCode(obj.get("Code").toString());
                formation.setDuree(obj.get("Duree").toString());
                formation.setType(obj.get("Type").toString());
                formation.setPrix(obj.get("Prix").toString());
                formation.setEtat(obj.get("Etat").toString());
                formation.setFileName(obj.get("filename").toString());
                formation.setDescription(obj.get("Description").toString());

                formationArrayList.add(formation);
            }
        } catch (IOException ex) {
        }
        return formationArrayList;
    }

    public ArrayList<Formation> showAll() {
        String url = Database.BASE_URL + "formations/api/"; // Add Symfony URL Here
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                formationArrayList = parseFormation(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return formationArrayList;
    }
}
