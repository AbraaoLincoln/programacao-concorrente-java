import java.util.ArrayList;

public class Main{
	
	public static void main(String[] args) throws InterruptedException{
//		System.out.println(Runtime.getRuntime().availableProcessors());
		String[] words = {"hello", "world", "today", "ok", "visit"};
		ArrayList<Word> listOfWords = new ArrayList<Word>();
		
		for(String w : words) {
			listOfWords.add(new Word(w));
		}
		
		for(Word w : listOfWords) {
			System.out.println(w.getValue());
		}

	}

}
