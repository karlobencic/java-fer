package demo;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import hr.fer.zemris.bf.parser.Parser;
import hr.fer.zemris.bf.qmc.Minimizer;
import hr.fer.zemris.bf.utils.ExpressionTreePrinter;
import hr.fer.zemris.bf.utils.Util;
import hr.fer.zemris.bf.utils.VariablesGetter;

/**
 * The {@code QMC} is a demonstration for the {@link Minimizer} class which uses
 * the Quine-McCluskey method. This program is not functional because of the
 * limited time of development.
 * 
 * @author Karlo Bencic
 * 
 */
public class QMC {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.print("> ");
			if (sc.hasNextLine()) {
				String input = sc.nextLine();
				if (input.equals("quit")) {
					System.out.println("Goodbye m8!");
					break;
				}

				String[] form = input.split("[=|]");

				if (form.length < 2 || form.length > 3) {
					System.out.println("Error: invalid function");
					continue;
				}

				String function = form[0].trim();
				String vars = function.split("[(|)]")[1];
				List<String> variables = Arrays.asList(vars.split(","));

				String expression = form[1].trim();
				Parser parser = new Parser(expression);
				VariablesGetter getter = new VariablesGetter();
				parser.getExpression().accept(getter);
				if (getter.getVariables().size() > variables.size()) {
					System.out.println("Error: invalid number of variables");
					continue;
				}

				Set<Integer> minterms = Util.toSumOfMinterms(getter.getVariables(), parser.getExpression());
				List<Integer> dontCareList = Collections.emptyList();

				if (form.length == 3) {
					String dontCareExpression = form[2].trim();
					if (dontCareExpression.startsWith("[")) {
						String dontCare = dontCareExpression.split("[\\[|\\]]")[1];
						dontCareList = Arrays.asList(dontCare.split(",")).stream().map(Integer::parseInt)
								.collect(Collectors.toList());
					} else {
						Parser dontCareParser = new Parser(dontCareExpression);
						// TODO: implement
					}
				}

				Set<Integer> dontCares = new HashSet<>(dontCareList);
				// TODO: implement
			}
		}

		sc.close();
	}
}
