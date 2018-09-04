package thread2;

/**
 * 定义一个仓库类, 当 产品数量>=5时可以时仓库满, <=0 仓库为满
 * 
 * 该仓库可以生成和消费产品
 * 
 * @author Administrator
 *
 */
public class Repertory {
	
	private static Integer productnum = 0;

	public synchronized void addProduct(){
		if(productnum>=5){
			try {
				this.wait();// 表示仓库已经满了,获取了该对象的线程处于等待状态
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else{
			productnum ++;
			System.out.println("生产第"+productnum+"产品");
			this.notifyAll();// 通知所有等待消费者来 消费这些产品
		}
		
	}
	
	public synchronized void getProduct(){
		if(productnum<=0){
			try {
				this.wait();// 没有商品要生产商品了
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			productnum--;
			System.out.println("消费第"+productnum+"产品");
			this.notifyAll();// 通知生成者可以生成产品了
		}
	}
}
