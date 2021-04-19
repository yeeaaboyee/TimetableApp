package org.micha;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Data data = new Data();
            data.getData();
            Button newClassButton = new Button("New Class");
            newClassButton.setOnAction(value -> {
                openNewClassWindow(data);
            });
            Button editClassButton = new Button("Edit Class");
            editClassButton.setOnAction(value -> {
                openEditClassSelectionWindow(data);
            });
            Button deleteClassButton = new Button("Delete Class");
            deleteClassButton.setOnAction(value -> {
                openDeleteClassWindow(data);
            });
            FileInputStream input = new FileInputStream("C:\\Users\\micha\\IdeaProjects\\TimetablePlus\\src\\main\\java\\org\\micha\\timetable bg.png");
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            StackPane theStack = new StackPane(imageView);
            var scene = new Scene(new VBox(theStack, newClassButton, editClassButton, deleteClassButton));
            stage.setTitle("TimeTable Plus");
            stage.setScene(scene);
            stage.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void openNewClassWindow(Data data) {
        Stage newClassStage = new Stage();
        newClassStage.setTitle("New Class...");
        newClassStage.initModality(Modality.APPLICATION_MODAL);
        Label nameText = new Label("Name");
        TextField nameField = new TextField();
        Label lecturerText = new Label("Lecturer");
        TextField lecturerField = new TextField();
        Label typeText = new Label("Type");
        TextField typeField = new TextField();
        Label dayText = new Label("Day");
        ChoiceBox dayField = new ChoiceBox();
        for (String s : Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")) {
            dayField.getItems().add(s);
        }
        Label timeText = new Label("Time");
        TextField timeField = new TextField();
        Label lengthText = new Label("Length");
        TextField lengthField = new TextField();
        Label notesText = new Label("Notes");
        TextField notesField = new TextField();
        Button addButton = new Button("Add");
        addButton.setOnAction(value -> {
            data.addClass(
                    nameField.getText(),
                    lecturerField.getText(),
                    typeField.getText(),
                    (String) dayField.getValue(),
                    timeField.getText(),
                    lengthField.getText(),
                    notesField.getText()
            );
            newClassStage.close();
        });
        Scene scene = new Scene(new VBox(nameText, nameField, lecturerText, lecturerField, typeText, typeField, dayText, dayField, timeText, timeField, lengthText, lengthField, notesText, notesField, addButton));
        newClassStage.setScene(scene);
        newClassStage.show();
    }

    public void openEditClassWindow(int id, Data data) {
        Stage editClassStage = new Stage();
        editClassStage.setTitle("Edit Class...");
        editClassStage.initModality(Modality.APPLICATION_MODAL);
        Label nameText = new Label("Name");
        TextField nameField = new TextField();
        Label lecturerText = new Label("Lecturer");
        TextField lecturerField = new TextField();
        Label typeText = new Label("Type");
        TextField typeField = new TextField();
        Label dayText = new Label("Day");
        ChoiceBox dayField = new ChoiceBox();
        for (String s : Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")) {
            dayField.getItems().add(s);
        }
        Label timeText = new Label("Time");
        TextField timeField = new TextField();
        Label lengthText = new Label("Length");
        TextField lengthField = new TextField();
        Label notesText = new Label("Notes");
        TextField notesField = new TextField();
        Button editButton = new Button("Edit");
        editButton.setOnAction(value -> {
            data.editClass(id, new Class(
                    nameField.getText(),
                    lecturerField.getText(),
                    typeField.getText(),
                    (String) dayField.getValue(),
                    timeField.getText(),
                    lengthField.getText(),
                    notesField.getText()
                    )
            );
            editClassStage.close();
        });
        Scene scene = new Scene(new VBox(nameText, nameField, lecturerText, lecturerField, typeText, typeField, dayText, dayField, timeText, timeField, lengthText, lengthField, notesText, notesField, editButton));
        editClassStage.setScene(scene);
        editClassStage.show();
    }

    public void openEditClassSelectionWindow(Data data) {
        VBox theVBox = new VBox();
        Stage editClassSelectionStage = new Stage();
        for (int i = 0; i < data.getData().size(); i++) {
            Class tempClass = data.getData().get(i);
            Button button = new Button(
                    tempClass.getId() + ", " +
                    tempClass.getName() + ", " +
                    tempClass.getLecturer() + ", " +
                    tempClass.getType() + ", " +
                    tempClass.getDay() + ", " +
                    tempClass.getTime() + ", " +
                    tempClass.getLength() + ", " +
                    tempClass.getNotes()
            );
            int finalI = i;
            button.setOnAction(value -> {
                openEditClassWindow(finalI, data);
                editClassSelectionStage.close();

            });
            theVBox.getChildren().add(button);
        }

        editClassSelectionStage.setTitle("Choose a Class to Edit");
        editClassSelectionStage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(theVBox);
        editClassSelectionStage.setScene(scene);
        editClassSelectionStage.show();
    }

    private void openDeleteClassWindow(Data data) {
        VBox theVBox = new VBox();
        Stage deleteClassStage = new Stage();
        for (int i = 0; i < data.getData().size(); i++) {
            Class tempClass = data.getData().get(i);
            Button button = new Button(
                    tempClass.getId() + ", " +
                            tempClass.getName() + ", " +
                            tempClass.getLecturer() + ", " +
                            tempClass.getType() + ", " +
                            tempClass.getDay() + ", " +
                            tempClass.getTime() + ", " +
                            tempClass.getLength() + ", " +
                            tempClass.getNotes()
            );
            int finalI = i;
            button.setOnAction(value -> {
                data.deleteClass(finalI);
                deleteClassStage.close();

            });
            theVBox.getChildren().add(button);
        }

        deleteClassStage.setTitle("Choose a Class to Delete");
        deleteClassStage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(theVBox);
        deleteClassStage.setScene(scene);
        deleteClassStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}