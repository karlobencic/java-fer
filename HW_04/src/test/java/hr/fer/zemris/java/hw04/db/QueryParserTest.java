package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueryParserTest {
	
	@Test
	public void testDirectQuery() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		assertEquals(true, qp1.isDirectQuery());

		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertEquals(false, qp2.isDirectQuery());
	}
	
	@Test
	public void testSize() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		assertEquals(1, qp1.getQuery().size());

		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertEquals(2, qp2.getQuery().size());
	}
	
	@Test
	public void testJmbagDirect() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		assertEquals("0123456789", qp1.getQueriedJMBAG());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testJmbagNotDirect() {
		QueryParser qp = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertEquals("0123456789", qp.getQueriedJMBAG());
	}
	
	@Test(expected = QueryParserException.class)
	public void testParseInvalidOperator() {
		new QueryParser("lastName LAJK \"B*\"").getQuery();
	}
	
	@Test(expected = QueryParserException.class)
	public void testParseInvalidValue() {
		new QueryParser("lname LIKE \"B*\"").getQuery();
	}
	
	@Test(expected = QueryParserException.class)
	public void testParseInvalidQuery() {
		new QueryParser("lastName LIKE B*").getQuery();
	}
}
