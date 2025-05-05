package lt.esdc.shapes.observer;

public interface ObservableShape {

  void addObserver(ShapeObserver observer);

  void removeObserver(ShapeObserver observer);

  void notifyObservers();
}
