package fx.main;


import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.util.Comparator.comparing;

public class Controller {
    @FXML
    private TableView<Member> memTable;
    @FXML
    private TableColumn<Member, Integer> id;

    @FXML
    private TableColumn<Member, String> name;

    @FXML
    private TableColumn<Member, String> surname;
    @FXML
    private ImageView soundOff;
    @FXML
    private ScrollPane parent;
    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private Label label5;

    @FXML
    private Label label6;

    @FXML
    private Label label7;

    @FXML
    private Label label8;

    @FXML
    private Label label9;

    @FXML
    private TextField searchUserField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surField;

    @FXML
    private DatePicker dobField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField postField;

    @FXML
    private TextField phoneField;

    @FXML
    private ImageView imgField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField countryField;


    private int count;


    @FXML
    private TextField emailField;
    private Member mem;
    private Users users;
    private DBConnection db;
    private Connection conn;
    private boolean found = false;
    private SQL sql;
    private ArrayList<TextField> textFields = new ArrayList<>();
    private ArrayList<Label> labels = new ArrayList<>();
    private UiSound sound;
    private Images images;
    private Dialogs a;





    public Controller() {
        sql = new SQL();
        sound = new UiSound();
        count = 3;
        sound.setLoudness(0.5);
        images = new Images();
        a = new Dialogs();


    }

    @FXML
    public void initialize() {
        if (!Main.isSplasheLoaded) {
            loadingScreen();
        } else {
            loadDatabase();
            filterTable();
            phoneNumbersValidator();
            textFieldsArray();
            labelsArray();
            imgField.setImage(getDefaultImage());
            Tooltip.install(imgField, new Tooltip("Click image to change it..."));
            javax.swing.ToolTipManager.sharedInstance().setInitialDelay(0);
            Image s = new Image("icons/sound_low.png");
            soundOff.setImage(s);
            Main.us.setAsStageDraggable(Main.ps,parent.getContent().lookup("#mb"));
        }

    }

    private boolean checkEmptyFields() {
        for (TextField t : textFields) {
            if (t.getText().isEmpty() || dobField.getEditor().getText().isEmpty()) {
                System.out.println("fields empty");
                return true;
            }
        }
        return false;
    }

    private void labelsArray() {
        labels.add(label1);
        labels.add(label2);
        labels.add(label3);
        labels.add(label4);
        labels.add(label5);
        labels.add(label6);
        labels.add(label7);
        labels.add(label8);
        labels.add(label9);
    }

