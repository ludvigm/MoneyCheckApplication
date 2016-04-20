import java.io.IOException;
import java.util.ArrayList;

import model.BankDataDocument;
import model.CollectedDataObject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


public class Test {
        public static void main(String[] args) throws InvalidFormatException, IOException {

       /*         BankDataDocument doc = new BankDataDocument("src/main/resources/2015jansept.xls", 0);


        CollectedDataObject collectedDataObject = new CollectedDataObject(doc);
                collectedDataObject.readPurchasesFromFile();
                collectedDataObject.printPurchases();
               collectedDataObject.mergeChargers();
                collectedDataObject.print();
            System.out.println(collectedDataObject.getTotalIn());
            System.out.println(collectedDataObject.getTotalOut());
        }*/



            method('a');
            method('b');
            method('c');

        }


    private static void method(char letter) {

        String letters = "gh";


        String word = "babbe";
        int i = 0;
        while (i < letters.length()){
            if (letter == letters.charAt(i)){
                System.out.println("found in letters");
                return;
            }
            i++;
        }

        int j = 0;

        while (j < word.length()){
            System.out.println(word.charAt(j));
            System.out.println(letter);

            if (letter == word.charAt(j)){

                System.out.println("found in word");
                return;
            }
            j++;
        }

        System.out.println("error");
    }
}

