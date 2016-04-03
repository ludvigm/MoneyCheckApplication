package GUI;

/**
 * Created by Ludvig on 4/2/2016.
 */
public interface BottomBoxSliderSubject {

    public void register(BottomBoxSliderObserver o);
    public void unregister(BottomBoxSliderObserver o);
    public void notifyObservers();


}
