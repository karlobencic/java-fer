package hr.fer.zemris.bf.qmc;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class MinimizerTests {

	@Test
	public void test0() {
		Set<Integer> minterms = new HashSet<>(Arrays.asList(0, 1, 3, 10, 11, 14, 15));
		Set<Integer> dontcares = new HashSet<>(Arrays.asList(4, 6));
		Minimizer m = new Minimizer(minterms, dontcares, Arrays.asList("A", "B", "C", "D"));

		List<String> solutions = Arrays.asList(
				"A AND C OR NOT A AND NOT B AND NOT C OR NOT A AND NOT B AND D",
				"A AND C OR NOT A AND NOT B AND NOT C OR NOT B AND C AND D",
				"A AND C OR NOT A AND NOT C AND NOT D OR NOT A AND NOT B AND D"
			);
		
		assertTrue(m.getMinimalFormsAsString().containsAll(solutions));
	}

	@Test
	public void test1() {
		Set<Integer> minterms = new HashSet<>(Arrays.asList(
				0, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30
			));
		Set<Integer> dontcares = Collections.emptySet();
		Minimizer m = new Minimizer(minterms, dontcares, Arrays.asList("A", "B", "C", "D", "E"));
		
		List<String> solutions = Arrays.asList(
				"NOT D AND NOT E OR NOT A AND NOT B AND NOT D OR NOT A AND D AND E OR NOT B AND D AND E OR A AND C AND NOT D OR C AND NOT E OR B AND NOT C",
				"NOT D AND NOT E OR NOT A AND NOT B AND NOT D OR NOT B AND D AND E OR NOT A AND C AND D OR A AND C AND NOT D OR C AND NOT E OR B AND NOT C",
				"NOT D AND NOT E OR NOT A AND NOT B AND NOT D OR NOT B AND D AND E OR NOT A AND B AND D OR A AND C AND NOT D OR C AND NOT E OR B AND NOT C",
				"NOT D AND NOT E OR NOT A AND NOT C AND NOT D OR NOT A AND D AND E OR NOT C AND D AND E OR A AND B AND NOT D OR NOT B AND C OR B AND NOT E",
				"NOT D AND NOT E OR NOT A AND NOT C AND NOT D OR NOT C AND D AND E OR NOT A AND C AND D OR A AND B AND NOT D OR NOT B AND C OR B AND NOT E",
				"NOT D AND NOT E OR NOT A AND NOT C AND NOT D OR NOT C AND D AND E OR NOT A AND B AND D OR A AND B AND NOT D OR NOT B AND C OR B AND NOT E",
				"NOT D AND NOT E OR NOT A AND NOT B AND E OR NOT B AND D AND E OR NOT A AND C AND D OR A AND C AND NOT D OR C AND NOT E OR B AND NOT C",
				"NOT D AND NOT E OR NOT A AND NOT B AND E OR NOT B AND D AND E OR NOT A AND B AND D OR A AND C AND NOT D OR C AND NOT E OR B AND NOT C",
				"NOT D AND NOT E OR NOT A AND NOT B AND E OR NOT A AND D AND E OR NOT B AND D AND E OR A AND C AND NOT D OR C AND NOT E OR B AND NOT C",
				"NOT D AND NOT E OR NOT A AND NOT C AND E OR NOT C AND D AND E OR NOT A AND C AND D OR A AND B AND NOT D OR NOT B AND C OR B AND NOT E",
				"NOT D AND NOT E OR NOT A AND NOT C AND E OR NOT A AND D AND E OR NOT C AND D AND E OR A AND B AND NOT D OR NOT B AND C OR B AND NOT E",
				"NOT D AND NOT E OR NOT A AND NOT C AND E OR NOT C AND D AND E OR NOT A AND B AND D OR A AND B AND NOT D OR NOT B AND C OR B AND NOT E"
			);
		
		assertTrue(m.getMinimalFormsAsString().containsAll(solutions));
	}
	
	@Test
	public void test6() {
		Set<Integer> minterms = new HashSet<>(Arrays.asList(
				0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 31
			));
		Set<Integer> dontcares = Collections.emptySet();
		Minimizer m = new Minimizer(minterms, dontcares, Arrays.asList("A", "B", "C", "D", "E"));
		
		List<String> solutions = Arrays.asList(
				"NOT A AND NOT E OR NOT B AND D OR C AND NOT D OR A AND NOT C OR B AND E",
				"NOT A AND NOT E OR NOT B AND D OR B AND NOT C OR A AND NOT D OR C AND E",
				"NOT A AND NOT E OR NOT C AND D OR NOT B AND C OR A AND NOT D OR B AND E",
				"NOT A AND NOT E OR NOT C AND D OR B AND NOT D OR A AND NOT B OR C AND E",
				"NOT A AND NOT E OR NOT B AND C OR B AND NOT D OR A AND NOT C OR D AND E",
				"NOT A AND NOT E OR C AND NOT D OR B AND NOT C OR A AND NOT B OR D AND E",
				"NOT B AND NOT E OR NOT A AND D OR C AND NOT D OR B AND NOT C OR A AND E",
				"NOT B AND NOT E OR NOT A AND D OR B AND NOT D OR A AND NOT C OR C AND E",
				"NOT B AND NOT E OR NOT C AND D OR NOT A AND C OR B AND NOT D OR A AND E",
				"NOT B AND NOT E OR NOT C AND D OR NOT A AND B OR A AND NOT D OR C AND E",
				"NOT B AND NOT E OR NOT A AND C OR B AND NOT C OR A AND NOT D OR D AND E",
				"NOT B AND NOT E OR C AND NOT D OR NOT A AND B OR A AND NOT C OR D AND E",
				"NOT C AND NOT E OR NOT A AND D OR NOT B AND C OR B AND NOT D OR A AND E",
				"NOT C AND NOT E OR NOT A AND D OR C AND NOT D OR A AND NOT B OR B AND E",
				"NOT C AND NOT E OR NOT B AND D OR NOT A AND C OR A AND NOT D OR B AND E",
				"NOT C AND NOT E OR NOT B AND D OR C AND NOT D OR NOT A AND B OR A AND E",
				"NOT C AND NOT E OR NOT A AND C OR B AND NOT D OR A AND NOT B OR D AND E",
				"NOT C AND NOT E OR NOT B AND C OR NOT A AND B OR A AND NOT D OR D AND E",
				"NOT D AND NOT E OR NOT A AND D OR NOT B AND C OR A AND NOT C OR B AND E",
				"NOT D AND NOT E OR NOT A AND D OR B AND NOT C OR A AND NOT B OR C AND E",
				"NOT D AND NOT E OR NOT B AND D OR NOT A AND C OR B AND NOT C OR A AND E",
				"NOT D AND NOT E OR NOT B AND D OR NOT A AND B OR A AND NOT C OR C AND E",
				"NOT D AND NOT E OR NOT C AND D OR NOT A AND C OR A AND NOT B OR B AND E",
				"NOT D AND NOT E OR NOT C AND D OR NOT B AND C OR NOT A AND B OR A AND E"
			);
		
		assertTrue(m.getMinimalFormsAsString().containsAll(solutions));
	}
	
	@Test
	public void test2() {
		Set<Integer> minterms = new HashSet<>(Arrays.asList(
				0, 1, 2, 3, 4, 5, 6, 8, 10, 11, 12, 13, 14, 15, 16, 17, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31
			));
		Set<Integer> dontcares = Collections.emptySet();
		Minimizer m = new Minimizer(minterms, dontcares, Arrays.asList("A", "B", "C", "D", "E"));

		assertEquals(4, m.getMinimalFormsAsString().size());
	}
	
	@Test
	public void test3() {
		Set<Integer> minterms = new HashSet<>(Arrays.asList(
				0, 2, 3, 4, 5, 6, 7, 8, 9, 11, 13, 14, 15, 16, 18, 19, 20, 21, 22, 24, 25, 26, 27, 28, 29, 30, 31
			));
		Set<Integer> dontcares = Collections.emptySet();
		Minimizer m = new Minimizer(minterms, dontcares, Arrays.asList("A", "B", "C", "D", "E"));

		assertEquals(2, m.getMinimalFormsAsString().size());
	}
	
	@Test
	public void test4() {
		Set<Integer> minterms = new HashSet<>(Arrays.asList(
				0, 1, 3, 4, 5, 6, 7, 8, 10, 11, 12, 13, 15, 16, 17, 18, 21, 22, 23, 24, 25, 26, 27, 29, 30, 31
			));
		Set<Integer> dontcares = Collections.emptySet();
		Minimizer m = new Minimizer(minterms, dontcares, Arrays.asList("A", "B", "C", "D", "E"));

		assertEquals(2, m.getMinimalFormsAsString().size());
	}
	
	@Test
	public void test5() {
		Set<Integer> minterms = new HashSet<>(Arrays.asList(
				0, 1, 2, 4, 5, 7, 8, 9, 10, 11, 12, 14, 15, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 28, 29, 30, 31
			));
		Set<Integer> dontcares = Collections.emptySet();
		Minimizer m = new Minimizer(minterms, dontcares, Arrays.asList("A", "B", "C", "D", "E"));

		assertEquals(55, m.getMinimalFormsAsString().size());
	}
	
}
