package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValueWrapperTest {
	
	private ValueWrapper op0 = new ValueWrapper(null);
    private ValueWrapper op1 = new ValueWrapper(420);
    private ValueWrapper op2 = new ValueWrapper(6.9);
    private ValueWrapper op3 = new ValueWrapper("6.66");
    private ValueWrapper op4 = new ValueWrapper("1337");

	@Test
	public void testNullAdd() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());

		assertEquals(0, v1.getValue());
		assertEquals(null, v2.getValue());
	}

	@Test
	public void testDoubleAddInt() {
		ValueWrapper v1 = new ValueWrapper("1.2E1");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue());

		assertEquals(13.0, v1.getValue());
		assertEquals(1, v2.getValue());
	}

	@Test
	public void testIntAddInt() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue());

		assertEquals(13, v1.getValue());
		assertEquals(1, v2.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void testInvalidAdd() {
		ValueWrapper v1 = new ValueWrapper("Ankica");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue());
	}

	@Test
	public void testNullAsInt() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());

		assertTrue(v1.getValue() instanceof Integer);
		assertTrue(v2.getValue() == null);
	}

	@Test
	public void testStringAsDouble() {
		ValueWrapper v1 = new ValueWrapper("1.2E1");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue());

		assertTrue(v1.getValue() instanceof Double);
		assertTrue(v2.getValue() instanceof Integer);
	}

	@Test
	public void testStringAsInt() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue());

		assertTrue(v1.getValue() instanceof Integer);
		assertTrue(v2.getValue() instanceof Integer);
	}

	@Test
	public void testSetValue() {
		ValueWrapper v1 = new ValueWrapper(null);
		assertNull(v1.getValue());
		v1.setValue("ABC");
		assertEquals("ABC", v1.getValue());
	}

    @Test
    public void testGetValue() {
    	ValueWrapper v1 = new ValueWrapper(null);
    	ValueWrapper v2 = new ValueWrapper(3);
        assertNull(v1.getValue());
        assertEquals(3, v2.getValue());
    }
    
    
    @Test
    public void testAddDouble() {
        double value = 6.9;
        op0.add(value);
        assertEquals(0, op0.numCompare(6.9));
        op1.add(value);
        assertEquals(0, op1.numCompare(426.9));
        op2.add(value);
        assertEquals(0, op2.numCompare(13.8));
        op3.add(value);
        assertEquals(0, op3.numCompare(13.56));
        op4.add(value);
        assertEquals(0, op4.numCompare(1343.9));
    }

    @Test
    public void testAddInteger() {
        int value = 3;
        op0.add(value);
        assertEquals(0, op0.numCompare(3));
        op1.add(value);
        assertEquals(0, op1.numCompare(423));
        op2.add(value);
        assertEquals(0, op2.numCompare(9.9));
        op3.add(value);
        assertEquals(0, op3.numCompare(9.66));
        op4.add(value);
        assertEquals(0, op4.numCompare(1340));
    }

    @Test(expected = RuntimeException.class)
    public void testAddInvalid() {
        op0.add(true);
    }

    @Test
    public void testAddNull() {
        op0.add(null);
        assertEquals(0, op0.numCompare(0));
        op1.add(null);
        assertEquals(0, op1.numCompare(420));
        op2.add(null);
        assertEquals(0, op2.numCompare(6.9));
        op3.add(null);
        assertEquals(0, op3.numCompare(6.66));
        op4.add(null);
        assertEquals(0, op4.numCompare(1337));
    }

    @Test
    public void testAddString() {
        String value = "6.9";
        op0.add(value);
        assertEquals(0, op0.numCompare(6.9));
        op1.add(value);
        assertEquals(0, op1.numCompare(426.9));
        op2.add(value);
        assertEquals(0, op2.numCompare(13.8));
        op3.add(value);
        assertEquals(0, op3.numCompare(13.56));
        op4.add(value);
        assertEquals(0, op4.numCompare(1343.9));
    }

    
    @Test
    public void testSubstractDouble() {
        double value = 0.1;
        op0.subtract(value);
        assertEquals(0, op0.numCompare(-0.1));
        op1.subtract(value);
        assertEquals(0, op1.numCompare(419.9));
        op2.subtract(value);
        assertEquals(0, op2.numCompare(6.8));
        op3.subtract(value);
        assertEquals(0, op3.numCompare(6.56));
        op4.subtract(value);
        assertEquals(0, op4.numCompare(1336.9));
    }

    @Test
    public void testSubstractInteger() {
        int value = 3;
        op0.subtract(value);
        assertEquals(0, op0.numCompare(-3));
        op1.subtract(value);
        assertEquals(0, op1.numCompare(417));
        op2.subtract(value);
        assertEquals(0, op2.numCompare(3.9));
        op3.subtract(value);
        assertEquals(0, op3.numCompare(3.66));
        op4.subtract(value);
        assertEquals(0, op4.numCompare(1334));
    }

    @Test(expected = RuntimeException.class)
    public void testSubstractInvalid() {
        op0.subtract(true);
    }

    @Test
    public void testSubstractNull() {
        op0.subtract(null);
        assertEquals(0, op0.numCompare(0));
        op1.subtract(null);
        assertEquals(0, op1.numCompare(420));
        op2.subtract(null);
        assertEquals(0, op2.numCompare(6.9));
        op3.subtract(null);
        assertEquals(0, op3.numCompare(6.66));
        op4.subtract(null);
        assertEquals(0, op4.numCompare(1337));
    }

    @Test
    public void testSubstractString() {
        String value = "100";
        op0.subtract(value);
        assertEquals(0, op0.numCompare(-100));
        op1.subtract(value);
        assertEquals(0, op1.numCompare(320));
        op2.subtract(value);
        assertEquals(0, op2.numCompare(-93.1));
        op3.subtract(value);
        assertEquals(0, op3.numCompare(-93.34));
        op4.subtract(value);
        assertEquals(0, op4.numCompare(1237));
    }
    
    @Test
    public void testMultiplyDouble() {
        double value = 6.9;
        op0.multiply(value);
        assertEquals(0, op0.numCompare(0));
        op1.multiply(value);
        assertEquals(0, op1.numCompare(2898));
        op2.multiply(value);
        assertEquals(0, op2.numCompare(47.61));
        op3.multiply(value);
        assertEquals(0, op3.numCompare(45.954));
        op4.multiply(value);
        assertEquals(0, op4.numCompare(9225.3));
    }

    @Test
    public void testMultiplyInteger() {
        int value = 69;
        op0.multiply(value);
        assertEquals(0, op0.numCompare(0));
        op1.multiply(value);
        assertEquals(0, op1.numCompare(28980));
        op2.multiply(value);
        assertEquals(0, op2.numCompare(476.1));
        op3.multiply(value);
        assertEquals(0, op3.numCompare(459.54));
        op4.multiply(value);
        assertEquals(0, op4.numCompare(92253));
    }

    @Test(expected = RuntimeException.class)
    public void testMultiplyInvalid() {
        op0.multiply(false);
    }

    @Test
    public void testMultiplyNull() {
        op0.multiply(null);
        assertEquals(0, op0.numCompare(0));
        op1.multiply(null);
        assertEquals(0, op1.numCompare(0));
        op2.multiply(null);
        assertEquals(0, op2.numCompare(0));
        op3.multiply(null);
        assertEquals(0, op3.numCompare(0));
        op4.multiply(null);
        assertEquals(0, op4.numCompare(0));
    }

    @Test
    public void testMultiplytring() {
    	String value = "6.9";
        op0.multiply(value);
        assertEquals(0, op0.numCompare(0));
        op1.multiply(value);
        assertEquals(0, op1.numCompare(2898));
        op2.multiply(value);
        assertEquals(0, op2.numCompare(47.61));
        op3.multiply(value);
        assertEquals(0, op3.numCompare(45.954));
        op4.multiply(value);
        assertEquals(0, op4.numCompare(9225.3));
    }

    @Test
    public void testDivideDouble() {
        double value = 1.5;
        op0.divide(value);
        assertEquals(0, op0.numCompare(0));
        op1.divide(value);
        assertEquals(0, op1.numCompare(280));
        op2.divide(value);
        assertEquals(0, op2.numCompare(4.6));
        op3.divide(value);
        assertEquals(0, op3.numCompare(4.44));
        op4.divide(value);
        assertEquals(0, op4.numCompare(891.3333));
    }

    @Test
    public void testDivideInteger() {
        int value = 5;
        op0.divide(value);
        assertEquals(0, op0.numCompare(0));
        op1.divide(value);
        assertEquals(0, op1.numCompare(84));
        op2.divide(value);
        assertEquals(0, op2.numCompare(1.38));
        op3.divide(value);
        assertEquals(0, op3.numCompare(1.332));
        op4.divide(value);
        assertEquals(0, op4.numCompare(267));
    }

    @Test(expected = RuntimeException.class)
    public void testDivideInvalid() {
        op0.divide(true);
    }

    @Test(expected = ArithmeticException.class)
    public void testDivideNull() {
        op0.divide(null);
    }

    @Test
    public void testDivideString() {
        String value = "4.2";
        op0.divide(value);
        assertEquals(0, op0.numCompare(0));
        op1.divide(value);
        assertEquals(0, op1.numCompare(100));
        op2.divide(value);
        assertEquals(0, op2.numCompare(1.6428));
        op3.divide(value);
        assertEquals(0, op3.numCompare(1.5857));
        op4.divide(value);
        assertEquals(0, op4.numCompare(318.3333));
    }

    @Test
    public void testToString() {
        assertEquals("null", op0.toString());
        assertEquals("420", op1.toString());
        assertEquals("6.9", op2.toString());
    }
}
