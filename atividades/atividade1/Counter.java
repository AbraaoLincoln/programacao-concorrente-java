package atividade1;

public class Counter {
	private static int count;
	
	public Counter() {
		count = 0;
	}
	
	private static void updateCount() {
		count++;
	}
	
	public static int getSeqNum() {
		updateCount();
		return count - 1;
	}
}
