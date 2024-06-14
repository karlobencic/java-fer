package hr.fer.zemris.java.hw01;

import static org.junit.Assert.*;

import org.junit.Test;

public class FactorialTest {

	@Test
	public void factorial20() {
		long fact = Factorial.factorial(20);
		assertEquals(2432902008176640000l, fact);
	}
	
	@Test
	public void factorial19() {
		long fact = Factorial.factorial(19);
		assertEquals(121645100408832000l, fact);
	}
	
	@Test
	public void factorial1() {
		long fact = Factorial.factorial(1);
		assertEquals(1, fact);
	}
	
	@Test
	public void factorial2() {
		long fact = Factorial.factorial(2);
		assertEquals(2, fact);
	}
	
	@Test
	public void factorial0() {
		long fact = Factorial.factorial(0);
		assertEquals(1, fact);
	}

}
