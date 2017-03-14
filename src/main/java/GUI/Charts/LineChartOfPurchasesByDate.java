package GUI.Charts;

import GUI.BottomBoxRadioObserver;
import GUI.SelectedItemObserver;
import javafx.scene.chart.*;
import javafx.scene.layout.Pane;
import model.CollectedDataObject;
import model.UniqueDatePurchase;
import java.util.ArrayList;


/**
 * Created by Ludvig on 4/4/2016.
 */
public class LineChartOfPurchasesByDate extends Pane implements SelectedItemObserver, BottomBoxRadioObserver {

    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final LineChart<String,Number> incomeLineChart = new LineChart<>(xAxis,yAxis);
    final LineChart<String,Number> outcomeLineChart = new LineChart<>(xAxis,yAxis);

    public LineChartOfPurchasesByDate() {
        xAxis.setLabel("Date");
        //creating the chart
        incomeLineChart.setTitle("Money gained");
        outcomeLineChart.setTitle("Money spent");
        //defining a series



    }

    @Override
    public void updateSelectedItem(Object selectedItem) {
        incomeLineChart.getData().removeAll(incomeLineChart.getData());
        outcomeLineChart.getData().removeAll(outcomeLineChart.getData());
        CollectedDataObject current = (CollectedDataObject) selectedItem;

        incomeLineChart.getData().add(purchasesToSeries(current.getM_positiveDatePurchases()));
        outcomeLineChart.getData().add(purchasesToSeries(current.getM_negativeDatePurchases()));
    }

    private XYChart.Series purchasesToSeries(ArrayList<UniqueDatePurchase> datePurchases) {
        XYChart.Series series = new XYChart.Series();
        for(UniqueDatePurchase p : datePurchases) {
            series.getData().add(new XYChart.Data<>(p.getDate(),p.getAmount()));
        }
        return series;
    }

    @Override
    public void updateChargerIncomeOrOutcome(boolean showIncome) {
        if(showIncome) {
            getChildren().remove(outcomeLineChart);
            getChildren().add(incomeLineChart);
        } else {
            getChildren().remove(incomeLineChart);
            getChildren().add(outcomeLineChart);
        }
    }
}
