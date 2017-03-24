package fx.main;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by kamilek on 02/03/2017.
 */

public class Images {
    private Image img;
    private ImageView i;

    public Image getSpeakerFull() {
        img = new Image("icons/sound.png");
        return img;
    }

    public Image getSpeakerLow() {
        img = new Image("icons/sound_low.png");
        return img;
    }

    public Image getSpeakerMute() {
        img = new Image("icons/sound_mute.png");
        return img;
    }

    public ImageView getConfirmIcon() {
        ImageView i = new ImageView("icons/confirm2.png");
        return i;
    }

    public ImageView getCancelIcon() {
        i = new ImageView("icons/cancel.png");
        return i;
    }
}
