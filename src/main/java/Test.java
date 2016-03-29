import java.io.IOException;

import model.BankDataDocument;
import model.CollectedDataObject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


public class Test {
        public static void main(String[] args) throws InvalidFormatException, IOException {

                BankDataDocument doc = new BankDataDocument("src/main/resources/2015jansept.xls", 0);


        CollectedDataObject collectedDataObject = new CollectedDataObject(doc);
                collectedDataObject.readPurchasesFromFile();
                collectedDataObject.printPurchases();
               collectedDataObject.mergeChargers();
                collectedDataObject.print();
            System.out.println(collectedDataObject.getTotalIn());
            System.out.println(collectedDataObject.getTotalOut());
        }

}

