package GUI.Charts;

import GUI.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;


import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Charger;
import model.CollectedDataObject;

import java.util.ArrayList;


/**
 * Created by Ludvig on 3/29/2016.
 */
public class PieChartOfChargers extends Pane implements Observer {

    private ObservableList<PieChart.Data> data;
    private boolean showIncome = true;


    Label labelOnMouseEntered;
    PieChart chart;


    public PieChartOfChargers() {

        //Chart
        chart = new PieChart(data);

        chart.setTitle("MoneyMoneyMoney");
        chart.setLegendSide(Side.LEFT);


        //label to show on hover
        labelOnMouseEntered = new Label();
        labelOnMouseEntered.setTextFill(Color.DARKORANGE);
        //labelOnMouseEntered.setStyle("-fx-font: 32 arial;");

        getChildren().addAll(chart,labelOnMouseEntered);

    }

    @Override
    public void update(Object selectedItem) {
        CollectedDataObject selectedCDO = (CollectedDataObject) selectedItem;
        data = convertChargerListToData(selectedCDO.getM_chargers());
        chart.setData(data);

        //      Display value on hover.

        for (PieChart.Data data : chart.getData()) {
            data.getNode().setOnMouseEntered(event -> {
                System.out.println("entered on : " + data.getName() + " with data: " + data.getPieValue());
                System.out.println("X:" + event.getSceneX() + " , Y: " + event.getSceneY());
                //Placement
                Point2D inScene = new Point2D(event.getSceneX(),event.getSceneY());
                Point2D local = sceneToLocal(inScene);
                labelOnMouseEntered.relocate(local.getX(),local.getY());
                //Change text.
                labelOnMouseEntered.setText(String.valueOf(data.getPieValue()));
                labelOnMouseEntered.setVisible(true);
                    });
            data.getNode().setOnMouseExited(event -> {
                labelOnMouseEntered.setVisible(false);
            });
        }


    }

    private ObservableList<PieChart.Data> convertChargerListToData(ArrayList<Charger> chargers) {
        ArrayList<PieChart.Data> data = new ArrayList<>();

        for(Charger c: chargers) {
            if(showIncome) {
                if (c.getChargerIncome() > 0)
                    data.add(new PieChart.Data(c.getChargerName(), c.getChargerIncome()));
            } else {
                if (c.getChargerOutcome() < 0)
                    data.add(new PieChart.Data(c.getChargerName(), c.getChargerOutcome()));
            }
        }

        return FXCollections.observableArrayList(data);
    }

    public void setShowIncome(boolean showIncome) {
        this.showIncome = showIncome;
    }
    private void updateData() {

    }
}
