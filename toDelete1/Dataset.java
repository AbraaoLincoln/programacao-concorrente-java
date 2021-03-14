package tdidf_implementacao_1;

import java.io.File; 

public class Dataset {
	private File dataset;
	private File[] listsOfDocuments;
	private int indexDocumentToAnalyze;
	public static Dataset sharedDataset;

	public Dataset(String location) {
		dataset = openDataset(location);
		listsOfDocuments = dataset.listFiles();
		indexDocumentToAnalyze = 0;
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
		if(indexDocumentToAnalyze < listsOfDocuments.length) {
			indexDocumentToAnalyze++;
			return listsOfDocuments[indexDocumentToAnalyze - 1];
		}else {
			return null;
		}
	}
	
	public Boolean stillHasTextToAnalyze() {
		if(indexDocumentToAnalyze >= listsOfDocuments.length) {
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
