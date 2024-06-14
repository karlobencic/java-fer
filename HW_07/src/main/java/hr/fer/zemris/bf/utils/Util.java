package hr.fer.zemris.bf.utils;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

import hr.fer.zemris.bf.model.Node;

/**
 * The {@code Util} class contains helper static methods for manipulating a
 * boolean expression and performing calculations.
 * 
 * @author Karlo Bencic
 * 
 */
public class Util {

	/**
	 * This method generates all combinations of boolean values for the given
	 * list of variables(truth table) and for each generated value it calles the
	 * defined {@code consumer} action.
	 *
	 * @param variables
	 *            the variables list
	 * @param consumer
	 *            the consumer, see {@link Consumer}
	 */
	public static void forEach(List<String> variables, Consumer<boolean[]> consumer) {
		int n = variables.size();
		for (int i = 0; i < Math.pow(2, n); i++) {
			consumer.accept(getTruthValues(i, n));
		}
	}

	/**
	 * This method returns the row number where the given boolean values are
	 * presented in the truth table.
	 *
	 * @param values
	 *            the boolean values
	 * @return the row where these values are presented in the truth table
	 */
	public static int booleanArrayToInt(boolean[] values) {
		int n = values.length;
		for (int i = 0; i < Math.pow(2, n); i++) {
			if (Arrays.equals(values, getTruthValues(i, n))) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Generates each row of the truth table.
	 *
	 * @param level
	 *            the current row
	 * @param size
	 *            the truth table size
	 * @return the values
	 */
	private static boolean[] getTruthValues(int level, int size) {
		boolean[] values = new boolean[size];
		for (int j = 0; j < size; j++) {
			values[size - j - 1] = (level / (int) Math.pow(2, j)) % 2 == 1;
		}
		return values;
	}

	/**
	 * This method generates all the combinations of given variables and using
	 * {@link ExpressionEvaluator} calculates the result of the
	 * {@code expression}, and if it's equal to the {@code expressionValue} it's
	 * added in the returned set.
	 *
	 * @param variables
	 *            the variables list
	 * @param expression
	 *            the boolean expression
	 * @param expressionValue
	 *            the expression value
	 * @return the filtered set of assignments
	 */
	public static Set<boolean[]> filterAssignments(List<String> variables, Node expression, boolean expressionValue) {
		Set<boolean[]> filtered = new LinkedHashSet<>();

		ExpressionEvaluator eval = new ExpressionEvaluator(variables);
		forEach(variables, values -> {
			eval.setValues(values);
			expression.accept(eval);
			if (eval.getResult() == expressionValue) {
				filtered.add(values);
			}
		});

		return filtered;
	}

	/**
	 * Method converts a boolean expression to sum of minterms.
	 *
	 * @param variables
	 *            the variables list
	 * @param expression
	 *            the boolean expression
	 * @return the set which contains the minterms
	 */
	public static Set<Integer> toSumOfMinterms(List<String> variables, Node expression) {
		return calculate(variables, expression, true);
	}

	/**
	 * Method converts a boolean expression to product of maxterms.
	 *
	 * @param variables
	 *            the variables list
	 * @param expression
	 *            the boolean expression
	 * @return the set which contains the maxterms
	 */
	public static Set<Integer> toProductOfMaxterms(List<String> variables, Node expression) {
		return calculate(variables, expression, false);
	}

	/**
	 * Calculates sum of minterms or product of maxterms and adds it into set.
	 *
	 * @param variables
	 *            the variables list
	 * @param expression
	 *            the expression expression
	 * @param minterm
	 *            true if calculating sum of minterms, false if calculating
	 *            product of maxterms
	 * @return the set which contains the minterms or maxterms
	 */
	private static Set<Integer> calculate(List<String> variables, Node expression, boolean minterm) {
		Set<Integer> calculated = new TreeSet<>();
		for (boolean[] values : filterAssignments(variables, expression, minterm)) {
			calculated.add(booleanArrayToInt(values));
		}
		return calculated;
	}
}
