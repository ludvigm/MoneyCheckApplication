package GUI;

import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Charger;
import model.CollectedDataObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Ludvig on 4/2/2016.
 */
public class BottomBox extends VBox{

    ButtonsBox buttonBox = new ButtonsBox();
    SliderBox sliderBox = new SliderBox();

    public BottomBox() {
        setPrefHeight(75);
        getChildren().addAll(buttonBox,sliderBox);
    }

}

class ButtonsBox extends HBox implements BottomBoxRadioSubject {

    private ArrayList<BottomBoxRadioObserver> observers = new ArrayList<>();


    public ButtonsBox() {
        // Bottom box root

        setSpacing(25);
        setPrefHeight(25);

        //Buttons
        ToggleGroup chartModeToggle = new ToggleGroup();
        RadioButton incomeModeRadio = new RadioButton("Show income");
        incomeModeRadio.setOnAction(event -> {
            notifyObservers(true);
        });
        incomeModeRadio.setToggleGroup(chartModeToggle);
        incomeModeRadio.setSelected(true);

        RadioButton outComeModeRadio = new RadioButton("Show outcome");
        outComeModeRadio.setOnAction(event -> {
            notifyObservers(false);
        });
        outComeModeRadio.setToggleGroup(chartModeToggle);
        setAlignment(Pos.BASELINE_CENTER);
        getChildren().addAll(incomeModeRadio,outComeModeRadio);
    }

    @Override
    public void register(BottomBoxRadioObserver o) {
        observers.add(o);
    }

    @Override
    public void unregister(BottomBoxRadioObserver o) {
        observers.remove(observers.indexOf(o));
    }

    @Override
    public void notifyObservers(boolean showIncome) {
        for(BottomBoxRadioObserver o : observers) {
            o.updateChargerIncomeOrOutcome(showIncome);
        }
    }
}

class SliderBox extends HBox implements SelectedItemObserver,BottomBoxRadioObserver,BottomBoxSliderSubject {

    Slider positiveSlider = new Slider();
    Slider negativeSlider = new Slider();

    CollectedDataObject currentSelectedCDO;
    private ArrayList<BottomBoxSliderObserver> observers = new ArrayList<>();
    final DecimalFormat df = new DecimalFormat("####0");
    private boolean positiveSliderIsShowing = true;

    public SliderBox() {

        positiveSlider.setPrefSize(350,50);
        positiveSlider.setMin(0);
        positiveSlider.setShowTickMarks(true);

        negativeSlider.setPrefSize(350,50);
        negativeSlider.setMin(0);
        negativeSlider.setShowTickMarks(true);


        setAlignment(Pos.CENTER);
        //setStyle("-fx-background-color: black; -fx-text-fill: white;");
        getChildren().addAll(positiveSlider);
    }

    @Override
    public void updateSelectedItem(Object selectedItem) {

        currentSelectedCDO = (CollectedDataObject) selectedItem;
        ArrayList<Charger> chargers = ((CollectedDataObject) selectedItem).getM_chargers();
        if(chargers.size()>0) {
            Collections.sort(chargers);
            //positive slider changes
            positiveSlider.setMax(chargers.get(chargers.size() - 1).getChargerIncome());
            positiveSlider.setMajorTickUnit(positiveSlider.getMax() / 10);
            positiveSlider.setShowTickLabels(true);

            positiveSlider.valueProperty().addListener(listener -> {
                notifyObservers();
            });
            negativeSlider.valueProperty().addListener(listener -> {
                notifyObservers();
            });

            //negative slider changes
            negativeSlider.setMax(chargers.get(0).getChargerOutcome() * -1);

            negativeSlider.setMajorTickUnit(Double.valueOf(df.format(negativeSlider.getMax() / 10)));
            negativeSlider.setShowTickLabels(true);
        }
    }

    @Override
    public void updateChargerIncomeOrOutcome(boolean showIncome) {
        if(showIncome) {
            getChildren().remove(negativeSlider);
            getChildren().add(positiveSlider);
            positiveSliderIsShowing = true;
            positiveSlider.setValue(0);
        } else {
            getChildren().remove(positiveSlider);
            getChildren().add(negativeSlider);
            positiveSliderIsShowing = false;
            negativeSlider.setValue(0);
        }
    }

    @Override
    public void register(BottomBoxSliderObserver o) {
        observers.add(o);
    }

    @Override
    public void unregister(BottomBoxSliderObserver o) {
        observers.remove(observers.indexOf(o));
    }

    @Override
    public void notifyObservers() {
        for(BottomBoxSliderObserver o : observers) {
            if(positiveSliderIsShowing) {
                o.updateOffsetFromSlider(positiveSlider.getValue());
            } else{
                o.updateOffsetFromSlider(negativeSlider.getValue());
            }
        }
    }
}