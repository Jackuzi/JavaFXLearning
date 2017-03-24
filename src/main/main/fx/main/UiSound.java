package fx.main;

import javafx.scene.media.AudioClip;

/**
 * Created by kamilek on 02/03/2017.
 */
public class UiSound {
    private double loudness;
    private String confirm = "/sounds/confirm.wav";
    private String delete = "/sounds/delete.wav";
    private String error = "/sounds/error.wav";
    private String added = "/sounds/added.wav";
    private String change = "/sounds/switch.wav";
    private String click = "/sounds/click3.wav";


    UiSound() {


    }

    public void playSoundChoice(int choice) {
        switch (choice) {
            case 1:
                playSound(confirm);
                break;
            case 2:
                playSound(delete);
                break;
            case 3:
                playSound(error);
                break;
            case 4:
                playSound(added);
                break;
            case 5:
                playSound(change);
                break;
            case 6:
                playSound(click);
                break;
            default:
                break;
        }
    }

    public void playSound(String s) {

        try {
            AudioClip sound = new AudioClip(getClass().getResource(s).toString());
            System.out.println(getClass().getResource(s).toString());
            //MediaPlayer mediaPlayer = new MediaPlayer(sound);
            //mediaPlayer.setVolume(getLoudness());
            sound.setVolume(getLoudness());
            sound.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getLoudness() {
        return loudness;
    }

    public void setLoudness(double loudness) {
        this.loudness = loudness;
    }

}
