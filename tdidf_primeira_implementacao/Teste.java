import java.io.File; 
import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.IOException;

public class Teste extends Thread{
	private int numOfDocuments;
	private File folderDocuments;
	private File[] listOfDocuments;
	private int indexOfDocumentoToAnalize;
	
	
	public Teste() {
	}
	
	public Teste(int index) {
		numOfDocuments = index;
	}
	
	public Teste(File folder) {
		folderDocuments = folder;
		listOfDocuments = folder.listFiles();
		numOfDocuments = listOfDocuments.length;
		indexOfDocumentoToAnalize = 0;
	}
	
	public void analazeText() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("./f1"));
		String text;
		String[] words = new String[1];
		
		while((text = br.readLine()) !=  null) {
			words = text.split(" ");
			System.out.println(text);
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
	
	public int getNumOfDocuments(){
		return numOfDocuments;
	}
	
	@Override
	public void run() {
		System.out.println(getNumOfDocuments());
	}
	
	public static void main(String[] args) throws InterruptedException{
		Teste t1 = new Teste(1);
		Teste t2 = new Teste();
//		System.out.println(Runtime.getRuntime().availableProcessors());
		t1.start();
//		Thread.sleep(1000);
		t2.start();
	}

}
