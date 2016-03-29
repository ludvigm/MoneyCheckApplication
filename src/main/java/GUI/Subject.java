package GUI;

/**
 * Created by Ludvig on 3/29/2016.
 */
public interface Subject {

    public void register(Observer o);
    public void unregister(Observer o);
    public void notifyObservers();
}
