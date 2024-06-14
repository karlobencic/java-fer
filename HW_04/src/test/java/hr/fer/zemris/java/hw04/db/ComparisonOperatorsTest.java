package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComparisonOperatorsTest {

	@Test
	public void testLess() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertEquals(true, oper.satisfied("Ana", "Jasna"));
		assertEquals(false, oper.satisfied("Ana", "Ana"));
		assertEquals(false, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testLessOrEquals() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertEquals(true, oper.satisfied("Ana", "Jasna"));
		assertEquals(true, oper.satisfied("Ana", "Ana"));
		assertEquals(false, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testGreater() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertEquals(false, oper.satisfied("Ana", "Jasna"));
		assertEquals(false, oper.satisfied("Ana", "Ana"));
		assertEquals(true, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testGreaterOrEquals() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertEquals(false, oper.satisfied("Ana", "Jasna"));
		assertEquals(true, oper.satisfied("Ana", "Ana"));
		assertEquals(true, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testEquals() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertEquals(false, oper.satisfied("Ana", "Jasna"));
		assertEquals(true, oper.satisfied("Ana", "Ana"));
		assertEquals(false, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testNotEquals() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertEquals(true, oper.satisfied("Ana", "Jasna"));
		assertEquals(false, oper.satisfied("Ana", "Ana"));
		assertEquals(true, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testLike() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(false, oper.satisfied("Zagreb", "Aba*"));
		assertEquals(false, oper.satisfied("AAA", "AA*AA"));
		assertEquals(true, oper.satisfied("AAAA", "AA*AA"));
	}

}
