package tn.esprit.pidev.views;

import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.*;
import tn.esprit.pidev.entities.Courses;
import tn.esprit.pidev.entities.Formation;
import tn.esprit.pidev.services.CourseService;
import tn.esprit.pidev.utils.Database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class CoursesForm extends Form {
    Form current;
    CourseService courseService = new CourseService();
    ArrayList<Courses> coursesArrayList = new ArrayList<>();

    public CoursesForm(Form previous, Formation formation) {
        /* *** *CONFIG SCREEN* *** */
        current = this;
        setTitle("Courses List");
        setLayout(BoxLayout.y());
        setScrollableY(false);
        /* *** *YOUR CODE GOES HERE* *** */
        /* *SHOW FORMATION DETAILS* */
        /* SHOW IMAGE */
        int deviceWidth = Display.getInstance().getDisplayWidth();
        com.codename1.ui.Image placeholder = com.codename1.ui.Image.createImage(deviceWidth, deviceWidth, 0xbfc9d2);
        EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);
        Image image = URLImage.createToStorage(encImage, formation.getTitre() + formation.getId(), Database.UPLOAD_IMAGE + formation.getFileName() + ".jpeg", URLImage.RESIZE_SCALE);
        ImageViewer imageViewer = new ImageViewer();
        imageViewer.setImage(image.fill(deviceWidth, deviceWidth / 2));
        imageViewer.getStyle().setMarginBottom(16);
        /* CREATING ITEMS DETAILS */
        Label dureeLabel = new Label("Durée: " + formation.getDuree() + "mins");
        Label priceLabel = new Label("Prix: " + formation.getPrix() + "€");
        priceLabel.getStyle().setPaddingRight(4);
        Container formationTextContainer = new Container(new BorderLayout());
        formationTextContainer.add(BorderLayout.WEST, dureeLabel);
        formationTextContainer.add(BorderLayout.EAST, priceLabel);
        Label titreLabel = new Label("Formation: " + formation.getTitre());
        SpanLabel descriptionLabel = new SpanLabel("Description: " + formation.getDescription());
        Container formationDetailsContainer = BoxLayout.encloseY();
        formationDetailsContainer.addAll(imageViewer, titreLabel, formationTextContainer, descriptionLabel);
        formationDetailsContainer.setScrollableY(false);

        /* *SHOW COURSES LIST* */
        coursesArrayList = courseService.showAll();
        Collections.reverse(coursesArrayList);
        Container scrollableListContainer = BoxLayout.encloseY();
        showCourses(scrollableListContainer);
        scrollableListContainer.setScrollableY(true);
        scrollableListContainer.getStyle().setMarginTop(16);
        /* ADDING ALL ITEMS */
        addAll(formationDetailsContainer, scrollableListContainer);
        /* *** *OVERFLOW MENU* *** */
        getToolbar().addCommandToOverflowMenu("Sort by Name", null, (evt) -> {
            scrollableListContainer.removeAll();
            Collections.sort(coursesArrayList, Courses.titleComparator);
            showCourses(scrollableListContainer);
        });
        /* *** *BACK BUTTON* *** */
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        /* *** *SEARCHBAR* *** */
        getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                // clear search
                for (Component cmp : scrollableListContainer) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                scrollableListContainer.animateLayout(150);
            } else {
                text = text.toLowerCase();
                for (Component cmp : scrollableListContainer) {
                    MultiButton mb = (MultiButton) cmp;
                    String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1 ||
                            line2 != null && line2.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);

                }
                scrollableListContainer.animateLayout(150);
            }
        }, 4);
    }

    public void showCourses(Container scrollableListContainer) {
        for (Courses courses : coursesArrayList) {
            int deviceWidth = Display.getInstance().getDisplayWidth();
            Image placeholder = Image.createImage(deviceWidth / 3, deviceWidth / 4, 0xbfc9d2);
            EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);
            Image image = URLImage.createToStorage(encImage, courses.getTitre() + courses.getId(), Database.UPLOAD_IMAGE + courses.getImageURL() + ".jpeg", URLImage.RESIZE_SCALE);
            MultiButton multiButton = new MultiButton();
            multiButton.setTextLine1(courses.getTitre());
            multiButton.setTextLine2(courses.getDuree());
            multiButton.setIcon(image);
            multiButton.setUIID(courses.getId() + "");
            multiButton.setEmblem(FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_RIGHT, "", 10.0f));
            multiButton.addActionListener(l -> new CourseDetailForm(current, courses).show());
            scrollableListContainer.add(multiButton);
        }
    }
}
