package hr.fer.zemris.cmdapps.trazilica;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The {@code Vocabulary} class represents a vocabulary of words which can be
 * searched in documents and which contribute to search results, and stopping
 * words that will be disregarded and which do not contribute to search results.
 * 
 * @author Karlo Bencic
 * 
 */
public class Vocabulary {

	/** The vocabulary. */
	private Set<String> vocabulary = new LinkedHashSet<>();

	/** The stop words. */
	private Set<String> stopWords = ConcurrentHashMap.newKeySet();

	/** The inverse document frequency. */
	private Map<String, Double> inverseDocumentFrequency = new HashMap<>();

	/** The documents. */
	private List<DocumentNode> documents = new CopyOnWriteArrayList<>();

	/**
	 * Gets the inverse document frequency for word passed as argument.
	 * 
	 * @param vocabularyWord
	 *            word whose IDF value is requested
	 * @return IDF value of passed word or {@code null} if the word is not
	 *         present in the vocabulary
	 */
	public double getInverseDocumentFrequency(String vocabularyWord) {
		return inverseDocumentFrequency.get(vocabularyWord);
	}

	/**
	 * Calculates inverse document frequency value for all words currently
	 * present in the vocabulary.
	 */
	public void calculateInverseDocumentFrequency() {
		inverseDocumentFrequency = VectorUtil.idf(documents);
	}

	/**
	 * Gets the list of documents whose words populate this vocabulary.
	 * 
	 * @return documents
	 */
	public List<DocumentNode> getDocuments() {
		return Collections.unmodifiableList(documents);
	}

	/**
	 * Adds document to list of this vocabulary's documents.
	 * 
	 * @param documentNode
	 *            document to be added
	 */
	public void addDocument(DocumentNode documentNode) {
		documents.add(documentNode);
	}

	/**
	 * Gets the words that can be searched in documents and which contribute to
	 * search results.
	 * 
	 * @return collection of words that can be searched
	 */
	public Set<String> getVocabularyWords() {
		return vocabulary;
	}

	/**
	 * Adds new words that can be searched and which contribute to search
	 * result.
	 * 
	 * @param newVocabularyWords
	 *            words to be added to vocabulary
	 */
	public synchronized void addVocabularyWords(Collection<String> newVocabularyWords) {
		vocabulary.addAll(newVocabularyWords);
	}

	/**
	 * Gets the words that will be ignored and which do not contribute to search
	 * results.
	 * 
	 * @return words that will be ignored
	 */
	public Set<String> getStoppingWords() {
		return Collections.unmodifiableSet(stopWords);
	}

	/**
	 * Adds new stopping words to this vocabulary.
	 * 
	 * @param newStoppingWords
	 *            stopping words to be added to vocabulary
	 */
	public void addStoppingWords(Collection<String> newStoppingWords) {
		stopWords.addAll(newStoppingWords);
	}
}