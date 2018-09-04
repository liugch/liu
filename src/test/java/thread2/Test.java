package thread2;

public class Test {
	public static void main(String[] args) {
		Repertory repertory = new Repertory();
		Thread threada = new Thread(new Producer(repertory));
		Thread threadb = new Thread(new Consumer(repertory));
		threada.start();
		threadb.start();
	}
}
