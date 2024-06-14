package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import org.junit.Test;

public class ObjectMultistackTest {

	@Test
	public void testPush() {
		ObjectMultistack multistack = initMultiStack();
		
		assertEquals(420, multistack.peek("Int").getValue());
	}
	
	@Test
	public void testPushMultiple() {
		ObjectMultistack multistack = initMultiStack();
		multistack.push("Int", new ValueWrapper(421));
		multistack.push("Double", new ValueWrapper(6.666));
		multistack.push("Int", new ValueWrapper(422));
		multistack.push("Double", new ValueWrapper(6.67));
		
		assertEquals(422, multistack.peek("Int").getValue());
	}
	
	@Test
	public void testPopMultiple() {
		ObjectMultistack multistack = initMultiStack();
		multistack.push("Int", new ValueWrapper(421));
		multistack.push("Double", new ValueWrapper(6.666));
		multistack.push("Int", new ValueWrapper(422));
		multistack.push("Double", new ValueWrapper(6.67));
		
		assertEquals(6.67, multistack.pop("Double").getValue());
		assertEquals(422, multistack.pop("Int").getValue());
		assertEquals(421, multistack.pop("Int").getValue());	
		assertEquals(420, multistack.pop("Int").getValue());	
		assertEquals(6.666, multistack.pop("Double").getValue());
	}
	
	@Test
	public void testPop() {
		ObjectMultistack multistack = initMultiStack();

		assertEquals(420, multistack.pop("Int").getValue());
		assertEquals("6.9", multistack.pop("StringDouble").getValue());
	}
	
	@Test
	public void testPeek() {
		ObjectMultistack multistack = initMultiStack();

		assertEquals("6E9", multistack.peek("StringExponent").getValue());
		assertEquals(6.66, multistack.peek("Double").getValue());	
		assertEquals(null, multistack.peek("null").getValue());
	}
	
	@Test
	public void testPeekAndPop() {
		ObjectMultistack multistack = initMultiStack();

		assertEquals(420, multistack.peek("Int").getValue());
		assertEquals(420, multistack.pop("Int").getValue());	
		assertEquals("nais cymbek", multistack.peek("String").getValue());
		assertEquals("nais cymbek", multistack.pop("String").getValue());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPopEmpty() {
		ObjectMultistack multistack = initMultiStack();
		multistack.pop("empty");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPeekEmpty() {
		ObjectMultistack multistack = initMultiStack();
		multistack.peek("empty");
	}
	
	@Test
	public void testIsEmpty() {
		ObjectMultistack multistack = initMultiStack();
		assertTrue(multistack.isEmpty("empty"));
		assertFalse(multistack.isEmpty("Int"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullKey() {
		ObjectMultistack multistack = initMultiStack();
		multistack.push(null, new ValueWrapper(420));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullValue() {
		ObjectMultistack multistack = initMultiStack();
		multistack.push("null", null);
	}
	
	private ObjectMultistack initMultiStack() {
		ObjectMultistack multistack = new ObjectMultistack();

		multistack.push("Int", new ValueWrapper(420));
		multistack.push("Double", new ValueWrapper(6.66));
		multistack.push("StringInt", new ValueWrapper("360"));
		multistack.push("String", new ValueWrapper("nais cymbek"));
		multistack.push("StringDouble", new ValueWrapper("6.9"));
		multistack.push("StringExponent", new ValueWrapper("6E9"));
		multistack.push("null", new ValueWrapper(null));
		
		return multistack;
	}

}
