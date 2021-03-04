package tdidf_implementacao_atomic;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Word {
	private String value;
	private HashMap<String, Double> td;
	private double idf;
	private double tdidf;
	private AtomicInteger qtyOfTextThaHaveThisWord;
	
	public Word(String newValue) {
		value = newValue;
		td = new HashMap<String, Double>();
		idf = 0;
		qtyOfTextThaHaveThisWord = new AtomicInteger();
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
		if(qtyOfTextThaHaveThisWord.get() != 0) {
			idf = Math.log10((double)qtyOfTextThaHaveThisWord.get() / Dataset.sharedDataset.getQtyOfDocuments());
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
		return qtyOfTextThaHaveThisWord.get();
	}

	/*
	 * Região critica, so uma thread por vez pode atualize o numero de textos que contem a palavra.
	 * 
	 * */
	public void updateQtyOfTextThaHaveThisWord() {
		while(true) {
			int currentValue = qtyOfTextThaHaveThisWord.get();
			int newValue = currentValue + 1;
			
			if(qtyOfTextThaHaveThisWord.compareAndSet(currentValue, newValue)) {
				return;
			}
		}
	}
	
}
