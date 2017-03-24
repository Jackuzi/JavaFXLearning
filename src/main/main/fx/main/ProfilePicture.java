package fx.main;

import javafx.stage.FileChooser;

import java.io.File;

/**
 * Created by kamilek on 28/02/2017.
 */


public class ProfilePicture {
    private File path = null;


    ProfilePicture() {

        ChoosePicture();
    }

    public void ChoosePicture() {
        FileChooser chooser = new FileChooser();
       FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif", "jpeg");
        chooser.getExtensionFilters().addAll(filter);
        chooser.setInitialDirectory(new File(System.getProperty(("user.dir"))));
        File retrival = chooser.showOpenDialog(Main.ps);
        if (retrival != null) {
            System.out.println(retrival);
            path = retrival;
        } else path = null;
    }


    public File getPath() {
        return path;
    }
}
