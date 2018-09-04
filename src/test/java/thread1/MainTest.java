package thread1;

public class MainTest {

	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("主线程开始");
		ThreadA a = new ThreadA("threadA+++");
		
		Thread b = new Thread(new ThreadB("threadB++"));
		a.start();
		b.start();
		
		a.join();
		b.join();
		
		//System.out.println((int)(Math.random()*10));
		System.out.println("主线程结束");
	}
}
