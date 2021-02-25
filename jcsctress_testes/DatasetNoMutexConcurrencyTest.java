package dataset_word_testes;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

import java.io.File;


public class DatasetNoMutexConcurrencyTest{
	@State
	public static class Dataset {
		private File dataset;
		private File[] listsOfDocuments;
		protected int indexDocumentToAnalyze;
		public static Dataset sharedDataset;
		
		
		public Dataset() {
			dataset = openDataset("./");
			listsOfDocuments = dataset.listFiles();
			indexDocumentToAnalyze = 0;
		};
		
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
		public File getDocumentToAnalyze(II_Result r, int threadId) {
			//Para testar o valor que cada thread ver.
			if(threadId == 1) {
				r.r1 = indexDocumentToAnalyze;
			}else {
				r.r2 = indexDocumentToAnalyze;
			}
			
			if(indexDocumentToAnalyze < listsOfDocuments.length) {
				//indexDocumentToAnalyze++;
				return listsOfDocuments[indexDocumentToAnalyze++];
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
	
	@JCStressTest
	@Outcome(id = "0, 1", expect = Expect.ACCEPTABLE, desc = "Both updates.")
	@Outcome(id = "1, 0", expect = Expect.ACCEPTABLE, desc = "Both updates.")
	@Outcome(id = "0, 0", expect = Expect.FORBIDDEN, desc = "One update lost.")
	@State
	public static class DatasetTeste extends Dataset{
		@Actor
		public void c1(II_Result r) {
			getDocumentToAnalyze(r, 1);
		}
		
		@Actor
		public void c2(II_Result r) {
			getDocumentToAnalyze(r, 2);
		}
	}
}



