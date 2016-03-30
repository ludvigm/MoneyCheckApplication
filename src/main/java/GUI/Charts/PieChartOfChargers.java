package GUI.Charts;

import GUI.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.chart.PieChart;


import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.Charger;
import model.CollectedDataObject;

import java.util.ArrayList;


/**
 * Created by Ludvig on 3/29/2016.
 */
public class PieChartOfChargers extends PieChart implements Observer {

    private ObservableList<PieChart.Data> data;
    private boolean showIncome = true;

    final Label caption = new Label("dofus");
    public PieChartOfChargers() {

        final PieChart chart = new PieChart(data);
        chart.setTitle("Imported Fruits");
        this.getChildren().add(chart);

        caption.setScaleX(5);
        caption.setScaleY(5);
        caption.toFront();

    }

    @Override
    public void update(Object selectedItem) {
        CollectedDataObject selectedCDO = (CollectedDataObject) selectedItem;
        data = convertChargerListToData(selectedCDO.getM_chargers());
        System.out.println("pie updated. length of data: " + data.size());
        setData(data);

        for (PieChart.Data data : this.data) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
                    event -> {
                        System.out.println("entered on : " + data.getName() + " with data: " + data.getPieValue());
                        caption.setTranslateX(event.getScreenX());
                        caption.setTranslateY(event.getScreenY());
                        caption.setText(String.valueOf(data.getPieValue() + "H"));
                        caption.setTextFill(Color.DARKORANGE);
                        caption.setStyle("-fx-font: 32 arial;");
                        caption.toFront();
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
                if (c.getChargerIncome() < 0)
                    data.add(new PieChart.Data(c.getChargerName(), c.getChargerIncome()));
            }
        }

        return FXCollections.observableArrayList(data);
    }

    public void setShowIncome(boolean showIncome) {
        this.showIncome = showIncome;
    }
}
