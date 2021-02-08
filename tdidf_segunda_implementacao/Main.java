import java.io.File;
import java.util.ArrayList;

public class Main{
	
	public static void main(String[] args) throws InterruptedException{
//		System.out.println(Runtime.getRuntime().availableProcessors());
		Dataset.sharedDataset = new Dataset("./dataset_small");
		String[] words = {"hello", "world", "today", "ok", "visit", "everyone"};
		ArrayList<Word> listOfWords = new ArrayList<Word>();
		ArrayList<Thread> threads;
		
		for(String w : words) {
			listOfWords.add(new Word(w));
		}
		threads = createThreads(listOfWords);
		//Inicializando as threads
		for(Thread t : threads) {
			t.start();
		}
		//Esperando todas as threads terminarem
		for(Thread t : threads) {
			t.join();
		}
		
		printResult(listOfWords);

	}
	
	public static ArrayList<Thread> createThreads(ArrayList<Word> listOfWords) {
		ArrayList<Thread> threads = new ArrayList<>();
		
		for(int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
			Runnable newThread = new TextAnalyze(listOfWords);
			threads.add(new Thread(newThread));
		}
		
		return threads;
	}
	
	public static void printResult(ArrayList<Word> listOfWords) {
		for(File f : Dataset.sharedDataset.getListsOfDocuments()) {
			System.out.print(f.getName() + " ");
		}
		
		System.out.println("");
		System.out.println("-----------------------------");
		for(Word w : listOfWords) {
			System.out.print(w.getValue() + " ");
//			System.out.println("IDF: " + w.getIdf());
			w.calculateIdf();
			for(File f : Dataset.sharedDataset.getListsOfDocuments()) {
//				System.out.println("TD: " + w.getTd(f.getName()));
//				System.out.println("TD_IDF: " + w.getTdidf(f.getName()));
				System.out.print(w.getTdidf(f.getName()) + " ");
			}
//			System.out.println(w.getQtyOfTextThaHaveThisWord());
			System.out.println("");
			System.out.println("---------------------------------------------------------------");
		}
	}

}
