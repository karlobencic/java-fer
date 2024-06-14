package hr.fer.zemris.java.hw05.observer2;

/**
 * The class {@code ObserverExample} is a simple demo which shows the usage of
 * the {@code IntegerStorage} and {@code IntegerStorageObserver}.
 * 
 * @author Karlo Bencic
 * 
 */
public class ObserverExample {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		
		IntegerStorageObserver observer = new SquareValue();
		
		istorage.addObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));	
		
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		
		istorage.removeObserver(observer);
		
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}

}
