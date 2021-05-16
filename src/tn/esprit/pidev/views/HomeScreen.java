package tn.esprit.pidev.views;

import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.Container;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import tn.esprit.pidev.entities.Formation;

import java.awt.*;

public class HomeScreen extends Form {
    Form current;
    public HomeScreen(){
        /* *** *CONFIG SCREEN* *** */
        current = this;
        setTitle("WELCOME");
        setLayout(BoxLayout.y());
        /* *** *YOUR CODE GOES HERE* *** */

        add(new Label("Choose an option from side menu"));
        /* *** *SIDE MENU* *** */
        getToolbar().addCommandToLeftSideMenu("", null, (evt) -> {        });
        getToolbar().addCommandToLeftSideMenu("Home", null, (evt) -> {            new HomeScreen().show();        });
        getToolbar().addCommandToLeftSideMenu("Formation", null, (evt) -> {new FormationForm(current).show();        });

    }
}
