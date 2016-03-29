package GUI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.BankDataDocument;
import model.Charger;
import model.CollectedDataObject;
import model.Purchase;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class gui extends Application {


    final ListView leftSideLV = new ListView();
    final TableView<Purchase> purchasesTableView = new TableView();
    final TableView<Charger> chargersTableView = new TableView();
    final NumberFormat decimalFormatter = new DecimalFormat("#0.00");
    ObservableList<CollectedDataObject> items = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("MoneyTracker");
        BorderPane pane = new BorderPane();

        final ContextMenu listViewContextMenu = new ContextMenu();
        MenuItem menuItemAddToList = new MenuItem("Add new");
        menuItemAddToList.setOnAction((event -> {
            //AddNewListItemWindow window = AddNewListItemWindow.getInstance();
            addNewListItemWindow window = new addNewListItemWindow();
            window.showAndWait();
            window.toFront();
            if (window.getDataObject() != null)
                items.add(window.getDataObject());
        }));

        listViewContextMenu.getItems().addAll(menuItemAddToList);

        leftSideLV.setItems(items);


        leftSideLV.getSelectionModel().selectedItemProperty().addListener(observable -> {
            CollectedDataObject selected = (CollectedDataObject) leftSideLV.getSelectionModel().getSelectedItem(); //GetSelectedItem()
            if(selected != null) {
                ObservableList<Purchase> purchases = FXCollections.observableArrayList(selected.getM_purchases());
                ObservableList<Charger> chargers = FXCollections.observableArrayList(selected.getM_chargers());
                purchasesTableView.setItems(purchases);
                chargersTableView.setItems(chargers);
            }
        });

        leftSideLV.setOnMouseClicked(event ->  {
            if(event.getButton() == MouseButton.PRIMARY) {
                System.out.println("leftclicked on " + leftSideLV.getSelectionModel().getSelectedItem());
                if(listViewContextMenu.isShowing()) {
                    listViewContextMenu.hide();
                }
            }
            else if (event.getButton() == MouseButton.SECONDARY) {
                System.out.println("rightclicked on " + leftSideLV.getSelectionModel().getSelectedItem());
                if(leftSideLV.getSelectionModel().getSelectedItem() != null) {
                    leftSideLV.getSelectionModel().clearSelection();
                } else {
                    listViewContextMenu.show(leftSideLV, null, event.getX() + 5, event.getY() - 10);
                }
            }
        });

        //top
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
        //Container
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
                    for(File file : dir.listFiles()) {
                        try {
                            if(file.isFile()) {
                                CollectedDataObject cdo = new CollectedDataObject(new BankDataDocument(file.getPath(), 0));
                                if(cdo.getFileName(file.getPath()).split("\\.")[1].equals("xls")) {
                                    cdo.readPurchasesFromFile();
                                    cdo.mergeChargers();
                                    items.add(cdo);
                                }
                            }
                        } catch (IOException|InvalidFormatException ex) {
                            System.out.println("Error loading file.");
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
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        topBar.getColumnConstraints().addAll(column1,column2);
       // topBar.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        topBar.setPrefHeight(50);
        topBar.setPrefWidth(50);
        topBar.add(directorySelectionContainer,0,0);
        topBar.add(radioContainer,1,0);

        //Right side, TableView
        purchasesTableView.setPrefWidth(450);
        TableColumn<Purchase,String> date = new TableColumn<>("Date");
        date.setCellValueFactory(new PropertyValueFactory("date"));
        date.setPrefWidth(150);
        TableColumn<Purchase,Double> amount = new TableColumn<>("Amount");

        amount.setCellValueFactory(new PropertyValueFactory("amount"));
        amount.setCellFactory(new Callback<TableColumn<Purchase, Double>,
                TableCell<Purchase, Double>>() {
            @Override
            public TableCell<Purchase, Double> call(
                    TableColumn<Purchase, Double> param) {
                return new TableCell<Purchase, Double>() {

                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        if (!empty) {
                            int currentIndex = indexProperty()
                                    .getValue() < 0 ? 0
                                    : indexProperty().getValue();
                            Double type = param
                                    .getTableView().getItems()
                                    .get(currentIndex).getAmount();
                            if (type > 0) {
                                setTextFill(Color.GREEN);
                                setText("" + item);
                            } else {
                                setTextFill(Color.RED);
                                setText("" + item);
                            }
                        }
                    }
                };
            }
        });
        amount.setPrefWidth(150);

        TableColumn<Purchase,String> charger = new TableColumn<>("Charger");
        charger.setCellValueFactory(new PropertyValueFactory("charger"));
        charger.setPrefWidth(150);

        purchasesTableView.getColumns().setAll(date, amount, charger);



        //Chargers
        chargersTableView.setPrefWidth(450);
        TableColumn<Charger,String> chargerName = new TableColumn<>("Charger");
        chargerName.setCellValueFactory(new PropertyValueFactory("chargerName"));
        chargerName.setPrefWidth(150);

        TableColumn<Charger,Double> chargerIncome = new TableColumn<>("Income");
        chargerIncome.setCellValueFactory(new PropertyValueFactory("chargerIncome"));
        chargerIncome.setCellFactory(param ->
            new TableCell<Charger,Double>() {
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        if (!empty) {
                            setTextFill(Color.GREEN);
                            setText(decimalFormatter.format(item));
                        }
                    }
                });
        chargerIncome.setPrefWidth(150);

        TableColumn<Charger,Double> chargerOutcome = new TableColumn<>("Outcome");
        chargerOutcome.setCellValueFactory(new PropertyValueFactory("chargerOutcome"));
        chargerOutcome.setCellFactory(param ->
                new TableCell<Charger, Double>() {
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        if (!empty) {
                            setTextFill(Color.RED);
                            setText(decimalFormatter.format(item));
                        }
                    }
                });
        chargerOutcome.setPrefWidth(150);

        chargersTableView.getColumns().setAll(chargerName,chargerIncome,chargerOutcome);






        pane.setTop(topBar);
        pane.setRight(purchasesTableView);
        pane.setLeft(leftSideLV);
        primaryStage.setScene(new Scene(pane, 1200, 500));
        primaryStage.show();
    }
}
