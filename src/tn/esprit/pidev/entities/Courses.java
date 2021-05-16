package tn.esprit.pidev.entities;

import java.util.Comparator;
import java.util.Locale;

public class Courses {
    private int id;
    private String titre;
    private String contenu;
    private String duree;
    private String code;
    private String videoURL;
    private String pdfURL;
    private String imageURL;

    public Courses() {
    }

    public Courses(String titre, String contenu, String duree, String code, String videoURL, String pdfURL, String imageURL) {
        this.titre = titre;
        this.contenu = contenu;
        this.duree = duree;
        this.code = code;
        this.videoURL = videoURL;
        this.pdfURL = pdfURL;
        this.imageURL = imageURL;
    }

    public Courses(int id, String titre, String contenu, String duree, String code, String videoURL, String pdfURL, String imageURL) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.duree = duree;
        this.code = code;
        this.videoURL = videoURL;
        this.pdfURL = pdfURL;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getPdfURL() {
        return pdfURL;
    }

    public void setPdfURL(String pdfURL) {
        this.pdfURL = pdfURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public static Comparator<Courses> titleComparator = new Comparator<Courses>() {
        @Override
        public int compare(Courses o1, Courses o2) {
            return (int) (o1.getTitre().toLowerCase().compareTo(o2.getTitre().toLowerCase()));
        }
    };
}
