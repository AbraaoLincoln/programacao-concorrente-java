import java.util.ArrayList;

public class Main{
	
	public static void main(String[] args) throws InterruptedException{
//		System.out.println(Runtime.getRuntime().availableProcessors());
		Object lock = new Object();
		Dataset.sharedDataset = new Dataset("./dataset");
		String[] words = {"hello", "world", "today", "ok", "visit"};
		ArrayList<Word> listOfWords = new ArrayList<Word>();
		Runnable tx1 = new TextAnalyze(listOfWords, lock);
		Thread t = new Thread(tx1);
		
		for(String w : words) {
			listOfWords.add(new Word(w));
		}
		t.start();
		
		synchronized (lock) {
			lock.wait();
			for(Word w : listOfWords) {
				System.out.println(w.getTd("doc1"));
			}
		}

	}

}
