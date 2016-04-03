package GUI;

/**
 * Created by Ludvig on 3/29/2016.
 */
public interface SelectedItemSubject {

    public void register(SelectedItemObserver o);
    public void unregister(SelectedItemObserver o);
    public void notifyObservers();
}
