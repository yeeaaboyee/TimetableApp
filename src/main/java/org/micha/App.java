package org.micha;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;


/**
 * JavaFX App
 */
public class App extends Application {
    private final Pane v1 = new Pane();
    private final Pane v2 = new Pane();
    private final Pane v3 = new Pane();
    private final Pane v4 = new Pane();
    private final Pane v5 = new Pane();
    private final Pane v6 = new Pane();
    private final Pane v7 = new Pane();
    private final Pane v8 = new Pane();

    @Override
    public void start(Stage stage) {
            Data data = new Data();
            data.getData();
            Button newClassButton = new Button("New Class");
            newClassButton.setOnAction(value -> openNewClassWindow(data));
            Button editClassButton = new Button("Edit Class");
            editClassButton.setOnAction(value -> openEditClassSelectionWindow(data));
            Button deleteClassButton = new Button("Delete Class");
            deleteClassButton.setOnAction(value -> openDeleteClassWindow(data));

            refreshVisualData(data);

            HBox hbox = new HBox(v1, v2, v3, v4, v5, v6, v7, v8);
            var scene = new Scene(new VBox(hbox, newClassButton, editClassButton, deleteClassButton));
            stage.setTitle("TimeTable Plus");
            stage.setScene(scene);
            stage.show();
    }

