package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Charger implements Comparable<Charger>{

    //Fields

    private StringProperty chargerName;
    public void setChargerName(String value) { chargerNameProperty().set(value); }
    public String getChargerName() { return chargerNameProperty().get();}
    public StringProperty chargerNameProperty() {
        if (chargerName == null) chargerName = new SimpleStringProperty(this, "chargerName");
        return chargerName;
    }

    private DoubleProperty chargerIncome;
    public void setChargerIncome(double value) { chargerIncomeProperty().set(value); }
    public double getChargerIncome() { return chargerIncomeProperty().get();}
    public DoubleProperty chargerIncomeProperty() {
        if (chargerIncome == null) chargerIncome = new SimpleDoubleProperty(this, "chargerIncome");
        return chargerIncome;
    }

    private DoubleProperty chargerOutcome;
    public void setChargerOutcome(double value) { chargerOutcomeProperty().set(value); }
    public double getChargerOutcome() { return chargerOutcomeProperty().get();}
    public DoubleProperty chargerOutcomeProperty() {
        if (chargerOutcome == null) chargerOutcome = new SimpleDoubleProperty(this, "chargerOutcome");
        return chargerOutcome;
    }

    //Constructor
    public Charger(String chargerName) {
        setChargerName(chargerName);
        setChargerIncome(0);
        setChargerOutcome(0);
    }

    //Add purchase info
    public void addPurchase(Purchase p) {
        if(p.getAmount()>=0) {
            setChargerIncome(getChargerIncome() + p.getAmount());
        } else if(p.getAmount()<0){
            setChargerOutcome(getChargerOutcome() + p.getAmount());
        } else {
            System.out.println("invalid amount in purchase p");
        }

    }

    //GetSet


    public double getChargerTotal() {
        return getChargerIncome() + getChargerOutcome();
    }

    public int compareTo(Charger c) {
        return (int) (this.getChargerTotal()- c.getChargerTotal());
    }


}
