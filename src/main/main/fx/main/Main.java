package fx.main;

import insidefx.undecorator.UndecoratorScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    public static boolean isSplasheLoaded = false;
    public static Stage ps;
    public static UndecoratorScene us;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

            ps = primaryStage;
        try {
            //System.out.println(getClass().getResource("main.fxml").getPath());
            new Dialogs().setPrimaryStage(primaryStage);
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
            primaryStage.setTitle("My Contacts");

            primaryStage.getIcons().add(new Image("icons/People.png"));

            setUserAgentStylesheet(STYLESHEET_CASPIAN);
            us = new UndecoratorScene(primaryStage, (ScrollPane)root);

           us.addStylesheet("cssFx.css");
           // us.getUndecorator().addGlassPane(root);


            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.setMinHeight(700);
            primaryStage.setMinWidth(900);
            primaryStage.setMaxHeight(700);
            primaryStage.setMinWidth(900);
            primaryStage.setScene(us);


            primaryStage.toFront();
            primaryStage.show();
            //ScenicView.show(us);

        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setOnCloseRequest(we -> {
            System.out.println("Stage is closing");

            Platform.exit();
            System.exit(0);
        });

    }
}
