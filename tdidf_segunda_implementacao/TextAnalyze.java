import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TextAnalyze implements Runnable{
	
	
	@Override
	public void run() {
		
	}
	
	public void analyzeText() throws IOException{
		BufferedReader text = new BufferedReader(new FileReader(Dataset.sharedDataset.getDocumentToAnalyze()));
		String line;
		String[] words = new String[1];
		
		while((line = text.readLine()) !=  null) {
			words = line.split(" ");
			System.out.println(line);
		}
		
		for(int i = 0; i < words.length; i++) {
			System.out.println(words[i]);
			
			if(words[i].equals("5")) {
				System.out.println("ok");
			}else {
				System.out.println("not ok");
			}
		}
	}
}
