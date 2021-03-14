package tfidf_implementacao_mutex;

import java.util.HashMap;

public class Word {
	private String value;
	private HashMap<String, Double> td;
	private double idf;
	private double tdidf;
	private int qtyOfTextThaHaveThisWord;
	
	public Word(String newValue) {
		value = newValue;
		td = new HashMap<String, Double>();
		idf = 0;
		qtyOfTextThaHaveThisWord = 0;
	}
	
	public String getValue() {
		return value;
	}

	public double getTd(String nameOfText) {
		return td.get(nameOfText);
	}

	public void addTd(String nameOfText, double tdOftext) {
		td.put(nameOfText, tdOftext);
	}

	public double getIdf() {
		calculateIdf();
		return idf;
	}
	
	public void calculateIdf() {
		if(qtyOfTextThaHaveThisWord != 0) {
			idf = Math.log10((double)qtyOfTextThaHaveThisWord / Dataset.sharedDataset.getQtyOfDocuments());
		}else {
			idf = 0;
		}
	}

	public void setIdf(double newIdf) {
		idf = newIdf;
	}

	public double getTdidf(String nameOfText) {
		return (double)td.get(nameOfText) * getIdf();
	}

	public void setTdidf(double newTdidf) {
		tdidf = newTdidf;
	}

	public int getQtyOfTextThaHaveThisWord() {
		return qtyOfTextThaHaveThisWord;
	}

	/*
	 * Região critica, so uma thread por vez pode atualize o numero de textos que contem a palavra.
	 * 
	 * */
	public synchronized void updateQtyOfTextThaHaveThisWord() {
		qtyOfTextThaHaveThisWord++;
	}
	
}
