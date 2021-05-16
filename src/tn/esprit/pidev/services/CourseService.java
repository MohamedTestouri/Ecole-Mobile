package tn.esprit.pidev.services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import tn.esprit.pidev.entities.Courses;
import tn.esprit.pidev.utils.Database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourseService {
    public ArrayList<Courses> coursesArrayList;

    public static CourseService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public CourseService() {
        req = new ConnectionRequest();
    }

    public static CourseService getInstance() {
        if (instance == null) {
            instance = new CourseService();
        }
        return instance;
    }

    public ArrayList<Courses> parseCourses(String jsonText) {
        try {
            coursesArrayList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> coursesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) coursesListJson.get("root");
            for (Map<String, Object> obj : list) {
                Courses courses = new Courses();
                courses.setCode(obj.get("Code").toString());
                courses.setContenu(obj.get("Contenu").toString());
                courses.setPdfURL(obj.get("filenamePDF").toString());
                courses.setDuree(obj.get("Duree").toString());
                courses.setVideoURL(obj.get("filenameVIDEO").toString());
                courses.setImageURL(obj.get("imageC").toString());
                courses.setTitre(obj.get("Titre").toString());
                courses.setId((int) Float.parseFloat(obj.get("id").toString()));
                coursesArrayList.add(courses);
            }
        } catch (IOException ex) {
        }
        return coursesArrayList;
    }

    public ArrayList<Courses> showAll() {
        String url = Database.BASE_URL + "cours/api/"; // Add Symfony URL Here
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                coursesArrayList = parseCourses(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return coursesArrayList;
    }
}
