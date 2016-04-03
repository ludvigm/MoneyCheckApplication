package GUI.TableViews;

import GUI.SelectedItemObserver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import model.Charger;
import model.CollectedDataObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Ludvig on 3/29/2016.
 */
public class ChargersTableView extends TableView implements SelectedItemObserver {

    final NumberFormat decimalFormatter = new DecimalFormat("#0.00");
    public ChargersTableView() {
        //Chargers
        setPrefWidth(450);
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
        getColumns().setAll(chargerName,chargerIncome,chargerOutcome);
    }
    @Override
    public void updateSelectedItem(Object selectedItem) {
        CollectedDataObject selectedCDO = (CollectedDataObject) selectedItem;
        ObservableList<Charger> chargers = FXCollections.observableArrayList(selectedCDO.getM_chargers());
        setItems(chargers);
    }

}
