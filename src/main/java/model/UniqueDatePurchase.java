package model;

import java.util.Objects;

/**
 * Created by Ludde on 2016-04-04.
 */
public class UniqueDatePurchase implements Comparable {

    private String date;
    private double amount;

    public UniqueDatePurchase(String date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public void addAmount(double amount) {
        setAmount(this.amount+amount);
    }

    public void deductAmount(double amount) {
        setAmount(this.amount-amount);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String toString() {
        return this.getAmount() + " on " + this.getDate();
    }


    @Override
    public int compareTo(Object o) {
        UniqueDatePurchase up = (UniqueDatePurchase) o;
        return this.getDate().compareTo(up.getDate());
    }
}
