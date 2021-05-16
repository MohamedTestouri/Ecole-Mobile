package tn.esprit.pidev.views;

import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import tn.esprit.pidev.entities.Formation;
import tn.esprit.pidev.services.FormationService;
import tn.esprit.pidev.utils.Database;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;

public class FormationForm extends Form{
    Form current;
    FormationService formationService = new FormationService();
    ArrayList<Formation> formationArrayList = new ArrayList<>();
    public FormationForm(Form previous) {
        /* *** *CONFIG SCREEN* *** */
        current = this;
        setTitle("Formation List");
        setLayout(BoxLayout.y());
        /* *** *YOUR CODE GOES HERE* *** */
        formationArrayList = formationService.showAll();
        Collections.reverse(formationArrayList);
        showFormationList();
        /* *** *SEARCHBAR* *** */
        getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                // clear search
                for (Component cmp : getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                getContentPane().animateLayout(150);
            } else {
                text = text.toLowerCase();
                for (Component cmp : getContentPane()) {
                    MultiButton mb = (MultiButton) cmp;
                    String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1 ||
                            line2 != null && line2.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);

                }
                getContentPane().animateLayout(150);
            }
        }, 4);
        /* *** *OVERFLOW MENU* *** */
        getToolbar().addCommandToOverflowMenu("Sort by Name", null, (evt)->{
            removeAll();
            Collections.sort(formationArrayList, Formation.titleComparator);
            showFormationList();
        });
        /* *** *SIDE MENU* *** */
        getToolbar().addCommandToLeftSideMenu("", null, (evt) -> {        });
        getToolbar().addCommandToLeftSideMenu("Home", null, (evt) -> {            new HomeScreen().show();        });
        getToolbar().addCommandToLeftSideMenu("Formation", null, (evt) -> {new FormationForm(current).show();        });

    }
    public void showFormationList(){
        for (Formation formation : formationArrayList) {
            int deviceWidth = Display.getInstance().getDisplayWidth();
            Image placeholder = Image.createImage(deviceWidth / 3, deviceWidth / 4, 0xbfc9d2);
            EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);
            Image image = URLImage.createToStorage(encImage, formation.getTitre() + formation.getId(), Database.UPLOAD_IMAGE+formation.getFileName()+".jpeg", URLImage.RESIZE_SCALE);
            MultiButton multiButton = new MultiButton();
            multiButton.setTextLine1(formation.getTitre());
            multiButton.setTextLine2("Duree: "+formation.getDuree()+"mins");
            multiButton.setTextLine3("Prix: "+formation.getPrix()+"€");
          //  multiButton.setTextLine("Prix: "+formation.getPrix()+"€");
            multiButton.setIcon(image);
            multiButton.setEmblem(FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT, "", 10.0f));
            multiButton.setUIID(formation.getId() + "");
            multiButton.addActionListener(l -> new CoursesForm(current, formation).show());
            add(multiButton);
        }
    }
}
