package fx.main;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

/**
 * Created by kamilek on 02/03/2017.
 */
public class Dialogs {
private Stage primaryStage;
    public Dialogs() {
    }

    public  void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public int showConfirmAlert(String n, String s, String m) {
        int result = 0;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, m
                + n + " " + s + " ?", ButtonType.YES, ButtonType.NO);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getClassLoader().getResource("dialog.css").toExternalForm());
        setDialogIcon(dialogPane);
        alert.initOwner(primaryStage);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            result = 1;
        }
        return result;
    }

    private void setDialogIcon(DialogPane d) {
        Stage stage = (Stage) d.getScene().getWindow();
        stage.getIcons().add(new Image("icons/People.png"));
    }

    public void showNotification(String n, String s, ImageView i, String m) {
        Notifications.create().darkStyle().position(Pos.TOP_CENTER).text(m +
                " " + n + " " + s).graphic(i).show();
    }

    public void showWarningAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning ");
        alert.setHeaderText("Something went wrong...");
        alert.setContentText("Please check all the details");
        DialogPane dialogPane = alert.getDialogPane();
        setDialogIcon(dialogPane);
        dialogPane.getStylesheets().add(
                getClass().getClassLoader().getResource("dialog.css").toExternalForm());
        alert.initOwner(primaryStage);
        alert.showAndWait();


    }
}
