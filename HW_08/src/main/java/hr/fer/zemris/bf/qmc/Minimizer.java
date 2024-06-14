package hr.fer.zemris.bf.qmc;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;
import hr.fer.zemris.bf.utils.Tuple;

/**
 * The {@code Minimizer} class uses Quine-McCluskey method using Pyne-McCluskey
 * approach to minimize a boolean function. It also prints a log of the
 * minimization process depending on the defined log level in the
 * {@code logging.properties} file.
 * 
 * @author Karlo Bencic
 * 
 */
public class Minimizer {

	/** The log. */
	private final Logger LOG = Logger.getLogger("hr.fer.zemris.bf.qmc");

	/** The minterm set. */
	private Set<Integer> mintermSet;

	/** The dont care set. */
	private Set<Integer> dontCareSet;

	/** The variables list. */
	private List<String> variables;

	/** The minimal forms list. */
	private List<Set<Mask>> minimalForms;

	/** The log queue. */
	private Queue<Tuple<Level, Mask, String>> logQueue = new LinkedList<>();

	/** The implicants log. */
	private Set<Tuple<Level, Mask, String>> implicantsLog = new LinkedHashSet<>();

	/** The implicants queue. */
	private Queue<Mask> implicantsQueue = new LinkedList<>();

	/**
	 * Instantiates a new minimizer and finds minimal forms.
	 *
	 * @param mintermSet
	 *            the minterm set, not null
	 * @param dontCareSet
	 *            the dont care set, not null
	 * @param variables
	 *            the variables, not null
	 * @throws IllegalArgumentException
	 *             if minterms and dontcares overlap or arguments are null
	 */
	public Minimizer(Set<Integer> mintermSet, Set<Integer> dontCareSet, List<String> variables) {
		if (mintermSet == null || dontCareSet == null || variables == null) {
			throw new IllegalArgumentException("Arguments can't be null");
		}
		if (!Collections.disjoint(mintermSet, dontCareSet)) {
			throw new IllegalArgumentException("Minterms and Dontcares overlap");
		}

		int maxIndex = (int) Math.pow(2, variables.size()) - 1;
		checkIndex(mintermSet, maxIndex, "minterm");
		checkIndex(dontCareSet, maxIndex, "dontcare");

		this.mintermSet = mintermSet;
		this.dontCareSet = dontCareSet;
		this.variables = variables;

		Set<Mask> primCover = findPrimaryImplicants();

		printLog();

		minimalForms = chooseMinimalCover(primCover);
	}

	/**
	 * Checks the set values if it contains a value greather than
	 * {@code maxIndex}.
	 *
	 * @param set
	 *            the integer set
	 * @param maxIndex
	 *            the max index
	 * @param name
	 *            the name of the set
	 * @throws IllegalArgumentException
	 *             if contains a value greather than {@code maxIndex}
	 */
	private void checkIndex(Set<Integer> set, int maxIndex, String name) {
		for (Integer index : set) {
			if (index > maxIndex) {
				throw new IllegalArgumentException(
						String.format("Invalid %s index, maximum: %d, was: %d", name, maxIndex, index));
			}
		}
	}

	/**
	 * Prints the log from the process of finding the primary implicants.
	 */
	private void printLog() {
		for (Tuple<Level, Mask, String> log : logQueue) {
			if (log.getSecond() != null && log.getThird() != null && log.getSecond().isCombined()) {
				continue;
			}

			StringBuilder logMsg = new StringBuilder();
			if (log.getThird() != null) {
				logMsg.append(log.getThird());
			}
			if (log.getSecond() != null) {
				logMsg.append(log.getSecond().toString());
			}

			LOG.log(log.getFirst(), () -> logMsg.toString());
		}
	}

	/**
	 * Finds primary implicants. This is the first them of the QMC method.
	 *
	 * @return the set of primary implicants
	 */
	private Set<Mask> findPrimaryImplicants() {
		Set<Mask> primaryImplicants = new LinkedHashSet<>();

		List<Set<Mask>> columns = createFirstColumn();
		for (int i = 1; !columns.isEmpty(); i++) {
			columns = createOtherColumn(columns, i);
			for (Set<Mask> column : columns) {
				for (Mask mask : column) {
					if (mask.isDontCare()) {
						continue;
					}
					implicantsQueue.add(mask);
					implicantsLog
							.add(new Tuple<Level, Mask, String>(Level.FINEST, mask, "Pronašao primarni implikant: "));
				}
			}
			logPrimaryImplicants();
		}

		getPrimaryImplicants(primaryImplicants);
		logQueue.add(new Tuple<Level, Mask, String>(Level.FINE, null, "Svi primarni implikanti: "));
		primaryImplicants.forEach(m -> logQueue.add(new Tuple<Level, Mask, String>(Level.FINE, m, null)));

		return primaryImplicants;
	}

