package atividade1;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName());
		ArrayList<Thread> listOfThreads = new ArrayList<>();
		
		for(int i = 0; i < 1000; i++) {
			listOfThreads.add(new Minion());
			listOfThreads.get(i).start();
		}
	}

}
