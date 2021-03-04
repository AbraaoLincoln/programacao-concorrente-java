package tdidf_implementacao_atomic;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger; 

public class Dataset {
	private File dataset;
	private File[] listsOfDocuments;
	private AtomicInteger indexDocumentToAnalyze;
	public static Dataset sharedDataset;

	public Dataset(String location) {
		dataset = openDataset(location);
		listsOfDocuments = dataset.listFiles();
		indexDocumentToAnalyze = new AtomicInteger(0);
	}
	
	private File openDataset(String location) {
		return new File(location);
	}
	
	/*
	 * Região critica, so uma thread por vez pode pegar o documento ao qual ela vai analizar
	 * assim evitando que duas ou mais threads analizem o mesmo documento.
	 * 
	 * */
	public File getDocumentToAnalyze() {
		while(true) {
			int currentIndex = indexDocumentToAnalyze.get();
			int newValue = currentIndex + 1;
			
			if(indexDocumentToAnalyze.compareAndSet(currentIndex, newValue)) {
				if(currentIndex < listsOfDocuments.length) {
					return listsOfDocuments[currentIndex];
				}else {
					return null;
				}
			}
		}
	}
	
	public Boolean stillHasTextToAnalyze() {
		if(indexDocumentToAnalyze.get() >= listsOfDocuments.length) {
			return false;
		}else {
			return true;
		}
	}
	
	public File[] getListsOfDocuments() {
		return listsOfDocuments;
	}
	
	public int getQtyOfDocuments() {
		return listsOfDocuments.length;
	}
}
