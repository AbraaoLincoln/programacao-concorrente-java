package tfidf_implementacao_CallableFuture;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

public class Word implements Callable<ArrayList<Double>>{
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
	
	@Override
	public ArrayList<Double> call() throws Exception {
		ArrayList<Double> result = new ArrayList<>();
		calculateIdf();
		File[] docs = Dataset.sharedDataset.getListsOfDocuments();
		for(int i = 0; i < docs.length; i++) {
			result.add(getTdidf(docs[i].getName()));
		}
//		for(File f : Dataset.sharedDataset.getListsOfDocuments()) {
//			result.add(getTdidf(f.getName()));
//		}
		return result;
	}
}
