package thread3;

public class Test2 {
	private void mian() {
		// TODO Auto-generated method stub
		Thread a = new Thread(new Station("a"));
		Thread b = new Thread(new Station("b"));
		Thread c = new Thread(new Station("c"));
		a.start();
		b.start();
		c.start();
	}
}
