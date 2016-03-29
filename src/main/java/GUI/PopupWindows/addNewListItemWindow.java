package GUI.PopupWindows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.BankDataDocument;
import model.CollectedDataObject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class addNewListItemWindow extends Stage {

    //Future supported filetypes can be added here.
    private final ArrayList<String> supportedFileTypes = new ArrayList<>(
            Arrays.asList("xls","xlsx")
    );

    private final String TITLE = "Create new document";
    private final String INSTRUCTION_1 = "Please select a file of type .xls";

    private static addNewListItemWindow instance = null;
    private File selectedFile;
    private CollectedDataObject cdo;

    public addNewListItemWindow() {

        setTitle(TITLE);

        VBox topBox = new VBox(20);
        topBox.setAlignment(Pos.CENTER);

        HBox bottomBox = new HBox(20);
        bottomBox.setAlignment(Pos.CENTER);


        bottomBox.setPadding(new Insets(10,10,10,10));
        Label instruction = new Label(INSTRUCTION_1);

        Label selectedFileLabel = new Label("No file selected..");


        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src/main/resources/"));
        fileChooser.setTitle("Open Resource File");

        Button selectFileButton = new Button("Browse..");
        selectFileButton.setOnAction(event ->  {
            File tempFile = fileChooser.showOpenDialog(null);
            if(tempFile != null) {
                if (checkFile(tempFile)) {
                    selectedFileLabel.setText(getFileName(tempFile.getPath()));
                    selectedFile = tempFile;
                } else {
                    System.out.println("Not .xls!");
                }
            }
        });

        Button acceptButton = new Button("Accept");
        acceptButton.setOnAction(event ->  {
                try {
                    if(selectedFile != null) {
                        cdo = new CollectedDataObject(new BankDataDocument(selectedFile.getPath(), 0));
                        cdo.readPurchasesFromFile();
                        cdo.mergeChargers();
                        System.out.println(cdo.getTotalIn());
                    }
                    close();
                } catch (IOException|InvalidFormatException ex) {
                    ex.printStackTrace();
                }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> {
            close();
                });

        bottomBox.getChildren().addAll(acceptButton,cancelButton);
        topBox.getChildren().addAll(instruction,selectFileButton,selectedFileLabel);
        BorderPane bp = new BorderPane();
        Scene scene = new Scene(bp,275,200);
        bp.setTop(topBox);
        bp.setBottom(bottomBox);

        setScene(scene);
        setResizable(false);
    }

    public static addNewListItemWindow getInstance() {
        if(instance == null) {
            instance = new addNewListItemWindow();
        }
        return instance;
    }


    private boolean checkFile(File file) {
        return supportedFileTypes.contains(getPrefix(file.getPath()));
    }
    private String getPrefix(String path) {
        System.out.println(path);
        String prefix = path.split("\\.")[1];
        System.out.println(prefix);
        return prefix;
    }

    private String getFileName(String path) {
        String[] arr = path.split("\\\\");
        return arr[arr.length-2]+"/"+arr[arr.length-1];
    }

    public File getFile() {
        return this.selectedFile;
    }

    public CollectedDataObject getDataObject() {
        return this.cdo;
    }
}
