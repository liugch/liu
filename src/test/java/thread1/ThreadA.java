package thread1;

public class ThreadA extends Thread{

	private String name;
	
	public ThreadA(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		for (int i = 0; i <6; i++) {
			System.out.println(name + i);
		}
	}
}
