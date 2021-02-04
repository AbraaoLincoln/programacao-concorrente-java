import java.util.HashMap;

public class Word {
	private String value;
	private HashMap<Integer, Double> td;
	private double idf;
	private double tdidf;
	private int qtyOfTextThaHaveThisWord;
	
	public Word(String newValue) {
		value = newValue;
		td = new HashMap<Integer, Double>();
		idf = 0;
		tdidf = 0;
		qtyOfTextThaHaveThisWord = 0;
	}
	
	public String getValue() {
		return value;
	}

	public double getTd(int numOftheText) {
		return td.get(numOftheText);
	}

	public void addTd(int indexOfText, double tdOftext) {
		td.put(indexOfText, tdOftext);
	}

	public double getIdf() {
		return idf;
	}

	public void setIdf(double newIdf) {
		idf = newIdf;
	}

	public double getTdidf() {
		return tdidf;
	}

	public void setTdidf(double newTdidf) {
		tdidf = newTdidf;
	}

	public int getQtyOfTextThaHaveThisWord() {
		return qtyOfTextThaHaveThisWord;
	}

	public void updateQtyOfTextThaHaveThisWord() {
		qtyOfTextThaHaveThisWord++;
	}
	
	
	
	
}
