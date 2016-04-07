package model;

import org.apache.poi.ss.usermodel.Row;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class CollectedDataObject {

    private ArrayList<Purchase> m_purchases;
    private ArrayList<UniqueDatePurchase> m_positiveDatePurchases;
    private ArrayList<UniqueDatePurchase> m_negativeDatePurchases;
    private ArrayList<Charger> m_chargers;
    private BankDataDocument m_document;


    final DecimalFormat df = new DecimalFormat("####0.00");

    public CollectedDataObject(BankDataDocument doc) {
        m_purchases = new ArrayList<>();
        m_chargers = new ArrayList<>();
        m_positiveDatePurchases = new ArrayList<>();
        m_negativeDatePurchases = new ArrayList<>();
        m_document = doc;
    }



    public void readPositiveDatedPurchasesFromPurchases() {
        ArrayList<UniqueDatePurchase> list = new ArrayList<>();
        for(Purchase p : m_purchases) {
            if (p.getAmount() > 0) {
                UniqueDatePurchase datePurchase = getUniqueDatePurchaseByDate(list, p.getDate());
                if (datePurchase == null) {
                    UniqueDatePurchase u = new UniqueDatePurchase(p.getDate(), p.getAmount());
                    list.add(u);
                } else {
                    datePurchase.addAmount(p.getAmount());
                }
            }
        }
        Collections.sort(list);
        m_positiveDatePurchases = list;
    }

    public void readNegativeDatedPurchasesFromPurchases() {
        ArrayList<UniqueDatePurchase> list = new ArrayList<>();
        for(Purchase p : m_purchases) {
            if (p.getAmount() < 0) {
                UniqueDatePurchase datePurchase = getUniqueDatePurchaseByDate(list, p.getDate());
                if (datePurchase == null) {
                    UniqueDatePurchase u = new UniqueDatePurchase(p.getDate(), p.getAmount());
                    list.add(u);
                } else {
                    datePurchase.addAmount(p.getAmount());
                }
            }
        }
        Collections.sort(list);
        m_negativeDatePurchases = list;
    }

    public void mergeChargers() {
        for(Purchase p : m_purchases) {
            Charger tempCharger = getChargerByName(p.getChargerName());
            if(tempCharger == null) {
                Charger c = new Charger(p.getChargerName());
                c.addPurchase(p);
                m_chargers.add(c);
            } else {
                //System.out.println("Charger exists already, adding in and outcome to it!");
                tempCharger.addPurchase(p);
            }
        }
    }

    private Charger getChargerByName(String chargerName) {
        for(Charger c : m_chargers) {
            if (c.getChargerName().equals(chargerName)) {
                return c;
            }
        }
        return null;
    }

    private UniqueDatePurchase getUniqueDatePurchaseByDate(ArrayList<UniqueDatePurchase> list, String date) {
        for(UniqueDatePurchase p : list) {
            if(p.getDate().equals(date)) {
                return p;
            }
        }
        return null;
    }

    private boolean chargersContainCharger(String chargerName) {
        for(Charger c : m_chargers) {
            if(c.getChargerName().equals(chargerName)) {
                return false;
            }
        }
        return true;
    }

    public void readPurchasesFromFile() {
        Iterator<Row> rowIterator = m_document.getSheet().iterator();

        String date;
        String chargerName;
        double amount;

        while(rowIterator.hasNext()) {
            Row currentRow = rowIterator.next();
            try {
                try {
                    date = currentRow.getCell(2).getStringCellValue().trim();
                    chargerName = currentRow.getCell(4).getStringCellValue().trim();
                    amount = currentRow.getCell(6).getNumericCellValue();
                    if (date != null && chargerName != null && !date.isEmpty() && !chargerName.isEmpty()) {
                        Purchase p = new Purchase(date, amount, chargerName);
                        m_purchases.add(p);
                    }
                } catch (NullPointerException e) {
                    //System.out.println(e.getMessage());
                }
            } catch(IllegalStateException ex) {
               // System.out.println(ex.getMessage());
            }
        }
    }

    public void printPurchases() {
        for(Purchase p : m_purchases) {
            System.out.println(p.toString());
        }
    }

    public double getTotalOut() {
        double total = 0;
        for(Charger c : m_chargers) {
            total += c.getChargerOutcome();
        }
        return total;
    }
    public double getTotalIn() {
        double total = 0;
        for(Charger c : m_chargers) {
            total += c.getChargerIncome();
        }
        return total;
    }

    public void print() {
        Collections.sort(m_chargers);
        for(Charger c : m_chargers) {
            System.out.println(c.getChargerName() + ": " + "In: " + df.format(c.getChargerIncome())+ " - " + "out: " + df.format(c.getChargerOutcome())+ " - total: " + df.format(c.getChargerTotal()));
        }
    }

    public String getFileName(String path) {
        String[] arr = path.split("\\\\");
        return arr[arr.length-1];
    }

    public String toString() {
        if(m_purchases.size()>0)
        return getFileName(m_document.getFilePath() +"\n" + m_purchases.get(m_purchases.size()-1).getDate() + " through " + m_purchases.get(0).getDate());

        return getFileName(m_document.getFilePath()  + "\n No data found");
    }

    public ArrayList<Purchase> getM_purchases() {
        return m_purchases;
    }

    public ArrayList<Charger> getM_chargers() {
        return m_chargers;
    }

    public ArrayList<UniqueDatePurchase> getM_positiveDatePurchases() {
        return m_positiveDatePurchases;
    }

    public BankDataDocument getM_document() {
        return m_document;
    }

    public ArrayList<UniqueDatePurchase> getM_negativeDatePurchases() {
        return m_negativeDatePurchases;
    }

    public void setM_negativeDatePurchases(ArrayList<UniqueDatePurchase> m_negativeDatePurchases) {
        this.m_negativeDatePurchases = m_negativeDatePurchases;
    }

    public void generateDataWhenFileRead() {
        readPurchasesFromFile();
        mergeChargers();

        readNegativeDatedPurchasesFromPurchases();
        readPositiveDatedPurchasesFromPurchases();
    }

    public String getEarliestDateOccuringInPurchases() {
        if(m_purchases.size()>0)
            return m_purchases.get(0).getDate();
        return "purchases empty";
    }

    public String getLatestDateOccuringInPurchases() {
        if(m_purchases.size()>0)
            return m_purchases.get(m_purchases.size()-1).getDate();
        return "purchases empty";
    }
}
