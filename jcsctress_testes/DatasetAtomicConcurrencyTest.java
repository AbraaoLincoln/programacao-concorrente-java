package dataset_word_testes;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

public class DatasetAtomicConcurrencyTest {
	@State
	public static class Dataset {
		private File dataset;
		private File[] listsOfDocuments;
		protected AtomicInteger indexDocumentToAnalyze;
		public static Dataset sharedDataset;
		
		
		public Dataset() {
			dataset = openDataset("./");
			listsOfDocuments = dataset.listFiles();
			indexDocumentToAnalyze = new AtomicInteger(0);
		};
		
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
		public File getDocumentToAnalyze(II_Result r, int threadId) {
			while(true) {
				int currentIndex = indexDocumentToAnalyze.get();
				int newValue = currentIndex + 1;
				
				if(indexDocumentToAnalyze.compareAndSet(currentIndex, newValue)) {
					//Para testar o valor que cada thread ver.
					if(threadId == 1) {
						r.r1 = currentIndex;
					}else {
						r.r2 = currentIndex;
					}
					
					
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

