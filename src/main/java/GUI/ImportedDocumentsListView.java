package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import model.CollectedDataObject;

import java.util.ArrayList;

/**
 * Created by Ludvig on 3/29/2016.
 */



public class ImportedDocumentsListView extends ListView implements Subject{

    private ArrayList<Observer> observers = new ArrayList<>();
    private CollectedDataObject selectedItem;
    public ObservableList<CollectedDataObject> listViewItems = FXCollections.observableArrayList();

    public ImportedDocumentsListView() {
        ListViewContextMenu listViewContextMenu = new ListViewContextMenu(listViewItems);

        setItems(listViewItems);

        getSelectionModel().selectedItemProperty().addListener(observable -> {
            CollectedDataObject selected = (CollectedDataObject) getSelectionModel().getSelectedItem(); //GetSelectedItem()
            if (selected != null) {
                selectedItem = selected;
                notifyObservers();
            }
        });

        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                System.out.println("leftclicked on " + getSelectionModel().getSelectedItem());
                if (listViewContextMenu.isShowing()) {
                    listViewContextMenu.hide();
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                System.out.println("rightclicked on " + getSelectionModel().getSelectedItem());
                if (getSelectionModel().getSelectedItem() != null) {
                    getSelectionModel().clearSelection();
                } else {
                    listViewContextMenu.show(this, null, event.getX() + 5, event.getY() - 10);
                }
            }
        });

    }

    @Override
    public void register(Observer o) {
        observers.add(o);
    }

    @Override
    public void unregister(Observer o) {
        observers.remove(observers.indexOf(o));
    }

    @Override
    public void notifyObservers() {
        for(Observer o : observers) {
            o.update(this.selectedItem);
        }
    }
}
