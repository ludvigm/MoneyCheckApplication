package GUI;

import GUI.PopupWindows.addNewListItemWindow;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import model.CollectedDataObject;

/**
 * Created by Ludvig on 3/29/2016.
 */
public class ListViewContextMenu extends ContextMenu {



    public ListViewContextMenu(ObservableList<CollectedDataObject> listViewItems) {
        //"Add new" button in menu.
        MenuItem addItemToList = new MenuItem("Add new");
        addItemToList.setOnAction((event -> {
            //AddNewListItemWindow window = AddNewListItemWindow.getInstance();
            addNewListItemWindow window = new addNewListItemWindow();
            window.showAndWait();
            window.toFront();
            if (window.getDataObject() != null)
                listViewItems.add(window.getDataObject());
        }));
        getItems().addAll(addItemToList);
    }
}
