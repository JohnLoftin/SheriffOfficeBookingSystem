/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sheriffofficebookingsystem;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author John
 */
public class SheriffOfficeBookingSystem extends Application {
    
    List<Inmate> inmates = new ArrayList<>();   // holds created inmate objects
    List<Visitor> visitors = new ArrayList<>(); // holds created visitor objects
    // used in displaying list with ListView
    ObservableList<Inmate> observeInmates = FXCollections.observableList(inmates);
    ListView listDisplay = new ListView(FXCollections.observableList(inmates));  // used in displaying listed inmates
    // hold values representing available capacity per cell block
    int minSecurity = 400;
    int maxSecurity = 100;
    int isolation = 10;
    int hospital = 50;
    // inmate id starting value - increases by one with each added inmate
    int id = 10000;
    
    @Override
    public void start(Stage primaryStage) {
        
        // Buttons to select function from main menu
        Button book = new Button("Book Inmate");
        book.setPadding(new Insets(5, 5, 5, 5));
        book.setStyle("-fx-font-size: 12pt; -fx-base: green; -fx-text-fill: white;"
                + " -fx-effect: dropshadow(gaussian, black, 5, 0.5, 1, 1);");
        Button list = new Button("List Inmates");
        list.setStyle("-fx-font-size: 12pt; -fx-base: green; -fx-text-fill: white;"
                + " -fx-effect: dropshadow(gaussian, black, 5, 0.5, 1, 1);");
        list.setPadding(new Insets(5, 5, 5, 5));
        
        // HBox holds buttons
        HBox btns = new HBox();
        btns.setSpacing(30);
        btns.setAlignment(Pos.BOTTOM_CENTER);
        btns.getChildren().addAll(book, list);
        
        // center image/main screen of main menu
        StackPane root = new StackPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setStyle(
                "-fx-background-image: url(resources/images/policelogo.jpg);"
                + "-fx-background-repeat: stretch;"
                + "-fx-background-color: white;"
                + "-fx-background-size: 700 650;" 
                + "-fx-background-position: center;");
        root.getChildren().addAll(btns);
        
        book.setOnAction(e -> bookInmate());    // set button action
        list.setOnAction(e -> listInmates());   // set button action
        
        Scene scene = new Scene(root, 900, 800);
        
        primaryStage.setTitle("Sheriff Office Booking System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void bookInmate(){
        
        Stage stage = new Stage();  // create new window
        
        // grid used to hold labels/textfields/comboboxes
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        
        // create label objects
        Label first = new Label("First Name: ");
        Label last = new Label("Last Name: ");
        Label height = new Label("Height: ");
        Label weight = new Label("Weight: ");
        Label ethnicity = new Label("Ethnicity: "); 
        Label status = new Label("Status: "); 
        Label cellB = new Label("Cell Block: "); 
        
        // create textfields/combobox objects
        TextField f = new TextField();
        TextField l = new TextField();
        TextField h = new TextField();
        TextField w = new TextField();
        ComboBox eth = new ComboBox();
        ComboBox s = new ComboBox();
        ComboBox c = new ComboBox();
        
        // add selectable values to comboboxes
        eth.getItems().addAll(
                "African American",
                "Asian",
                "Caucasion",
                "Hispanic",
                "Native American"
        );
        s.getItems().addAll(
                "Booked",
                "Released",
                "Processing"
        );
        c.getItems().addAll(
                "Minimum Security",
                "Maximum Security",
                "Isolation",
                "Hospital"
        );
        
        // place items onto gridpane
        grid.add(first, 0, 0);
        grid.add(f, 1, 0);
        grid.add(last, 2, 0);
        grid.add(l, 3, 0);
        grid.add(height, 0, 1);
        grid.add(h, 1, 1);
        grid.add(weight, 2, 1);
        grid.add(w, 3, 1);
        grid.add(ethnicity, 0, 3);
        grid.add(eth, 1, 3);
        grid.add(status, 2, 3);
        grid.add(s, 3, 3);
        grid.add(cellB, 0, 4);
        grid.add(c, 1, 4);
        
        // button creation for booking screen
        Button cancel = new Button("Cancel");
        cancel.setPadding(new Insets(5, 5, 5, 5));
        cancel.setStyle("-fx-font-size: 12pt; -fx-base: green; -fx-text-fill: white;"
                + " -fx-effect: dropshadow(gaussian, black, 5, 0.5, 1, 1);");
        cancel.setOnAction(e -> stage.close()); // cancel button closes window

        Button submit = new Button("Submit");
        submit.setPadding(new Insets(5, 5, 5, 5));
        submit.setStyle("-fx-font-size: 12pt; -fx-base: green; -fx-text-fill: white;"
                + " -fx-effect: dropshadow(gaussian, black, 5, 0.5, 1, 1);");
        submit.setOnAction(e -> {
            id++;   // increment inmate ID value
            // create new inmate object using data input by user
            Inmate newInmate = new Inmate(f.getText(), l.getText(), h.getText(),
            w.getText(), eth.getValue().toString(), s.getValue().toString(), c.getValue().toString(), id);
            observeInmates.add(newInmate); // add new inmate object to observable list
            // switch statement removes 1 from whichever cell block is chosen
            switch(c.getValue().toString()){
                case "Minimum Security": minSecurity--;break;
                case "Maximum Security": maxSecurity--;break;
                case "Isolation": isolation--;break;
                case "Hospital": hospital--;break;
            }
            stage.close();  // closes window once data submitted/processed
        });
        // HBox holds buttons
        HBox btns = new HBox();
        btns.setSpacing(30);
        btns.setPadding(new Insets(15, 10, 10, 10));
        btns.setAlignment(Pos.BOTTOM_RIGHT);
        btns.getChildren().addAll(submit, cancel); // add buttons to HBox
        
        // text displays screen purpose to user
        Text display = new Text("Inmate Booking");
        display.setStyle("-fx-font-size: 24pt; -fx-base: black; -fx-text-fill: green; "
                + "-fx-effect: dropshadow(gaussian, green, 6, 0.5, 1, 1);");
        
        // stackpane holds text
        StackPane topText = new StackPane();
        topText.setAlignment(Pos.BOTTOM_CENTER);
        topText.getChildren().add(display);
        
        // borderpane holds stackpane, gridpane, and hbox
        BorderPane root = new BorderPane();
        // positioning for each pane/box
        root.setTop(topText);
        root.setCenter(grid);
        root.setBottom(btns);
        
        Scene scene = new Scene(root, 600, 350);    // create scene
        
        // name/display scene
        stage.setTitle("Book Inmate");
        stage.setScene(scene);
        stage.show();
    }
    public void releaseInmate(){
        
        Stage stage = new Stage();
        
        Button yes = new Button("Yes");
        yes.setPadding(new Insets(5, 20, 5, 20));
        yes.setStyle("-fx-font-size: 12pt; -fx-base: green; -fx-text-fill: white;"
                + " -fx-effect: dropshadow(gaussian, black, 5, 0.5, 1, 1);");
        yes.setOnAction(e -> stage.close());
        
        Button no = new Button("No");
        no.setPadding(new Insets(5, 20, 5, 20));
        no.setStyle("-fx-font-size: 12pt; -fx-base: green; -fx-text-fill: white;"
                + " -fx-effect: dropshadow(gaussian, black, 5, 0.5, 1, 1);");
        no.setOnAction(e -> stage.close());
        
        HBox btns = new HBox();
        btns.setSpacing(30);
        btns.setPadding(new Insets(25, 25, 25, 25));
        btns.setAlignment(Pos.BOTTOM_CENTER);
        btns.getChildren().addAll(yes, no);
        
        Text warning = new Text("Are you sure you want to release this inmate?");
        
        StackPane textDisplay = new StackPane();
        textDisplay.getChildren().add(warning);
        
        BorderPane root = new BorderPane();
        root.setCenter(textDisplay);
        root.setBottom(btns);
        
        Scene scene = new Scene(root, 250, 150);
        
        stage.setTitle("Release Inmate");
        stage.setScene(scene);
        stage.show();
    }
    public void listInmates(){
        Stage stage = new Stage();
        
        listDisplay.setItems(observeInmates);
        listDisplay.setPrefSize(150, 150);
        listDisplay.setCellFactory(new Callback<ListView<Inmate>, ListCell<Inmate>>(){
            @Override
            public ListCell<Inmate> call(ListView<Inmate> p){
                ListCell<Inmate> cell = new ListCell<Inmate>(){
                    @Override
                    protected void updateItem(Inmate t, boolean bln){
                        super.updateItem(t, bln);
                        if(t != null){
                            setText(t.getFirstName() + " " + t.getLastName() + "\t\t|\t " + t.getID()
                            + "\t\t|\t " + t.getEthnicity() + "\t\t|\t" + t.getHeight() + "\t\t|\t" + t.getWeight()
                            + "\t\t|\t" + t.getStatus() + "\t\t|\t" + t.getBlock());
                        }
                    }
                };
                return cell;
            } 
        });
        
        listDisplay.setOnMouseClicked(new EventHandler<Event>(){
            @Override
            public void handle(Event event){
                Inmate selectedInmate = (Inmate) listDisplay.getSelectionModel().getSelectedItem();
            }
        });
        
        Tooltip v = new Tooltip("Update Inmate's Visitor Log");
        Tooltip r = new Tooltip("Release Selected Inmate");
        Tooltip c = new Tooltip("Close Window");
        
        Button cancel = new Button("Cancel");
        cancel.setStyle("-fx-font-size: 12pt; -fx-base: green; -fx-text-fill: white;"
                + " -fx-effect: dropshadow(gaussian, black, 5, 0.5, 1, 1);");
        cancel.setPadding(new Insets(5, 5, 5, 5));
        cancel.setTooltip(c);
        cancel.setOnAction(e -> stage.close());
        
        Button release = new Button("Release Inmate");
        release.setStyle("-fx-font-size: 12pt; -fx-base: green; -fx-text-fill: white;"
                + " -fx-effect: dropshadow(gaussian, black, 5, 0.5, 1, 1);");
        release.setPadding(new Insets(5, 5, 5, 5));
        release.setTooltip(r);
        release.setOnAction(e -> releaseInmate());
        
        Button visitorLog = new Button("Visitor Log");
        visitorLog.setStyle("-fx-font-size: 12pt; -fx-base: green; -fx-text-fill: white;"
                + " -fx-effect: dropshadow(gaussian, black, 5, 0.5, 1, 1);");
        visitorLog.setPadding(new Insets(5, 5, 5, 5));
        visitorLog.setTooltip(v);
        visitorLog.setOnAction(e -> updateVisitorLog());
        
        HBox btns = new HBox();
        btns.setSpacing(30);
        btns.setPadding(new Insets(15, 10, 10, 10));
        btns.setAlignment(Pos.BOTTOM_RIGHT);
        btns.getChildren().addAll(visitorLog, release, cancel);
        
        TabPane tabs = new TabPane();
        Tab name = new Tab("Name");
        name.setClosable(false);
        name.setOnSelectionChanged(new EventHandler<Event>() {
            
            @Override
            public void handle(Event event) {
                if(name.isSelected()){
                    sortByName();
                    SortedList<String> sortedName = new SortedList(observeInmates);
                    listDisplay.setItems(sortedName);
                    listDisplay.setCellFactory(new Callback<ListView<Inmate>, ListCell<Inmate>>(){
                        @Override
                        public ListCell<Inmate> call(ListView<Inmate> p){
                            ListCell<Inmate> cell = new ListCell<Inmate>(){
                                @Override
                                protected void updateItem(Inmate t, boolean bln){
                                    super.updateItem(t, bln);
                                    if(t != null){
                                    setText(t.getFirstName() + " " + t.getLastName() + "\t\t|\t " + t.getID()
                                        + "\t\t|\t " + t.getEthnicity() + "\t\t|\t" + t.getHeight() + "\t\t|\t" + t.getWeight()
                                        + "\t\t|\t" + t.getStatus() + "\t\t|\t" + t.getBlock());
                                    }
                                }
                            };
                            return cell;
                        } 
                    });
                }
            }
        });
        Tab inmateID = new Tab("Inmate ID");
        
        inmateID.setClosable(false);
        inmateID.setOnSelectionChanged(new EventHandler<Event>(){
            @Override
            public void handle(Event t){
                if(inmateID.isSelected()){
                    sortByID();
                    SortedList<Integer> sortedID = new SortedList(observeInmates);
                    listDisplay.setItems(sortedID);
                    listDisplay.setCellFactory(new Callback<ListView<Inmate>, ListCell<Inmate>>(){
                        @Override
                        public ListCell<Inmate> call(ListView<Inmate> p){
                            ListCell<Inmate> cell = new ListCell<Inmate>(){
                                @Override
                                protected void updateItem(Inmate t, boolean bln){
                                    super.updateItem(t, bln);
                                    if(t != null){
                                    setText(t.getFirstName() + " " + t.getLastName() + "\t\t|\t " + t.getID()
                                        + "\t\t|\t " + t.getEthnicity() + "\t\t|\t" + t.getHeight() + "\t\t|\t" + t.getWeight()
                                        + "\t\t|\t" + t.getStatus() + "\t\t|\t" + t.getBlock());
                                    }
                                }
                            };
                            return cell;
                        } 
                    });
                }
            }
        });
        Tab cellBlock = new Tab("Cell Block");
        cellBlock.setClosable(false);
        cellBlock.setOnSelectionChanged(new EventHandler<Event>(){
            @Override
            public void handle(Event t){
                if(cellBlock.isSelected()){
                    sortByCell();
                    SortedList<String> sortedCell = new SortedList(observeInmates);
                    listDisplay.setItems(sortedCell);
                    listDisplay.setCellFactory(new Callback<ListView<Inmate>, ListCell<Inmate>>(){
                        @Override
                        public ListCell<Inmate> call(ListView<Inmate> p){
                            ListCell<Inmate> cell = new ListCell<Inmate>(){
                                @Override
                                protected void updateItem(Inmate t, boolean bln){
                                    super.updateItem(t, bln);
                                    if(t != null){
                                    setText(t.getFirstName() + " " + t.getLastName() + "\t\t|\t " + t.getID()
                                        + "\t\t|\t " + t.getEthnicity() + "\t\t|\t" + t.getHeight() + "\t\t|\t" + t.getWeight()
                                        + "\t\t|\t" + t.getStatus() + "\t\t|\t" + t.getBlock());
                                    }
                                }
                            };
                            return cell;
                        } 
                    });
                }
            }
        });
        tabs.getTabs().addAll(inmateID, name, cellBlock);
        
        BorderPane root = new BorderPane();
        root.setTop(tabs);
        root.setCenter(listDisplay);
        root.setBottom(btns);
        
        Scene scene = new Scene(root, 900, 800);
        
        stage.setTitle("List Inmates");
        stage.setScene(scene);
        stage.show();
    }
   public void sortByName(){
        Collections.sort(inmates, new Comparator<Inmate>(){
            @Override
            public int compare(Inmate o1, Inmate o2){
                return o1.getLastName().compareTo(o2.getLastName());
            }
        });
    }
    public void sortByID(){
        Collections.sort(inmates, new Comparator<Inmate>(){
            @Override
            public int compare(Inmate o1, Inmate o2){
                return Integer.toString(o1.getID()).compareTo(Integer.toString(o2.getID()));
            }
        });
    }
    public void sortByCell(){
        Collections.sort(inmates, new Comparator<Inmate>(){
            @Override
            public int compare(Inmate o1, Inmate o2){
                return o1.getBlock().compareTo(o2.getBlock());
            }
        });
    }
    public void updateVisitorLog(){
        // WIP
    }
}