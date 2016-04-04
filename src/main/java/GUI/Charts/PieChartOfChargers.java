package GUI.Charts;

import GUI.BottomBoxRadioObserver;
import GUI.BottomBoxSliderObserver;
import GUI.SelectedItemObserver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;


import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.Charger;
import model.CollectedDataObject;

import java.util.ArrayList;


/**
 * Created by Ludvig on 3/29/2016.
 */
public class PieChartOfChargers extends Pane implements SelectedItemObserver, BottomBoxRadioObserver, BottomBoxSliderObserver {

    private ObservableList<PieChart.Data> data;
    private boolean showIncome = true;
    private CollectedDataObject currentSelectedCDO;
    private int offSet = 0;

    Label labelOnMouseEntered;
    PieChart chart;


    public PieChartOfChargers() {

        //Chart
        chart = new PieChart(data);

        chart.setTitle("MoneyMoneyMoney");
        chart.setLegendSide(Side.RIGHT);
        setVisible(false);
        //label to show on hover
        labelOnMouseEntered = new Label();
        labelOnMouseEntered.setTextFill(Color.BLACK);
        //labelOnMouseEntered.setStyle("-fx-font: 32 arial;");

        getChildren().addAll(chart,labelOnMouseEntered);

    }

    @Override
    public void updateSelectedItem(Object selectedItem) {
            currentSelectedCDO = (CollectedDataObject) selectedItem;
            data = convertChargerListToData(currentSelectedCDO.getM_chargers());

            chart.setData(data);

            //      Display value on hover.
            for (PieChart.Data data : chart.getData()) {
                data.getNode().setOnMouseEntered(event -> {
                    System.out.println("entered on : " + data.getName() + " with data: " + data.getPieValue());
                    System.out.println("X:" + event.getSceneX() + " , Y: " + event.getSceneY());
                    //Placement
                    Point2D inScene = new Point2D(event.getSceneX(), event.getSceneY());
                    Point2D local = sceneToLocal(inScene);
                    labelOnMouseEntered.relocate(local.getX() + 25, local.getY());
                    //Change text.
                    labelOnMouseEntered.setText(String.valueOf(data.getPieValue()));
                    labelOnMouseEntered.setVisible(true);
                });
                data.getNode().setOnMouseExited(event -> {
                    labelOnMouseEntered.setVisible(false);
                });
            }
        setVisible(true);
    }


    private ObservableList<PieChart.Data> convertChargerListToData(ArrayList<Charger> chargers) {
        ArrayList<PieChart.Data> data = new ArrayList<>();

        for(Charger c: chargers) {
            if(showIncome) {
                if (c.getChargerIncome() > offSet)
                    data.add(new PieChart.Data(c.getChargerName(), c.getChargerIncome()));
            } else {
                if (c.getChargerOutcome() < offSet*-1)
                    data.add(new PieChart.Data(c.getChargerName(), c.getChargerOutcome()*-1));
            }
        }

        return FXCollections.observableArrayList(data);
    }

    @Override
    public void updateChargerIncomeOrOutcome(boolean showIncome) {
        this.showIncome = showIncome;
        if(currentSelectedCDO!= null)
        updateSelectedItem(this.currentSelectedCDO);        //Ugly way to update..
    }

    @Override
    public void updateOffsetFromSlider(double offSet) {
        this.offSet = (int) offSet;
        updateSelectedItem(this.currentSelectedCDO);

    }
}
