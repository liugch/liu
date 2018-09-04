package thread2;


/**
 * 定义生产者线程,用来生成产品的
 * @author Administrator
 *
 */
public class Producer implements Runnable{
	private Repertory repertory = null;
	
	public Producer(Repertory repertory) {
		this.repertory = repertory;
	}
	
	@Override
	public void run() {
		
		System.out.println("开始生产产品");
		while(true){
			try {
				Thread.sleep((int)(Math.random()*10)*100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repertory.addProduct();
		}
	}

}
