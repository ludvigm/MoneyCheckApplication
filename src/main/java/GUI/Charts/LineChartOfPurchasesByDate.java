package GUI.Charts;

import GUI.SelectedItemObserver;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.layout.Pane;
import model.CollectedDataObject;
import model.UniqueDatePurchase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Ludvig on 4/4/2016.
 */
public class LineChartOfPurchasesByDate extends Pane implements SelectedItemObserver {

    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final LineChart<String,Number> lineChart = new LineChart<>(xAxis,yAxis);
    public LineChartOfPurchasesByDate() {

        xAxis.setLabel("Date");
        //creating the chart

        lineChart.setTitle("Money spent each day");

        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Money spent");

        getChildren().add(lineChart);
    }

    @Override
    public void updateSelectedItem(Object selectedItem) {
        lineChart.getData().removeAll(lineChart.getData());
        CollectedDataObject current = (CollectedDataObject) selectedItem;

        lineChart.getData().add(purchasesToSeries(current.getM_positiveDatePurchases()));
        lineChart.getData().add(purchasesToSeries(current.getM_negativeDatePurchases()));
    }

    private XYChart.Series purchasesToSeries(ArrayList<UniqueDatePurchase> datePurchases) {
        XYChart.Series series = new XYChart.Series();
        for(UniqueDatePurchase p : datePurchases) {
            series.getData().add(new XYChart.Data<>(p.getDate(),p.getAmount()));
        }
        return series;
    }

}
