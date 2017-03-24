package fx.main;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoadingScreenController {

    @FXML
    private ImageView img;


    @FXML
    public void initialize() {
        Image image = new Image("images/welcome.jpg");
        img.setImage(image);
    }
}