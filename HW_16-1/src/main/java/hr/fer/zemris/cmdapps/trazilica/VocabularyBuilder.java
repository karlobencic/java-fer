package hr.fer.zemris.cmdapps.trazilica;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * The {@code VocabularyBuilder} class is responsible for building the
 * {@link Vocabulary}.
 * 
 * @author Karlo Bencic
 * 
 */
public class VocabularyBuilder {

	/** Thread pool used for speed enhancements during vocabulary build. */
	private static ExecutorService pool;

	/**
	 * Method that creates and populates the {@code Vocabulary} with information
	 * stored in directory with the given path.
	 * 
	 * @param directoryPath
	 *            path to directory with vocabulary files
	 * @return populated vocabulary
	 */
	public static Vocabulary buildVocabulary(String directoryPath) throws IOException {
		Path pathToDirectory = Paths.get(directoryPath);
		Path pathToStoppingWords = Paths.get(Main.RELATIVE_PATH_TO_STOPPING_WORDS);

		if (!Files.isDirectory(pathToDirectory)) {
			throw new RuntimeException("Given path does not lead to a directory.");
		}

		if (!Files.isRegularFile(pathToStoppingWords)) {
			throw new RuntimeException("Given path does not lead to a file.");
		}

		pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), (r) -> {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		});

		Vocabulary vocabulary = new Vocabulary();
		
		loadStoppingWords(vocabulary, pathToStoppingWords);
		loadDocuments(vocabulary, pathToDirectory);
		vocabulary.calculateInverseDocumentFrequency();
		computeVectors(vocabulary);

		System.out.println("Vocabulary size: " + vocabulary.getVocabularyWords().size());

		return vocabulary;
	}

	/**
	 * Loads the stopping words into the vocabulary from the file located on the
	 * given path.
	 * 
	 * @param vocabulary
	 *            vocabulary to be populated with stopping words
	 * @param pathToStoppingWords
	 *            path to file with declared stopping words
	 */
	private static void loadStoppingWords(Vocabulary vocabulary, Path pathToStoppingWords) throws IOException {
		List<String> stoppingWords = Files.readAllLines(pathToStoppingWords, StandardCharsets.UTF_8);
		vocabulary.addStoppingWords(stoppingWords);
	}

	/**
	 * Loads documents into the vocabulary from the file located on the given
	 * path whose words will build search engine vocabulary, and whose content
	 * can be searched for through search engine.
	 * 
	 * @param vocabulary
	 *            vocabulary to be populated with documents
	 * @param pathToDirectory
	 *            path to directory with documents
	 */
	private static void loadDocuments(Vocabulary vocabulary, Path pathToDirectory) {
		List<Future<Void>> documentParsingResults = new ArrayList<>();
		try {
			Files.walk(pathToDirectory).forEach(file -> {
				if (Files.isRegularFile(file)) {
					documentParsingResults.add(pool.submit(new Parser(vocabulary, file)));
				}
			});
		} catch (IOException e) {
			System.err.println("An error ocurred during reading search engine files.");
			return;
		}

		for (Future<Void> result : documentParsingResults) {
			try {
				result.get();
			} catch (InterruptedException | ExecutionException ignorable) {
			}
		}
	}

	/**
	 * Worker thread which performs document parsing and saves file content
	 * encapsulated as {@link DocumentNode} object.
	 * 
	 * @author Karlo Bencic
	 * 
	 */
	private static class Parser implements Callable<Void> {

		/** Vocabulary to which document belongs. */
		private Vocabulary vocabulary;

		/** File to be parsed by this worker. */
		private Path file;

		/**
		 * Instantiates a new parser.
		 * 
		 * @param vocabulary
		 *            vocabulary to which document belongs
		 * @param file
		 *            file
		 */
		public Parser(Vocabulary vocabulary, Path file) {
			this.vocabulary = vocabulary;
			this.file = file;
		}

		@Override
		public Void call() throws Exception {
			List<String> fileTokens = DocumentParser.parseFile(file, vocabulary.getStoppingWords());
			DocumentNode documentNode = new DocumentNode(file.getFileName().toString(), fileTokens);
			vocabulary.addVocabularyWords(fileTokens);
			vocabulary.addDocument(documentNode);
			return null;
		}
	}

	/**
	 * Computes necessary tf-idf document vectors for all documents which
	 * populate the given vocabulary.
	 * 
	 * @param vocabulary
	 *            vocabulary with documents
	 */
	private static void computeVectors(Vocabulary vocabulary) {
		List<Future<Void>> vectorComputationResults = new ArrayList<>();

		for (DocumentNode document : vocabulary.getDocuments()) {
			vectorComputationResults.add(pool.submit(new VectorComputationJob(vocabulary, document)));
		}

		for (Future<Void> result : vectorComputationResults) {
			try {
				result.get();
			} catch (InterruptedException | ExecutionException ignorable) {
			}
		}
	}

	/**
	 * Worker thread which performs computation of tf-idf document vectors.
	 * 
	 * @author Karlo Bencic
	 * 
	 */
	private static class VectorComputationJob implements Callable<Void> {

		/** Document vocabulary. */
		private Vocabulary vocabulary;

		/** Document whose tf-idf vector needs to be produced */
		private DocumentNode document;

		/**
		 * Instantiates a new vector computation job.
		 * 
		 * @param vocabulary
		 *            document vocabulary
		 * @param document
		 *            document Document whose tf-idf vector needs to be produced
		 */
		public VectorComputationJob(Vocabulary vocabulary, DocumentNode document) {
			this.vocabulary = vocabulary;
			this.document = document;
		}

		@Override
		public Void call() throws Exception {
			document.calculateTfIdfVector(vocabulary);
			return null;
		}
	}
}