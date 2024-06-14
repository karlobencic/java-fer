package hr.fer.zemris.java.gui.prim;

import static org.junit.Assert.*;

import org.junit.Test;

public class PrimListModelTests {

	@Test
	public void testSize() {
		PrimListModel model = new PrimListModel();
		
		assertEquals(0, model.getSize());
		
		for (int i = 0; i < 50; i++) {
			model.next();
		}
		
		assertEquals(50, model.getSize());
	}
	
	@Test
	public void testGetElement() {
		PrimListModel model = new PrimListModel();
		
		for (int i = 0; i < 100; i++) {
			model.next();
		}
		
		assertEquals(1, model.getElementAt(0).intValue());
		assertEquals(2, model.getElementAt(1).intValue());
		assertEquals(7, model.getElementAt(4).intValue());
		assertEquals(11, model.getElementAt(5).intValue());
		assertEquals(523, model.getElementAt(99).intValue());
	}
}
