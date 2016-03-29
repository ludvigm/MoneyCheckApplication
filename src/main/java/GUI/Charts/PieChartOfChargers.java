package GUI.Charts;

import GUI.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;


import model.Charger;
import model.CollectedDataObject;

import java.util.ArrayList;


/**
 * Created by Ludvig on 3/29/2016.
 */
public class PieChartOfChargers extends PieChart implements Observer {

    private ObservableList<PieChart.Data> data;
    private boolean showIncome = true;

    public PieChartOfChargers() {

        final PieChart chart = new PieChart(data);
        chart.setTitle("Imported Fruits");
        this.getChildren().add(chart);
    }

    @Override
    public void update(Object selectedItem) {
        CollectedDataObject selectedCDO = (CollectedDataObject) selectedItem;
        data = convertChargerListToData(selectedCDO.getM_chargers());
        System.out.println("pie updated. length of data: " + data.size());
        setData(data);
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
