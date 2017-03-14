package GUI;

import GUI.Charts.LineChartOfPurchasesByDate;
import GUI.Charts.PieChartOfChargers;
import GUI.TableViews.ChargersTableView;
import GUI.TableViews.PurchasesTableView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.BankDataDocument;
import model.CollectedDataObject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.io.File;
import java.io.IOException;

public class gui extends Application {

    ImportedDocumentsListView leftSideLV = new ImportedDocumentsListView();
    final PurchasesTableView purchasesTableView = new PurchasesTableView();
    final ChargersTableView chargersTableView = new ChargersTableView();
    final PieChartOfChargers pieChartChargers = new PieChartOfChargers();
    final LineChartOfPurchasesByDate lineChartOfPurchasesByDate = new LineChartOfPurchasesByDate();
    final BottomBox bottomBox = new BottomBox();
    final BorderPane middleBorderPane = new BorderPane();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {


        /*
        Register
         */
        leftSideLV.register(purchasesTableView);
        leftSideLV.register(chargersTableView);
        leftSideLV.register(pieChartChargers);
        leftSideLV.register(bottomBox.sliderBox);
        leftSideLV.register(lineChartOfPurchasesByDate);

        bottomBox.buttonBox.register(pieChartChargers);
        bottomBox.buttonBox.register(bottomBox.sliderBox);
        bottomBox.buttonBox.register(lineChartOfPurchasesByDate);

        bottomBox.sliderBox.register(pieChartChargers);


        primaryStage.setTitle("MoneyTracker");
        BorderPane pane = new BorderPane();

        //topright
        //Radios
        ToggleGroup tg = new ToggleGroup();
        RadioButton purchasesModeRadio = new RadioButton("View all purchases");
        purchasesModeRadio.setOnAction(event -> {
            pane.setRight(purchasesTableView);
        });
        purchasesModeRadio.setToggleGroup(tg);
        purchasesModeRadio.setSelected(true);
        RadioButton chargerModeRadio = new RadioButton("View by chargers");
        chargerModeRadio.setOnAction(event -> {
            pane.setRight(chargersTableView);
        });
        chargerModeRadio.setToggleGroup(tg);


        //top center
        //tabpane
        TabPane topTabs = new TabPane();
        topTabs.getSelectionModel().selectedItemProperty().addListener(listener -> {
            String selectedID = topTabs.getSelectionModel().getSelectedItem().getId();
            if(selectedID.equals("1")) {
                middleBorderPane.setCenter(pieChartChargers);
                bottomBox.sliderBox.setVisible(true);
            } else if(selectedID.equals("2")) {
                middleBorderPane.setCenter(lineChartOfPurchasesByDate);
                bottomBox.sliderBox.setVisible(false);

            } else if(selectedID.equals("3")) {

            }
        });
        Tab pieChartTab = new Tab("Chargers PieChart");
        pieChartTab.setId("1");
        Tab stapleDiagramTab = new Tab("Staple Diagram");
        stapleDiagramTab.setId("2");



        topTabs.getTabs().addAll(pieChartTab,stapleDiagramTab);

        HBox tabPaneContainer = new HBox();
        tabPaneContainer.getChildren().add(topTabs);

        //container

        // Radios Container
        HBox radioContainer = new HBox(10);

        radioContainer.getChildren().addAll(purchasesModeRadio,chargerModeRadio);
        radioContainer.setAlignment(Pos.CENTER_RIGHT);
        radioContainer.setPadding(new Insets(15, 25, 10, 10));

        //topleft directory selection
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Import all .xls files from a directory...");
        directoryChooser.setInitialDirectory(new File("src/main/resources/"));
        Button selectDirButton = new Button("Import all from directory..");
        selectDirButton.setOnAction(event -> {
                    File dir = directoryChooser.showDialog(null);
                    if (dir != null) {
                        for (File file : dir.listFiles()) {
                            try {
                                if (file.isFile()) {
                                    CollectedDataObject cdo = new CollectedDataObject(new BankDataDocument(file.getPath(), 0));
                                    if (cdo.getFileName(file.getPath()).split("\\.")[1].equals("xls")) {
                                        cdo.generateDataWhenFileRead();
                                        leftSideLV.listViewItems.add(cdo);
                                    }
                                }
                            } catch (IOException | InvalidFormatException ex) {
                                System.out.println("Error loading file.");
                            }
                        }
                    }
                }
        );
        VBox directorySelectionContainer = new VBox();
        directorySelectionContainer.setAlignment(Pos.CENTER_LEFT);
        directorySelectionContainer.setPadding(new Insets(15, 25, 10, 10));
        directorySelectionContainer.getChildren().add(selectDirButton);


        //VBox
        GridPane topBar = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(33);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(33);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(33);
        topBar.getColumnConstraints().addAll(column1, column2, column3);
       // topBar.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        topBar.setPrefHeight(50);
        topBar.add(directorySelectionContainer,0,0);
        topBar.add(radioContainer,2,0);

        middleBorderPane.setTop(tabPaneContainer);
        middleBorderPane.setCenter(pieChartChargers);
        middleBorderPane.setBottom(bottomBox);

        pane.setCenter(middleBorderPane);
        pane.setTop(topBar);
        pane.setRight(purchasesTableView);
        pane.setLeft(leftSideLV);
        primaryStage.setScene(new Scene(pane, 1400, 600));
        primaryStage.show();
    }
}
