package atividade1;

public class Minion extends Thread{
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + ";" + Counter.getSeqNum() + ";");
	}
}