	/**
	 * Logs primary implicants.
	 */
	private void logPrimaryImplicants() {
		implicantsLog.forEach(log -> logQueue.add(log));
		implicantsLog.clear();
	}

	/**
	 * Gets the primary implicants.
	 *
	 * @param primaryImplicants
	 *            the primary implicants
	 * @return the primary implicants
	 */
	private void getPrimaryImplicants(Set<Mask> primaryImplicants) {
		implicantsQueue.stream().filter(implicant -> !implicant.isCombined())
				.forEach(implicant -> primaryImplicants.add(implicant));
	}

	/**
	 * Creates the other column in the implicants and minterms table.
	 *
	 * @param columns
	 *            the list of columns
	 * @param iteration
	 *            the current iteration
	 * @return the list of implicants in the current column
	 */
	private List<Set<Mask>> createOtherColumn(List<Set<Mask>> columns, int iteration) {
		List<Set<Mask>> column = new ArrayList<>();
		Set<Mask> primaryImplicants = new LinkedHashSet<>();

		if (columns.size() > 1) {
			logQueue.add(new Tuple<Level, Mask, String>(Level.FINER, null, "Stupac tablice:"));
			logQueue.add(new Tuple<Level, Mask, String>(Level.FINER, null, "================================="));
		}

		int numberOfVariables = variables.size();
		for (int i = 0; i <= variables.size() - iteration; i++) {
			for (int j = 0; j < columns.size() - 1; j++) {
				Set<Mask> masks1 = columns.get(j);
				Set<Mask> masks2 = columns.get(j + 1);
				for (Mask m1 : masks1) {
					for (Mask m2 : masks2) {
						if (m1.getIndexes().containsAll(m2.getIndexes())) {
							continue;
						}
						Optional<Mask> combined = m1.combineWith(m2);
						if (!combined.isPresent()) {
							continue;
						}

						m1.setCombined(true);
						m2.setCombined(true);
						Mask m3 = combined.get();
						if (m3.countOfOnes() != i) {
							continue;
						}
						if (primaryImplicants.add(m3)) {
							logQueue.add(new Tuple<Level, Mask, String>(Level.FINER, m3, null));
						}
					}
				}
			}

			if (!primaryImplicants.isEmpty()) {
				column.add(primaryImplicants);
				String logMsg = i == numberOfVariables - iteration ? "" : "-------------------------------";
				logQueue.add(new Tuple<Level, Mask, String>(Level.FINER, null, logMsg));
			}
		}

		return column;
	}

	/**
	 * Creates the first column in the implicants and minterms table.
	 *
	 * @return the list of implicants in the first column
	 */
	private List<Set<Mask>> createFirstColumn() {
		List<Set<Mask>> column = new ArrayList<>();
		Set<Mask> primaryImplicants = new LinkedHashSet<>();

		logQueue.add(new Tuple<Level, Mask, String>(Level.FINER, null, "Stupac tablice:"));
		logQueue.add(new Tuple<Level, Mask, String>(Level.FINER, null, "================================="));

		int numberOfVariables = variables.size();
		for (int i = 0; i <= numberOfVariables; i++) {
			for (Integer minterm : mintermSet) {
				Mask mask = new Mask(minterm, numberOfVariables, false);
				if (mask.countOfOnes() == i) {
					primaryImplicants.add(mask);
					logQueue.add(new Tuple<Level, Mask, String>(Level.FINER, mask, null));
				}
			}

			for (Integer dontCare : dontCareSet) {
				Mask mask = new Mask(dontCare, numberOfVariables, true);
				if (mask.countOfOnes() == i) {
					primaryImplicants.add(mask);
					logQueue.add(new Tuple<Level, Mask, String>(Level.FINER, mask, null));
				}
			}
			column.add(primaryImplicants);
			String logMsg = i == numberOfVariables ? "" : "-------------------------------";
			logQueue.add(new Tuple<Level, Mask, String>(Level.FINER, null, logMsg));
		}

		return column;
	}

