package hr.fer.zemris.cmdapps.trazilica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The {@code DocumentNode} class encapsulates content of one file which
 * contributes to vocabulary of search engine and for whose content search can
 * be performed. Internally it uses the tf-idf (term frequency-inverse document
 * frequency) vector.</a>.
 * 
 * @author Karlo Bencic
 * 
 */
public class DocumentNode {

	/** The file name. */
	private String fileName;
	
	/** The file tokens. */
	private List<String> fileTokens;
	
	/** The tf-idf vector. */
	private List<Double> tfIdfVector;

	/**
	 * Instantiates a new {@code DocumentNode} with given file name and tokens
	 * retrieved from file which will be encapsulated.
	 * 
	 * @param fileName
	 *            file name
	 * @param fileTokens
	 *            file tokens
	 */
	public DocumentNode(String fileName, List<String> fileTokens) {
		this.fileName = fileName;
		this.fileTokens = fileTokens;
	}

	/**
	 * Gets the file name.
	 * 
	 * @return file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the file name.
	 * 
	 * @param fileName
	 *            file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * File tokens retrieved from the file.
	 * 
	 * @return file tokens
	 */
	public List<String> getFileTokens() {
		return Collections.unmodifiableList(fileTokens);
	}

	/**
	 * Sets file tokens retrieved from the file.
	 * 
	 * @param fileTokens
	 *            new file tokens
	 */
	public void setFileTokens(List<String> fileTokens) {
		this.fileTokens = fileTokens;
	}

	/**
	 * Gets the tf-idf (term frequency-inverse document frequency) vector
	 * calculated for this {@link DocumentNode}.
	 * 
	 * @return tf-idf vector
	 */
	public List<Double> getTfIdfVector() {
		return tfIdfVector;
	}

	/**
	 * Calculates the value of tf-idf (term frequency-inverse document
	 * frequency) vector for this node's file.
	 * 
	 * @param vocabulary
	 *            vocabulary
	 */
	public void calculateTfIdfVector(Vocabulary vocabulary) {
		Set<String> vocabularyWords = vocabulary.getVocabularyWords();
		tfIdfVector = new ArrayList<>(vocabularyWords.size());
		int position = 0;
		for (String word : vocabularyWords) {
			if (!fileTokens.contains(word)) {
				tfIdfVector.add(position++, 0.0);
				continue;
			}

			int tf = VectorUtil.tf(word, fileTokens);
			double idf = vocabulary.getInverseDocumentFrequency(word);
			tfIdfVector.add(position++, tf * idf);
		}
	}

	@Override
	public String toString() {
		return fileTokens.toString();
	}
}