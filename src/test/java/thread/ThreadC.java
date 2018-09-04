package thread;

public class ThreadC implements Runnable{
	
	private String name;
	
	private Object prev;
	
	private Object self;
	
	public ThreadC(String name,Object prev,Object self) {
		this.name = name;
		this.prev = prev;
		this.self = self;
	}
	
	
	@Override
	public void run() {
		int count = 10;
		while(count>0){
			synchronized (prev) {
				synchronized (self) {
					System.out.println(name);
					count--;
					self.notify();
					 
				}
				try {
					prev.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	public static void main(String[] args) {
		
		Object a = new Object();
		Object b = new Object();
		Object c = new Object();
				
		Thread ta = new Thread(new ThreadC("A", c, a));
		Thread tb = new Thread(new ThreadC("B", a, b));
		Thread tc = new Thread(new ThreadC("C", b, c));
		
		
		try {
			ta.start();
			Thread.sleep(2);
			tb.start();
			Thread.sleep(2);
			tc.start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
