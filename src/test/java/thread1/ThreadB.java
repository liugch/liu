package thread1;

public class ThreadB implements Runnable{
	private String name;
	
	public ThreadB(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		for (int i = 0; i < 6; i++) {
			System.out.println(name + i);
		}
		
	}

}
