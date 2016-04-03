package GUI;

/**
 * Created by Ludvig on 4/2/2016.
 */
public interface BottomBoxRadioSubject {

    public void register(BottomBoxRadioObserver o);
    public void unregister(BottomBoxRadioObserver o);
    public void notifyObservers(boolean showIncome);
}
