package tn.esprit.pidev.views;

import com.codename1.components.ImageViewer;
import com.codename1.components.MediaPlayer;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.Util;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import tn.esprit.pidev.entities.Courses;
import tn.esprit.pidev.utils.Database;

import java.io.IOException;
import java.io.InputStream;

public class CourseDetailForm extends Form {
    Form current;

    public CourseDetailForm(Form previous, Courses courses) {
        /* *** *CONFIG SCREEN* *** */
        current = this;
        setTitle("Course Detail");
        setLayout(BoxLayout.y());
        /* *** *YOUR CODE GOES HERE* *** */
        Label titreLabel = new Label("Cours: " + courses.getTitre());
        Label dureeLabel = new Label("Duree: " + courses.getDuree() + "mins");
        SpanLabel contenuLabel = new SpanLabel("Content: -" + courses.getContenu());
        Button downloadPdfButton = new Button("Download Course");
        Button videoButton = new Button("Get Video");
        /* SHOW IMAGE */
        int deviceWidth = Display.getInstance().getDisplayWidth();
        com.codename1.ui.Image placeholder = com.codename1.ui.Image.createImage(deviceWidth, deviceWidth, 0x000000);
        EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);
        Image image = URLImage.createToStorage(encImage, courses.getTitre() + courses.getId(), Database.UPLOAD_IMAGE + courses.getImageURL() + ".jpeg", URLImage.RESIZE_SCALE);
        ImageViewer imageViewer = new ImageViewer();
        imageViewer.setImage(image.fill(deviceWidth, deviceWidth));

/* *** *DISPLAY* *** */
        videoButton.addActionListener(evt -> Display.getInstance().execute(Database.UPLOAD_VIDEO + courses.getVideoURL() + ".mp4"));
        /* *** *PDF* *** */
        downloadPdfButton.addActionListener(e -> {
            FileSystemStorage fileSystemStorage = FileSystemStorage.getInstance();
            String fileName = fileSystemStorage.getAppHomePath() + courses.getPdfURL() + ".pdf";
            if (!fileSystemStorage.exists(fileName)) {
                Util.downloadUrlToFile(Database.UPLOAD_PDF + courses.getPdfURL() + ".pdf", fileName, true);
            }
            Display.getInstance().execute(fileName);
        });
        addAll(imageViewer, titreLabel, dureeLabel, contenuLabel, downloadPdfButton, videoButton);
        /* *** *DISPLAY VIDEO* *** */
        try {
            Media video = MediaManager.createMedia(Database.UPLOAD_VIDEO + courses.getVideoURL() + ".mp4", true);
            MediaPlayer mediaPlayer = new MediaPlayer(video);
            add(mediaPlayer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* *** *BACK BUTTON* *** */
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }
}