    private void openNewClassWindow(Data data) {
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
            refreshVisualData(data);
        });
        Scene scene = new Scene(new VBox(nameText, nameField, lecturerText, lecturerField, typeText, typeField, dayText, dayField, timeText, timeField, lengthText, lengthField, notesText, notesField, addButton));
        newClassStage.setScene(scene);
        newClassStage.show();
    }

    private void openEditClassWindow(int id, Data data) {
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
            refreshVisualData(data);
        });
        Scene scene = new Scene(new VBox(nameText, nameField, lecturerText, lecturerField, typeText, typeField, dayText, dayField, timeText, timeField, lengthText, lengthField, notesText, notesField, editButton));
        editClassStage.setScene(scene);
        editClassStage.show();
    }

    private void openEditClassSelectionWindow(Data data) {
        VBox theVBox = new VBox();
        Stage editClassSelectionStage = new Stage();
        for (int i = 0; i < data.getData().size(); i++) {
            Class tempClass = data.getData().get(i);
            Button button = new Button(
                    tempClass.getName() + ", " +
                    tempClass.getLecturer() + ", " +
                    tempClass.getType() + ", " +
                    tempClass.getDay() + ", " +
                    tempClass.getTime() + "-" +
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
            Button button = new Button(
                            data.getData().get(i).getName() + ", " +
                            data.getData().get(i).getLecturer() + ", " +
                            data.getData().get(i).getType() + ", " +
                            data.getData().get(i).getDay() + ", " +
                            data.getData().get(i).getTime() + "-" +
                            data.getData().get(i).getLength() + ", " +
                            data.getData().get(i).getNotes()
            );
            int finalI = i;
            button.setOnAction(value -> {
                data.deleteClass(finalI);
                refreshVisualData(data);
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

    private void openClassContextMenu(Data data, int index) {
        VBox theVBox = new VBox();
        Stage contextStage = new Stage();
        Label classLabel = new Label(data.getData().get(index).getName() +
                "\n" + data.getData().get(index).getLecturer() +
                "\n" + data.getData().get(index).getTime());
        Button editButton = new Button("Edit");
        editButton.setOnAction(actionEvent -> {
            openEditClassWindow(index, data);
            contextStage.close();
        });
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(actionEvent -> {
            data.deleteClass(index);
            refreshVisualData(data);
            contextStage.close();

        });
        theVBox.getChildren().add(classLabel);
        theVBox.getChildren().add(editButton);
        theVBox.getChildren().add(deleteButton);
        contextStage.setTitle("Class Context Menu");
        theVBox.setMinWidth(275);
        contextStage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(theVBox);
        contextStage.setScene(scene);
        contextStage.show();
    }

    private double timeToCoordinates(String theTime) {
        int partOne = (Integer.parseInt(theTime.substring(0, 2)) - 8) * 100;
        double timeInt = (Integer.parseInt(theTime.substring(3)) / 0.6) + partOne;
        return ((timeInt / 1100) * 550) + 75;
    }

    public void refreshVisualData(Data data) {
        v2.getChildren().clear();
        v3.getChildren().clear();
        v4.getChildren().clear();
        v5.getChildren().clear();
        v6.getChildren().clear();
        v7.getChildren().clear();
        v8.getChildren().clear();
        try {
            ClassLoader classloader = this.getClass().getClassLoader();
            FileInputStream input1 = new FileInputStream(Objects.requireNonNull(classloader.getResource("timetable_bg1.png")).getFile());
            Image image1 = new Image(input1);
            FileInputStream input2 = new FileInputStream(Objects.requireNonNull(classloader.getResource("timetable_bg2.png")).getFile());
            Image image2 = new Image(input2);
            FileInputStream input3 = new FileInputStream(Objects.requireNonNull(classloader.getResource("timetable_bg3.png")).getFile());
            Image image3 = new Image(input3);
            FileInputStream input4 = new FileInputStream(Objects.requireNonNull(classloader.getResource("timetable_bg4.png")).getFile());
            Image image4 = new Image(input4);
            FileInputStream input5 = new FileInputStream(Objects.requireNonNull(classloader.getResource("timetable_bg5.png")).getFile());
            Image image5 = new Image(input5);
            FileInputStream input6 = new FileInputStream(Objects.requireNonNull(classloader.getResource("timetable_bg6.png")).getFile());
            Image image6 = new Image(input6);
            FileInputStream input7 = new FileInputStream(Objects.requireNonNull(classloader.getResource("timetable_bg7.png")).getFile());
            Image image7 = new Image(input7);
            FileInputStream input8 = new FileInputStream(Objects.requireNonNull(classloader.getResource("timetable_bg8.png")).getFile());
            Image image8 = new Image(input8);

            ImageView imageView1 = new ImageView(image1);
            v1.getChildren().add(imageView1);
            ImageView imageView2 = new ImageView(image2);
            v2.getChildren().add(imageView2);
            ImageView imageView3 = new ImageView(image3);
            v3.getChildren().add(imageView3);
            ImageView imageView4 = new ImageView(image4);
            v4.getChildren().add(imageView4);
            ImageView imageView5 = new ImageView(image5);
            v5.getChildren().add(imageView5);
            ImageView imageView6 = new ImageView(image6);
            v6.getChildren().add(imageView6);
            ImageView imageView7 = new ImageView(image7);
            v7.getChildren().add(imageView7);
            ImageView imageView8 = new ImageView(image8);
            v8.getChildren().add(imageView8);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < data.getData().size(); i++) {
            switch (data.getData().get(i).getDay()) {
                case "Monday": {
                    Rectangle temp = new Rectangle();
                    Label tempLabel = new Label(data.getData().get(i).getName() + "\n" + data.getData().get(i).getLecturer());
                    temp.setY(timeToCoordinates(data.getData().get(i).getTime()));
                    tempLabel.setTranslateY(timeToCoordinates(data.getData().get(i).getTime()) + 10);
                    temp.setX(1);
                    tempLabel.setTranslateX(38);
                    tempLabel.setTextAlignment(TextAlignment.CENTER);
                    temp.setWidth(140);
                    temp.setHeight(timeToCoordinates(data.getData().get(i).getLength()) - temp.getY()); //49

                    temp.setFill(Color.valueOf("#97C5FF"));
                    temp.setStroke(Color.BLACK);
                    int finalI = i;
                    temp.setOnMouseClicked(mouseEvent -> openClassContextMenu(data, finalI));
                    v2.getChildren().add(temp);
                    v2.getChildren().add(tempLabel);
                    temp.toFront();
                    tempLabel.toFront();
                    break;
                }
                case "Tuesday": {
                    Rectangle temp = new Rectangle();
                    Label tempLabel = new Label(data.getData().get(i).getName() + "\n" + data.getData().get(i).getLecturer());
                    temp.setY(timeToCoordinates(data.getData().get(i).getTime()));
                    tempLabel.setTranslateY(timeToCoordinates(data.getData().get(i).getTime()) + 10);
                    temp.setX(1);
                    tempLabel.setTranslateX(38);
                    tempLabel.setTextAlignment(TextAlignment.CENTER);
                    temp.setWidth(140);
                    temp.setHeight(timeToCoordinates(data.getData().get(i).getLength()) - temp.getY()); //49

                    temp.setFill(Color.valueOf("#97C5FF"));
                    temp.setStroke(Color.BLACK);
                    int finalI = i;
                    temp.setOnMouseClicked(mouseEvent -> openClassContextMenu(data, finalI));
                    v3.getChildren().add(temp);
                    v3.getChildren().add(tempLabel);
                    temp.toFront();
                    tempLabel.toFront();
                    break;
                }
                case "Wednesday": {
                    Rectangle temp = new Rectangle();
                    Label tempLabel = new Label(data.getData().get(i).getName() + "\n" + data.getData().get(i).getLecturer());
                    temp.setY(timeToCoordinates(data.getData().get(i).getTime()));
                    tempLabel.setTranslateY(timeToCoordinates(data.getData().get(i).getTime()) + 10);
                    temp.setX(1);
                    tempLabel.setTranslateX(38);
                    tempLabel.setTextAlignment(TextAlignment.CENTER);
                    temp.setWidth(140);
                    temp.setHeight(timeToCoordinates(data.getData().get(i).getLength()) - temp.getY()); //49

                    temp.setFill(Color.valueOf("#97C5FF"));
                    temp.setStroke(Color.BLACK);
                    int finalI = i;
                    temp.setOnMouseClicked(mouseEvent -> openClassContextMenu(data, finalI));
                    v4.getChildren().add(temp);
                    v4.getChildren().add(tempLabel);
                    temp.toFront();
                    tempLabel.toFront();
                    break;
                }
                case "Thursday": {
                    Rectangle temp = new Rectangle();
                    Label tempLabel = new Label(data.getData().get(i).getName() + "\n" + data.getData().get(i).getLecturer());
                    temp.setY(timeToCoordinates(data.getData().get(i).getTime()));
                    tempLabel.setTranslateY(timeToCoordinates(data.getData().get(i).getTime()) + 10);
                    temp.setX(1);
                    tempLabel.setTranslateX(38);
                    tempLabel.setTextAlignment(TextAlignment.CENTER);
                    temp.setWidth(140);
                    temp.setHeight(timeToCoordinates(data.getData().get(i).getLength()) - temp.getY()); //49

                    temp.setFill(Color.valueOf("#97C5FF"));
                    temp.setStroke(Color.BLACK);
                    int finalI = i;
                    temp.setOnMouseClicked(mouseEvent -> openClassContextMenu(data, finalI));
                    v5.getChildren().add(temp);
                    v5.getChildren().add(tempLabel);
                    temp.toFront();
                    tempLabel.toFront();
                    break;
                }
                case "Friday": {
                    Rectangle temp = new Rectangle();
                    Label tempLabel = new Label(data.getData().get(i).getName() + "\n" + data.getData().get(i).getLecturer());
                    temp.setY(timeToCoordinates(data.getData().get(i).getTime()));
                    tempLabel.setTranslateY(timeToCoordinates(data.getData().get(i).getTime()) + 10);
                    temp.setX(1);
                    tempLabel.setTranslateX(38);
                    tempLabel.setTextAlignment(TextAlignment.CENTER);
                    temp.setWidth(140);
                    temp.setHeight(timeToCoordinates(data.getData().get(i).getLength()) - temp.getY()); //49

                    temp.setFill(Color.valueOf("#97C5FF"));
                    temp.setStroke(Color.BLACK);
                    int finalI = i;
                    temp.setOnMouseClicked(mouseEvent -> openClassContextMenu(data, finalI));
                    v6.getChildren().add(temp);
                    v6.getChildren().add(tempLabel);
                    temp.toFront();
                    tempLabel.toFront();
                    break;
                }
                case "Saturday": {
                    Rectangle temp = new Rectangle();
                    Label tempLabel = new Label(data.getData().get(i).getName() + "\n" + data.getData().get(i).getLecturer());
                    temp.setY(timeToCoordinates(data.getData().get(i).getTime()));
                    tempLabel.setTranslateY(timeToCoordinates(data.getData().get(i).getTime()) + 10);
                    temp.setX(1);
                    tempLabel.setTranslateX(38);
                    tempLabel.setTextAlignment(TextAlignment.CENTER);
                    temp.setWidth(140);
                    temp.setHeight(timeToCoordinates(data.getData().get(i).getLength()) - temp.getY()); //49

                    temp.setFill(Color.valueOf("#97C5FF"));
                    temp.setStroke(Color.BLACK);
                    int finalI = i;
                    temp.setOnMouseClicked(mouseEvent -> openClassContextMenu(data, finalI));
                    v7.getChildren().add(temp);
                    v7.getChildren().add(tempLabel);
                    temp.toFront();
                    tempLabel.toFront();
                    break;
                }
                case "Sunday": {
                    Rectangle temp = new Rectangle();
                    Label tempLabel = new Label(data.getData().get(i).getName() + "\n" + data.getData().get(i).getLecturer());
                    temp.setY(timeToCoordinates(data.getData().get(i).getTime()));
                    tempLabel.setTranslateY(timeToCoordinates(data.getData().get(i).getTime()) + 10);
                    temp.setX(1);
                    tempLabel.setTranslateX(38);
                    tempLabel.setTextAlignment(TextAlignment.CENTER);
                    temp.setWidth(140);
                    temp.setHeight(timeToCoordinates(data.getData().get(i).getLength()) - temp.getY()); //49

                    temp.setFill(Color.valueOf("#97C5FF"));
                    temp.setStroke(Color.BLACK);
                    int finalI = i;
                    temp.setOnMouseClicked(mouseEvent -> openClassContextMenu(data, finalI));
                    v8.getChildren().add(temp);
                    v8.getChildren().add(tempLabel);
                    temp.toFront();
                    tempLabel.toFront();
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}