	/**
	 * This method generates a cover table of minterms with primary implicants
	 * and searches for important primary implicants. They will certanly be
	 * added in the minimal form.
	 *
	 * @param primCover
	 *            the primary implicants set
	 * @return the list of minimal forms
	 */
	private List<Set<Mask>> chooseMinimalCover(Set<Mask> primCover) {
		// Izgradi polja implikanata i minterma (rub tablice):
		Mask[] implicants = primCover.toArray(new Mask[primCover.size()]);
		Integer[] minterms = mintermSet.toArray(new Integer[mintermSet.size()]);
		// Mapiraj minterm u stupac u kojem se nalazi:
		Map<Integer, Integer> mintermToColumnMap = new HashMap<>();
		for (int i = 0; i < minterms.length; i++) {
			Integer index = minterms[i];
			mintermToColumnMap.put(index, i);
		}
		// Napravi praznu tablicu pokrivenosti:
		boolean[][] table = buildCoverTable(implicants, minterms, mintermToColumnMap);
		// Donji redak tablice: koje sam minterme pokrio?
		boolean[] coveredMinterms = new boolean[minterms.length];
		// Pronađi primarne implikante...
		Set<Mask> importantSet = selectImportantPrimaryImplicants(implicants, mintermToColumnMap, table,
				coveredMinterms);

		LOG.log(Level.FINE, () -> "");
		LOG.log(Level.FINE, () -> "Bitni primarni implikanti su:");
		importantSet.forEach(m -> {
			LOG.log(Level.FINE, () -> m.toString());
		});

		// Izgradi funkciju pokrivenosti:
		List<Set<BitSet>> pFunction = buildPFunction(table, coveredMinterms);

		LOG.log(Level.FINER, () -> "");
		LOG.log(Level.FINER, () -> "p funkcija je:");
		LOG.log(Level.FINER, () -> pFunction.toString());

		// Pronađi minimalne dopune:
		Set<BitSet> minset = findMinimalSet(pFunction);

		LOG.log(Level.FINER, () -> "");
		LOG.log(Level.FINER, () -> "Minimalna pokrivanja još trebaju:");
		LOG.log(Level.FINER, () -> minset.toString());

		// Izgradi minimalne zapise funkcije:
		List<Set<Mask>> minimalForms = new ArrayList<>();

		for (BitSet bs : minset) {
			Set<Mask> set = new LinkedHashSet<>(importantSet);
			bs.stream().forEach(i -> set.add(implicants[i]));
			minimalForms.add(set);
		}

		LOG.log(Level.FINE, () -> "");
		LOG.log(Level.FINE, () -> "Minimalni oblici funkcije su:");

		int numOfForms = 1;
		for (Set<Mask> form : minimalForms) {
			String msg = String.format("%d. %s", numOfForms++, form.toString());
			LOG.log(Level.FINE, () -> msg);
		}

		return minimalForms;
	}

	/**
	 * Finds the minimal set by converting the {@code pFunction} into sum of
	 * products and then gets the minimum cardinality of that set and adds all
	 * the minterms in the returned set that have this cardinality.
	 *
	 * @param pFunction
	 *            the function of covered minterms
	 * @return the minimal set
	 */
	private Set<BitSet> findMinimalSet(List<Set<BitSet>> pFunction) {
		Set<BitSet> sumOfProducts = new LinkedHashSet<>();

		for (int i = 0; i < pFunction.size() - 1; i++) {
			Set<BitSet> sum1 = pFunction.get(i);
			Set<BitSet> sum2 = pFunction.get(i + 1);

			if (!sumOfProducts.isEmpty()) {
				sum1.clear();
				sum1.addAll(sumOfProducts);
				sumOfProducts.clear();
			}

			for (BitSet bit1 : sum1) {
				for (BitSet bit2 : sum2) {
					BitSet bit = new BitSet();
					bit.or(bit1);
					bit.or(bit2);
					sumOfProducts.add(bit);
				}
			}
		}

		LOG.log(Level.FINER, () -> "");
		LOG.log(Level.FINER, () -> "Nakon prevorbe p-funkcije u sumu produkata:");
		LOG.log(Level.FINER, () -> sumOfProducts.toString());

		int minimum = Collections.min(sumOfProducts,
				(b1, b2) -> b1.cardinality() < b2.cardinality() ? -1 : b1.cardinality() == b2.cardinality() ? 0 : 1)
				.cardinality();

		Set<BitSet> minimalSet = new LinkedHashSet<>();
		sumOfProducts.stream().filter(bit -> bit.cardinality() == minimum).forEach(bit -> minimalSet.add(bit));

		return minimalSet;
	}

	/**
	 * Builds the function of covered minterms.
	 *
	 * @param table
	 *            the truth table
	 * @param coveredMinterms
	 *            the covered minterms
	 * @return the function of covered minterms
	 */
	private List<Set<BitSet>> buildPFunction(boolean[][] table, boolean[] coveredMinterms) {
		List<Set<BitSet>> pFunction = new ArrayList<>();

		for (int i = 0; i < coveredMinterms.length; i++) {
			Set<BitSet> sum = new LinkedHashSet<>();
			if (coveredMinterms[i] == true) {
				continue;
			}
			for (int j = 0; j < table.length; j++) {
				if (table[j][i] == true) {
					BitSet set = new BitSet();
					set.set(j);
					sum.add(set);
				}
			}
			pFunction.add(sum);
		}

		return pFunction;
	}