    private void phoneNumbersValidator() {
        phoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                phoneField.setText(oldValue);
            }
        });
    }

    private Image getDefaultImage() {
        return new Image("images/avatar2.png");
    }

    private void textFieldsArray() {
        textFields.add(nameField);
        textFields.add(surField);
        textFields.add(cityField);
        textFields.add(postField);
        textFields.add(phoneField);
        textFields.add(streetField);
        textFields.add(countryField);
        textFields.add(emailField);

        for (TextField t : textFields) {
            t.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!t.getText().isEmpty()) {
                    for (Label l : labels) {
                        if (!l.isVisible()) {
                            l.setVisible(true);
                        }
                    }
                } else {
                    for (Label l : labels) {
                        if (l.isVisible()) {
                            l.setVisible(false);
                        }
                    }
                }
            });
        }
    }

    private void filterTable() {

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Member> filteredData = new FilteredList<>(users.getMemberData(), m -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchUserField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(member -> {
            // If filter text is empty, display all members.
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            // Compare first name and last name of every person with filter text.
            String lowerCaseFilter = newValue.toLowerCase();

            if (member.getName().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filter matches first name.
            } else if (member.getSurname().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filter matches last name.
            }
            return false; // Does not match.
        }));
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Member> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(memTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        memTable.setItems(sortedData);
    }


    public void resetForm() {
        for (TextField t : textFields) {
            t.clear();
        }
        dobField.setValue(null);
        imgField.setImage(getDefaultImage());
        memTable.getSelectionModel().clearSelection();

    }

    public void tableListener() {
        if (!memTable.getSelectionModel().isEmpty()) {
            sound.playSoundChoice(5);
            mem = memTable.getSelectionModel().getSelectedItem();
            nameField.setText(mem.getName());
            surField.setText(mem.getSurname());
            if (mem.getDob() != null) {
                dobField.setValue(mem.getDob().toLocalDate());
                System.out.println(mem.getDob().toLocalDate());
            } else dobField.getEditor().clear();
            streetField.setText(mem.getStreet());
            cityField.setText(mem.getCity());
            postField.setText(mem.getPostcode());
            countryField.setText((mem.getCountry()));
            phoneField.setText(mem.getPhone().toString());
            emailField.setText(mem.getEmail());


            if (mem.getProfile() != null && new File(mem.getProfile()).isFile()) {
                Image image = new Image("file:///" + mem.getProfile());
                imgField.setImage(image);
            } else if (mem.getProfile() == null) {
                imgField.setImage(getDefaultImage());
            } else if (!new File(mem.getProfile()).isFile()) {
                imgField.setImage(getDefaultImage());
            }
        }
    }

    private void loadDatabase() {

        System.out.println("Database loading");
        try {

            org.h2.tools.Server.createTcpServer().start();// start database engine
            users = new Users();
            users.retrieveData();
            populateTable();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateTable() {

        memTable.setItems(users.getMemberData());
        id.setCellValueFactory(
                new PropertyValueFactory<>("Id"));
        name.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        surname.setCellValueFactory(
                new PropertyValueFactory<>("surname"));
    }

    public void exit() {
        Platform.exit();
        System.exit(0);
    }


    private boolean search(int i) {

        if (i == 2) {
            Member memData = memTable.getSelectionModel().getSelectedItem();
            mem = new Member(memData.getId(), nameField.getText(), surField.getText(), memData.getProfile(), java.sql.Date.valueOf(dobField.getValue()), cityField.getText(), postField.getText(), streetField.getText(), countryField.getText(), Integer.valueOf(phoneField.getText()),
                    emailField.getText());

            for (Member member : users.getMemberData()) {
                if (member.getName().equalsIgnoreCase(mem.getName()) && member.getSurname().equalsIgnoreCase(mem
                        .getSurname()) && mem.getDob().equals(member.getDob()) && mem.getCity().equals(member
                        .getCity()) && mem.getPostcode().equals(member.getPostcode()) && mem.getStreet().equals
                        (member.getStreet()) && mem.getCountry().equals(member.getCountry()) && mem.getPhone().equals
                        (member.getPhone()) && mem.getEmail().equals(member.getEmail())) {
                    System.out.println("contains");
                    return found = true;
                }

            }
        }
        if (i == 1) {
            mem = new Member(getMaxID(), nameField.getText(), surField.getText(), null, null, null, null, null, null, null, null);

            for (Member member : users.getMemberData()) {
                if (member.getName().equalsIgnoreCase(mem.getName()) && member.getSurname().equalsIgnoreCase(mem
                        .getSurname())) {
                    return found = true;


                }
            }
        }
        return found = false;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private Integer getMaxID() {
        if (!memTable.getItems().isEmpty()) {
            Member maxID = users.getMemberData().stream().max(comparing(Member::getId)).get();
            System.out.println(maxID.getId());
            Integer max = Integer.valueOf(String.valueOf(maxID.getId()));
            return max + 1;
        }

        return 1;
    }

    public void changeProfile() {
        if (memTable.getSelectionModel().getSelectedItem() != null) {
            ProfilePicture prof = new ProfilePicture();
            if (prof.getPath() != null) {
                try {
                    db = new DBConnection();
                    conn = db.getConn();
                    PreparedStatement ps = conn.prepareStatement(sql.getAddPicturePath());
                    ps.setString(1, prof.getPath().toString());
                    ps.setInt(2, memTable.getSelectionModel().getSelectedItem().getId());

                    ps.executeUpdate();
                    ps.close();

                    String message = "Profile picture successfully updated";
                    sound.playSoundChoice(4);

                    a.showNotification("", "", images.getConfirmIcon(), message);

                    resetForm();
                    loadDatabase();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void addNew() {
        System.out.println(dobField.getValue());
        search(1);
        System.out.println(found);
        if (!found && !checkEmptyFields() && !checkEmptyFields()) {
            try {
                System.out.println("trying to add");
                db = new DBConnection();
                conn = db.getConn();
                PreparedStatement ps = conn.prepareStatement(sql.getAddSql());
                ps.setInt(1, getMaxID());
                ps.setString(2, StringUtils.capitalize(nameField.getText()));
                ps.setString(3, StringUtils.capitalize(surField.getText()));
                ps.setObject(4, java.sql.Date.valueOf(dobField.getValue()));
                ps.setString(5, StringUtils.capitalize(streetField.getText()));
                ps.setString(6, StringUtils.capitalize(cityField.getText()));
                ps.setString(7, postField.getText().toUpperCase());
                ps.setString(8, StringUtils.capitalize(countryField.getText()));
                ps.setString(9, phoneField.getText());
                ps.setString(10, emailField.getText());
                ps.setString(11, "null");

                ps.executeUpdate();
                ps.close();

                String message = "Record successfully added:";
                sound.playSoundChoice(4);

                a.showNotification(nameField.getText(), surField.getText(), images.getConfirmIcon(), message);
                loadDatabase();
                resetForm();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("User already exists or fields empty");
            sound.playSoundChoice(3);
            a.showWarningAlert();


        }

    }


    public void delRec() {
        search(1);
        if (found) {
            System.out.println("deleting record");
            found = false;
            sound.playSoundChoice(1);
            String mess = "Are you sure to delete selected record ";
            int go = a.showConfirmAlert(nameField.getText(), surField.getText(), mess);
            if (go == 1) {
                sound.playSoundChoice(2);
                try {
                    db = new DBConnection();
                    conn = db.getConn();
                    PreparedStatement ps = conn.prepareStatement(sql.getDelSql());
                    ps.setString(1, nameField.getText());
                    ps.setString(2, surField.getText());
                    ps.executeUpdate();
                    ps.close();
                    loadDatabase();
                    resetForm();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else System.out.println("Canceled");
        } else {
            System.out.println("Cannot delete or not found in database");
            String message = "Cannot delete or not found in database:";
            sound.playSoundChoice(3);
            a.showNotification("", "", images.getCancelIcon(), message);

        }
    }

    public void updateRec() {

        System.out.println("updating method");
        if (!memTable.getSelectionModel().isEmpty() || !checkEmptyFields()) {
            if (!search(2)) {
                int dbId = memTable.getSelectionModel().getSelectedItem().getId();

                try {
                    System.out.println("updating record");
                    db = new DBConnection();
                    conn = db.getConn();
                    PreparedStatement ps = conn.prepareStatement(sql.getUpdSql());
                    ps.setString(1, StringUtils.capitalize(nameField.getText()));
                    ps.setString(2, StringUtils.capitalize(surField.getText()));
                    ps.setObject(3, java.sql.Date.valueOf(dobField.getValue()));
                    ps.setString(4, StringUtils.capitalize(streetField.getText()));
                    ps.setString(5, StringUtils.capitalize(cityField.getText()));
                    ps.setString(6, postField.getText().toUpperCase());
                    ps.setString(7, StringUtils.capitalize(countryField.getText()));
                    ps.setString(8, phoneField.getText());
                    ps.setString(9, emailField.getText());
                    ps.setInt(10, dbId);

                    ps.executeUpdate();
                    ps.close();


                    String message = "Record successfully Updated: ";
                    sound.playSoundChoice(4);

                    a.showNotification(nameField.getText(), surField.getText(), images.getConfirmIcon(), message);

                    loadDatabase();
                    resetForm();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                String m = "identical record already exists";
                sound.playSoundChoice(3);
                a.showNotification("", "", images.getCancelIcon(), m);
                System.out.println("identical record already exists");
            }
        } else {
            String m = "Cannot update";
            sound.playSoundChoice(3);
            a.showNotification("", "", images.getCancelIcon(), m);
            System.out.println("Cannot update");
        }
    }


    public void turnSound() {
        Images images = new Images();
        System.out.println(count + " count");
        if (count == 1) {
            soundOff.setImage(images.getSpeakerFull());
            sound.setLoudness(1.0);
            sound.playSoundChoice(5);
            System.out.println("here1");
            count++;
        } else if (count == 2) {
            soundOff.setImage(images.getSpeakerLow());
            sound.setLoudness(0.5);
            sound.playSoundChoice(5);
            System.out.println("here2");
            count++;
        } else if (count == 3) {
            soundOff.setImage(images.getSpeakerMute());
            sound.setLoudness(0.0);
            sound.playSoundChoice(5);
            System.out.println("here3");
            count = 1;
        }


    }

    public void playClickSound() {
        sound.playSoundChoice(6);
    }

    private void loadingScreen() {

        try {
            Main.isSplasheLoaded = true;
            StackPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("LoadingScreen.fxml"));
            parent.setContent(pane);
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);
            fadeIn.play();

            fadeIn.setOnFinished(e -> {

                fadeOut.setFromValue(1);
                fadeOut.setToValue(0);
                fadeOut.setCycleCount(1);
                fadeOut.play();


            });


            fadeOut.setOnFinished(e -> {
                try {
                    Parent root = FXMLLoader.load(Controller.this.getClass().getClassLoader().getResource("sample.fxml"));
                    parent.setContent(root);


                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
