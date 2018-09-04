package thread2;

/**
 * 定义一个消费者的线程. 用来消费产品的
 * @author Administrator
 *
 */
public class Consumer implements Runnable{
	private Repertory repertory = null;
		
	public Consumer(Repertory repertory) {
		this.repertory = repertory;
	}
	
	@Override
	public void run() {
		System.out.println("开始消费产品");
		while(true){
			try {
				Thread.sleep((int)(Math.random()*10)*100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repertory.getProduct();
		}
	}

}
