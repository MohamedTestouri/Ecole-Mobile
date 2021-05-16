package tn.esprit.pidev.entities;

import java.util.Comparator;

public class Formation {
    private int id;
    private String titre;
    private String description;
    private String type;
    private String duree;
    private String prix;
    private String etat;
    private String code;
    private String fileName;

    public Formation() {
    }

    public Formation(int id, String titre, String description, String type, String duree, String prix, String etat, String code, String fileName) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.type = type;
        this.duree = duree;
        this.prix = prix;
        this.etat = etat;
        this.code = code;
        this.fileName = fileName;
    }

    public Formation(String titre, String description, String type, String duree, String prix, String etat, String code, String fileName) {
        this.titre = titre;
        this.description = description;
        this.type = type;
        this.duree = duree;
        this.prix = prix;
        this.etat = etat;
        this.code = code;
        this.fileName = fileName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public static Comparator<Formation> titleComparator = new Comparator<Formation>() {
        @Override
        public int compare(Formation o1, Formation o2) {
            return (int) (o1.getTitre().toLowerCase().compareTo(o2.getTitre().toLowerCase()));
        }
    };
}
