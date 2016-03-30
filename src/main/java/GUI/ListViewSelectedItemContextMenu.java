package GUI;

import GUI.PopupWindows.addNewListItemWindow;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import model.CollectedDataObject;

/**
 * Created by Ludde on 2016-03-30.
 */
public class ListViewSelectedItemContextMenu extends ContextMenu{


    private CollectedDataObject selectedItem;
    public void setSelectedItem(CollectedDataObject selected) {
        selectedItem = selected;
    }

    public ListViewSelectedItemContextMenu(ObservableList<CollectedDataObject> listViewItems) {
        //"Add new" button in menu.
        MenuItem removeSelectedFromList = new MenuItem("Remove");
        removeSelectedFromList.setOnAction((event -> {
            listViewItems.remove(selectedItem);
        }));
        getItems().addAll(removeSelectedFromList);
    }
}
