package tfidf_implementacao_serial;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TextAnalyze implements Runnable{
	private ArrayList<Word> listOfWordsToAnalyse;
	private int qtyOfWordsText;
	private HashMap<String, Integer> qtyOfWordInTheText;
	
	public TextAnalyze(ArrayList<Word> listOfWordsToAnalyse) {
		this.listOfWordsToAnalyse = listOfWordsToAnalyse;
	}
	
	@Override
	public void run() {
		analyzeText();
	}
	
	public void analyzeText(){
		File textToAnalyze = Dataset.sharedDataset.getDocumentToAnalyze();
		
		while(textToAnalyze != null) {	
			try {
				System.out.println("Analisando " + textToAnalyze.getName() + " ...");
				countTheNumberOfTheWordInText(new BufferedReader(new FileReader(textToAnalyze.toString())));
				calculateTd(textToAnalyze.getName());
			} catch (IOException e) {
				e.printStackTrace();
			}
			textToAnalyze = Dataset.sharedDataset.getDocumentToAnalyze();
		}
	}
	
	/*
	 * conta o numero de palavras no texto e a quantidade de cada palavra no texto.
	 * */
	private void countTheNumberOfTheWordInText(BufferedReader text) throws IOException{
		qtyOfWordsText = 0;
		qtyOfWordInTheText= new HashMap<>();
		String line;
		String[] wordsOfTheLine = new String[1];
		
		for(Word wordToAnalyze : listOfWordsToAnalyse) {
			qtyOfWordInTheText.put(wordToAnalyze.getValue(), 0);
		}
		
		while((line = text.readLine()) !=  null) {
			wordsOfTheLine = line.split(" ");
			qtyOfWordsText += wordsOfTheLine.length;
			
			for(Word wordToAnalyze : listOfWordsToAnalyse) {
				for(int i = 0; i < wordsOfTheLine.length; i++) {
					if((wordToAnalyze.getValue().length() == wordsOfTheLine[i].length()) && wordToAnalyze.getValue().equals(wordsOfTheLine[i])) {
						qtyOfWordInTheText.put(wordToAnalyze.getValue(), qtyOfWordInTheText.get(wordToAnalyze.getValue()) + 1);
					}
				}
			}
		}
		
	}
	
	private void calculateTd(String textName) {
		
		for(Word wordToAnalyze : listOfWordsToAnalyse) {
			int qtyOfTheWordInText = qtyOfWordInTheText.get(wordToAnalyze.getValue());
			
			if(qtyOfTheWordInText > 0) {
				wordToAnalyze.addTd(textName, qtyOfTheWordInText / (double)qtyOfWordsText);
				wordToAnalyze.updateQtyOfTextThaHaveThisWord();
			}else {
				wordToAnalyze.addTd(textName, 0);
			}
		}
		
	}
}