	/**
	 * Gets important primary implicants. It searches those primary implicants
	 * that cover only one minterm in the cover table.
	 *
	 * @param implicants
	 *            the implicants
	 * @param mintermToColumnMap
	 *            the minterm to column map
	 * @param table
	 *            the truth table
	 * @param coveredMinterms
	 *            the covered minterms
	 * @return the important implicants set
	 */
	private Set<Mask> selectImportantPrimaryImplicants(Mask[] implicants, Map<Integer, Integer> mintermToColumnMap,
			boolean[][] table, boolean[] coveredMinterms) {
		Set<Mask> importantImplicants = new LinkedHashSet<>();

		for (int j = 0, covered = 0, index = 0; j < table[0].length; j++, covered = 0) {
			for (int i = 0; i < table.length; i++) {
				if (table[i][j] == true) {
					covered++;
					index = i;
				}
			}
			if (covered == 1) {
				importantImplicants.add(implicants[index]);
			}
		}

		mintermToColumnMap.forEach((k, v) -> {
			importantImplicants.forEach(m -> {
				if (m.getIndexes().contains(k)) {
					coveredMinterms[v] = true;
				}
			});
		});

		return importantImplicants;
	}

	/**
	 * Builds the cover table where element at position (i,j) is true if ith
	 * primary implicant covers the minterm which is localted in the jth column
	 * of the table.
	 *
	 * @param implicants
	 *            the implicants
	 * @param minterms
	 *            the minterms
	 * @param mintermToColumnMap
	 *            the minterm to column map
	 * @return the cover table
	 */
	private boolean[][] buildCoverTable(Mask[] implicants, Integer[] minterms,
			Map<Integer, Integer> mintermToColumnMap) {
		boolean[][] table = new boolean[implicants.length][minterms.length];

		mintermToColumnMap.forEach((k, v) -> {
			for (int i = 0; i < table.length; i++) {
				if (implicants[i].getIndexes().contains(k)) {
					table[i][v] = true;
				}
			}
		});

		return table;
	}

	/**
	 * Gets the minimal forms of a boolean function.
	 *
	 * @return the list of minimal forms
	 */
	public List<Set<Mask>> getMinimalForms() {
		return minimalForms;
	}

	/**
	 * Gets the minimal forms as {@link Node} expressions.
	 *
	 * @return the list of minimal forms as expressions
	 */
	public List<Node> getMinimalFormsAsExpressions() {
		List<Node> forms = new ArrayList<>();

		for (Set<Mask> minimalForm : minimalForms) {
			List<Node> ors = new ArrayList<>();
			for (Mask m : minimalForm) {
				List<Node> ands = new ArrayList<>();
				for (int i = 0; i < m.size(); i++) {
					byte value = m.getValueAt(i);
					if (value >= 2) {
						continue;
					}
					Node var = new VariableNode(String.valueOf(getLetterByIndex(i)));
					if (value == 0) {
						Node not = new UnaryOperatorNode("NOT", var, v -> !v);
						ands.add(not);
						continue;
					}
					ands.add(var);
				}
				Node and = new BinaryOperatorNode("AND", ands, (v1, v2) -> Boolean.logicalAnd(v1, v2));
				ors.add(and);
			}
			Node or = new BinaryOperatorNode("OR", ors, (v1, v2) -> Boolean.logicalOr(v1, v2));
			forms.add(or);
		}

		return forms;
	}

	/**
	 * Gets the minimal forms as a string which can later be parsed as a boolean
	 * function.
	 *
	 * @return the list of minimal forms as a string
	 */
	public List<String> getMinimalFormsAsString() {
		List<String> forms = new ArrayList<>();

		for (Set<Mask> minimalForm : minimalForms) {
			StringBuilder form = new StringBuilder();
			int index = 0;
			for (Mask m : minimalForm) {
				if (index != 0) {
					form.append("OR ");
				}
				for (int i = 0, j = 0; i < m.size(); i++) {
					byte value = m.getValueAt(i);
					if (value >= 2) {
						continue;
					}
					if (j > 0) {
						form.append("AND ");
					}
					if (value == 0) {
						form.append("NOT ");
					}
					form.append(getLetterByIndex(i) + " ");
					j++;
				}
				index++;
			}
			form.deleteCharAt(form.length() - 1);
			forms.add(form.toString());
		}

		return forms;
	}

	/**
	 * Gets the letter by index in alphabet.
	 *
	 * @param index
	 *            the index
	 * @return the letter by index
	 */
	private char getLetterByIndex(int index) {
		return (char) (index + 'A');
	}
}
