package GUI.TableViews;


import GUI.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import model.CollectedDataObject;
import model.Purchase;

/**
 * Created by Ludvig on 3/29/2016.
 */
public class PurchasesTableView extends TableView implements Observer {

    public PurchasesTableView() {
        //Right side, TableView
        setPrefWidth(450);
        TableColumn<Purchase,String> date = new TableColumn<>("Date");
        date.setCellValueFactory(new PropertyValueFactory("date"));
        date.setPrefWidth(150);
        TableColumn<Purchase,Double> amount = new TableColumn<>("Amount");
        amount.setCellValueFactory(new PropertyValueFactory("amount"));
        amount.setCellFactory(param1 ->
                new TableCell<Purchase, Double>() {
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        if (!empty) {
                            if (item > 0) {
                                setTextFill(Color.GREEN);
                                setText("" + item);
                            } else {
                                setTextFill(Color.RED);
                                setText("" + item);
                            }
                        }
                    }
                });
        amount.setPrefWidth(150);
        TableColumn<Purchase,String> charger = new TableColumn<>("Charger");
        charger.setCellValueFactory(new PropertyValueFactory("charger"));
        charger.setPrefWidth(150);
        getColumns().setAll(date, amount, charger);
    }


    @Override
    public void update(Object selectedItem) {
        CollectedDataObject selectedCDO = (CollectedDataObject) selectedItem;
        ObservableList<Purchase> purchases = FXCollections.observableArrayList(selectedCDO.getM_purchases());
        setItems(purchases);
    }
}
