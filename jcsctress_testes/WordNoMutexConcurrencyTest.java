package dataset_word_testes;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

import java.util.HashMap;

public class WordNoMutexConcurrencyTest {
	@State
	public static class Word {
		private String value;
		private HashMap<String, Double> td;
		private double idf;
		private double tdidf;
		protected int qtyOfTextThaHaveThisWord;
		
		public Word() {qtyOfTextThaHaveThisWord = 0;}
		
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

//		public double getIdf() {
//			calculateIdf();
//			return idf;
//		}
//		
//		public void calculateIdf() {
//			if(qtyOfTextThaHaveThisWord != 0) {
//				idf = Math.log10((double)qtyOfTextThaHaveThisWord / Dataset.sharedDataset.getQtyOfDocuments());
//			}else {
//				idf = 0;
//			}
//		}

		public void setIdf(double newIdf) {
			idf = newIdf;
		}

//		public double getTdidf(String nameOfText) {
//			return (double)td.get(nameOfText) * getIdf();
//		}

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
		public void updateQtyOfTextThaHaveThisWord() {
			qtyOfTextThaHaveThisWord++;
		}
		
	}
	
	@JCStressTest
	@Outcome(id = "2", expect = Expect.ACCEPTABLE, desc = "Both updates.")
	@Outcome(id = "1", expect = Expect.FORBIDDEN, desc = "One update lost.")
	@State
	public static class WordTeste extends Word{
		@Actor
		public void c1() {
			updateQtyOfTextThaHaveThisWord();
		}
		
		@Actor
		public void c2() {
			updateQtyOfTextThaHaveThisWord();
		}
		
		@Arbiter
	    public void arbiter(I_Result r) {
	        r.r1 = qtyOfTextThaHaveThisWord;
	    }
	}
}
