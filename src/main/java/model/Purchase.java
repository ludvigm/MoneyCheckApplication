package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Purchase {

	//Fields
	private StringProperty date;
	public void setDate(String value) { dateProperty().set(value); }
	public String getDate() { return dateProperty().get();}
	public StringProperty dateProperty() {
		if (date == null) date = new SimpleStringProperty(this, "date");
		return date;
	}

	private DoubleProperty amount;
	public void setAmount(Double value) {amountProperty().set(value);}
	public Double getAmountProperty() { return amountProperty().get();}
	public DoubleProperty amountProperty() {
		if (amount == null) amount = new SimpleDoubleProperty(this, "amount");
		return amount;
	}

	private StringProperty chargerName;
	public void setChargerName(String value) { chargerProperty().set(value); }
	public String getChargerName() { return chargerProperty().get();}
	public StringProperty chargerProperty() {
		if (chargerName == null) chargerName = new SimpleStringProperty(this, "charger");
		return chargerName;
	}
	
	//Constructor
	public Purchase(String date, double amount, String charger) {
		setDate(date);
		setAmount(amount);
		setChargerName(charger);
	}
	
	public double getAmount() {return getAmountProperty();}

	public String toString() {
		return getDate() + ": " + getChargerName() + " - " + getAmountProperty();
	}
}
